package sticklings.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import javafx.scene.image.Image;

/**
 * Manages textures and enables dynamic / animated textures
 */
public class TextureManager {
	private List<TextureSource> sources;

	// All access to either allTextures or dynamicTextures must be synchronized on allTextures
	private List<AbstractTexture> allTextures;
	private List<DynamicTexture> dynamicTextures;

	private Map<String, Optional<AbstractTexture>> loadedTextures;

	/**
	 * Constructs a new empty texture manager
	 */
	public TextureManager() {
		sources = new ArrayList<>();
		loadedTextures = Maps.newHashMap();

		allTextures = new ArrayList<>();
		dynamicTextures = new ArrayList<>();
	}

	public void addTextureSource(TextureSource source) {
		Preconditions.checkNotNull(source);
		sources.add(source);
	}

	public AbstractTexture getTexture(String path) {
		Optional<AbstractTexture> texture = loadedTextures.get(path);
		if (texture == null) {
			// Source the texture
			for (TextureSource source : sources) {
				try {
					texture = Optional.of(source.provideTexture(path));
				} catch (NoSuchFileException e) {
					texture = Optional.absent();
				} catch (IOException e) {
					System.err.println("Failed to source texture " + path + ". IOException:");
					e.printStackTrace();
					break;
				}
			}

			if (texture != null) {
				loadedTextures.put(path, texture);

				if (texture.isPresent()) {
					return texture.get();
				} else {
					System.err.println("Failed to source texture " + path + ". File not found");
				}
			} else {
				loadedTextures.put(path, Optional.absent());
			}
		} else if (texture.isPresent()) {
			return texture.get();
		}

		return NullTexture.get();
	}

	/**
	 * Creates a basic static texture from an image
	 * 
	 * @param input The texture input stream.
	 * @return The texture
	 */
	public AbstractTexture createBasic(InputStream input) {
		Image image = new Image(input);
		BasicTexture texture = new BasicTexture(image);
		synchronized (allTextures) {
			allTextures.add(texture);
		}
		return texture;
	}

	/**
	 * Creates an animated, sprite based, texture from an image
	 * 
	 * @param input The texture input stream
	 * @param settings The settings for defining the animation
	 * @return The texture
	 */
	public AbstractTexture createAnimated(InputStream input, AnimationSettings settings) {
		Image image = new Image(input);
		AnimatedTexture texture = new AnimatedTexture(image, settings);
		synchronized (allTextures) {
			allTextures.add(texture);
		}
		return texture;
	}

	/**
	 * Adds a dynamic texture to the scene. This texture will be updated during the game loop
	 * 
	 * @param texture The texture to add
	 */
	public void addDynanic(DynamicTexture texture) {
		synchronized (allTextures) {
			allTextures.add(texture);
			dynamicTextures.add(texture);
		}
	}

	/**
	 * Removes a previously added texture from the manager.
	 * 
	 * @param texture The texture to remove
	 */
	public void removeTexture(AbstractTexture texture) {
		synchronized (allTextures) {
			allTextures.remove(texture);
			if (texture instanceof DynamicTexture) {
				dynamicTextures.remove(texture);
			}
		}
	}

	/**
	 * Removes all textures from the manager
	 */
	public void clearTextures() {
		synchronized (allTextures) {
			allTextures.clear();
			dynamicTextures.clear();
		}
	}

	/**
	 * Gets the number of loaded textures
	 * 
	 * @return The number of loaded texture
	 */
	public int getLoadedTextureCount() {
		synchronized (allTextures) {
			return allTextures.size();
		}
	}

	/**
	 * Updates dynamic textures
	 * 
	 * @param deltaTime The time in seconds between the last update and this one
	 */
	public void update(double deltaTime) {
		synchronized (allTextures) {
			for (DynamicTexture texture : dynamicTextures) {
				texture.update(deltaTime);
			}
		}
	}
}
