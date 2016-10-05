package sticklings.render;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * Represents a provider of textures
 */
public interface TextureSource {
	/**
	 * Asks this source to provide a texture at the given path.
	 * This may do whatever it needs in order to create the texture
	 * @param path The path to load the texture from
	 * @return The loaded texture
	 * @throws NoSuchFileException Thrown if the path is not a texture
	 * @throws IOException Thrown if the texture cannot load
	 */
	public AbstractTexture provideTexture(String path) throws NoSuchFileException, IOException;
}
