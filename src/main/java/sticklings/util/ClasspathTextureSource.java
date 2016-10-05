package sticklings.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

import sticklings.render.AbstractTexture;
import sticklings.render.TextureManager;
import sticklings.render.TextureSource;

public class ClasspathTextureSource implements TextureSource {
	private final TextureManager manager;
	private final Class<?> providingSource;
	
	
	
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
