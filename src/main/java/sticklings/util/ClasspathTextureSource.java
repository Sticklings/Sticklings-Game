package sticklings.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

import sticklings.render.AbstractTexture;
import sticklings.render.TextureManager;
import sticklings.render.TextureSource;

/**
 * A texture source that loads from the class path
 */
public class ClasspathTextureSource implements TextureSource {
	private final TextureManager manager;
	private final Class<?> providingSource;

	/**
	 * Creates a new classpath texture source
	 * 
	 * @param providingSource The class that provides the classpath to search
	 * @param manager The texture manager to load into
	 */
	public ClasspathTextureSource(Class<?> providingSource, TextureManager manager) {
		this.providingSource = providingSource;
		this.manager = manager;
	}

	@Override
	public AbstractTexture provideTexture(String path) throws NoSuchFileException, IOException {
		InputStream textureStream = providingSource.getResourceAsStream(path);
		if (textureStream == null) {
			throw new NoSuchFileException(path);
		}

		return manager.createBasic(textureStream);
	}
}
