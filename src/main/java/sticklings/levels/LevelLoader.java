package sticklings.levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import sticklings.scene.sticklings.SticklingType;
import sticklings.util.Location;

public class LevelLoader {
	private final URL rootPath;
	private final List<Level> levels;
	
	public LevelLoader(URL rootPath) {
		this.rootPath = rootPath;
		levels = new ArrayList<>();
	}
	
	/**
	 * Gets all levels that were loaded
	 * @return An unmodifiable list of levels
	 */
	public List<Level> getLevels() {
		return Collections.unmodifiableList(levels);
	}
	
	/**
	 * Loads all levels specified in the levels dat file
	 * @throws IOException Thrown if either the levels dat file cannot be read, or a level cannot be read
	 */
	public void loadAll() throws IOException {
		// Read the levels file, find all level paths
		List<URL> levelPaths = new ArrayList<>();
		levels.clear();
		
		URL levelsDatPath = new URL(rootPath, "levels.dat");
		
		try (InputStream stream = levelsDatPath.openStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			
			String line;
			while ((line = reader.readLine()) != null) {
				try {
					levelPaths.add(new URL(rootPath, line));
				} catch (MalformedURLException e) {
					// TODO: Report problem
					System.err.println("Invalid level path: " + line);
				}
			}
		}
		
		// Load each level
		for (URL path : levelPaths) {
			Level level = load(path);
			levels.add(level);
		}
	}
	
	/**
	 * Loads a single level file from anywhere
	 * @param levelPath The path of the level folder. There must be a level.dat in that folder
	 * @return The loaded level
	 * @throws IOException Thrown if the level.dat file doesnt exist or cannot be read
	 */
	public Level load(URL levelPath) throws IOException {
		URL levelDatPath = new URL(levelPath, "level.dat");
		Properties propFile = new Properties();
		propFile.load(levelDatPath.openStream());
		
		String levelName = propFile.getProperty("name");
		int totalSticklings = Integer.parseInt(propFile.getProperty("total"));
		int requiredSticklings = Integer.parseInt(propFile.getProperty("required"));
		
		URL terrainMaskPath = new URL(levelPath, propFile.getProperty("terrain.mask"));
		URL foregroundPath = new URL(levelPath, propFile.getProperty("terrain.foreground"));
		URL backgroundPath = new URL(levelPath, propFile.getProperty("terrain.background"));
		
		// Load the number of each type available to use
		SticklingAvailability availability = new SticklingAvailability();
		for (SticklingType type : SticklingType.values()) {
			if (type == SticklingType.Basic) {
				continue;
			}
			
			try {
				int count = Integer.parseInt(propFile.getProperty("type." + type.name().toLowerCase()));
				if (count < 0) {
					System.err.println("Invalid value in level 'type." + type.name().toLowerCase() + "'. Must be 0 or more");
					continue;
				}
				
				availability.setTotal(type, count);
			} catch (NumberFormatException e) {
				System.err.println("Invalid value in level 'type." + type.name().toLowerCase() + "'. Must be a number 0 or more");
			}
		}
		
		// Start location
		Location startLocation;
		try {
			int x = Integer.parseInt(propFile.getProperty("start.x"));
			int y = Integer.parseInt(propFile.getProperty("start.y"));
			
			startLocation = new Location(x, y);
		} catch (NumberFormatException e) {
			System.err.println("Invalid start coord.");
			startLocation = new Location();
		}
		
		// Start location
		Location endLocation;
		try {
			int x = Integer.parseInt(propFile.getProperty("end.x"));
			int y = Integer.parseInt(propFile.getProperty("end.y"));
			
			endLocation = new Location(x, y);
		} catch (NumberFormatException e) {
			System.err.println("Invalid end coord.");
			endLocation = new Location();
		}
		
		// Build the level now
		return new Level(levelName, terrainMaskPath, foregroundPath, backgroundPath, totalSticklings, requiredSticklings, availability, startLocation, endLocation);
	}
}
