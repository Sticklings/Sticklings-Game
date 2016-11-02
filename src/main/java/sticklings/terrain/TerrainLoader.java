package sticklings.terrain;

import java.nio.IntBuffer;

import com.google.common.base.Preconditions;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;

/**
 * A utility class to load terrain
 */
public class TerrainLoader {
	/**
	 * Loads terrain from a masking image. The mask should be grayscale where types are represented by the following values
	 * <dl>
	 * <dt>AIR</dt>
	 * <dd>0x00</dd>
	 * <dt>GROUND</dt>
	 * <dd>0xFF</dd>
	 * <dt>WATER</dt>
	 * <dd>0x80</dd>
	 * </dl>
	 * 
	 * @param mask The masking image
	 * @return The loaded terrain data object
	 */
	public static TerrainData load(Image mask) {
		Preconditions.checkNotNull(mask);

		PixelReader reader = mask.getPixelReader();
		int width = (int) mask.getWidth();
		int height = (int) mask.getHeight();

		TerrainType[] data = new TerrainType[width * height];
		IntBuffer maskData = IntBuffer.allocate(width * height);
		reader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), maskData, width);

		for (int i = 0; i < width * height; ++i) {
			int maskValue = maskData.get(i) & 0xFF;
			TerrainType type;
			switch (maskValue) {
			case 0x00:
				type = TerrainType.AIR;
				break;
			case 0x80:
				type = TerrainType.WATER;
				break;
			default:
				type = TerrainType.GROUND;
				break;
			}

			data[i] = type;
		}

		return new TerrainData(data, width, height);
	}
}
