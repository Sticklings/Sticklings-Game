package sticklings.levels;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import sticklings.scene.sticklings.SticklingType;
import sticklings.util.Location;

public class LevelLoader {
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
