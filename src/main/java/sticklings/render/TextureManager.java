package sticklings.render;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Manages textures and enables dynamic / animated textures 
 */
public class TextureManager {
	// All access to either allTextures or dynamicTextures must be synchronized on allTextures
	private List<AbstractTexture> allTextures;
	private List<DynamicTexture> dynamicTextures;
	
	/**
	 * Constructs a new empty texture manager
	 */
	public TextureManager() {
		allTextures = new ArrayList<>();
		dynamicTextures = new ArrayList<>();
	}
	
	/**
	 * Creates a basic static texture from an image
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
	 * Adds a dynamic texture to the scene.
	 * This texture will be updated during the game loop 
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
	 * @return The number of loaded texture
	 */
	public int getLoadedTextureCount() {
		synchronized (allTextures) {
			return allTextures.size();
		}
	}
	
	/**
	 * Updates dynamic textures
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
