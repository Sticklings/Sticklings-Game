package sticklings.scene;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javafx.scene.image.Image;
import sticklings.levels.Level;
import sticklings.terrain.TerrainData;
import sticklings.terrain.TerrainLoader;

/**
 * Represents an entire scene, including the entities and terrain
 */
public class Scene {
	private final Map<Integer, Entity> entityMap;
	
	// In order to prevent CME's, changes to the entity map must be postponed
	// until updating is finished
	private boolean isUpdating;
	private final Map<Integer, Entity> toAdd;
	private final Set<Integer> toRemove;
	
	private int nextEntityId;
	
	private final TerrainData terrainData;
	
	private final int sceneWidth;
	private final int sceneHeight;
	
	// Game stats
	private int totalSticklings;
	private int remainingSticklings;
	
	/**
	 * Constructs a new empty scene
	 */
	public Scene(TerrainData terrain, Level levelDefinition) {
		this.terrainData = terrain;
		this.sceneWidth = terrain.getWidth();
		this.sceneHeight = terrain.getHeight();
		
		entityMap = Maps.newHashMap();
		
		isUpdating = false;
		toAdd = Maps.newHashMap();
		toRemove = Sets.newHashSet();
		
		nextEntityId = 0;
		
		// Load settings from level
		// DEBUG STUFF
		totalSticklings = 10;
		remainingSticklings = totalSticklings;
	}
	
	/**
	 * Gets the width of the scene.
	 * This is not a hard limit
	 * @return The width
	 */
	public int getWidth() {
		return sceneWidth;
	}
	
	/**
	 * Gets the height of the scene.
	 * This is not a hard limit
	 * @return The height
	 */
	public int getHeight() {
		return sceneHeight;
	}
	
	/**
	 * Gets the terrain data for this scene
	 * @return The terrain data
	 */
	public TerrainData getTerrain() {
		return terrainData;
	}
	
	/**
	 * Adds an entity to the scene.
	 * If an update is in progress, the entity will not be added until the update is over
	 * @param entity The entity to add
	 */
	public void addEntity(Entity entity) {
		Preconditions.checkNotNull(entity);
		
		int id = nextEntityId++;
		entity.setScene(this, id);
		
		if (isUpdating) {
			toAdd.put(id, entity);
		} else {
			entityMap.put(id, entity);
		}
	}
	
	/**
	 * Marks an entity for removal.
	 * If an update is in progress, the entity will be removed after the update is over
	 * @param entityId The ID of the entity to remove
	 */
	public void removeEntity(int entityId) {
		if (isUpdating) {
			toRemove.add(entityId);
		} else {
			entityMap.remove(entityId);
		}
	}
	
	/**
	 * Gets an entity by ID
	 * @param id the ID of the entity
	 * @return The Entity or null
	 */
	public Entity getEntity(int id) {
		if (isUpdating) {
			// Consider that the entity may not yet be added
			Entity ent = entityMap.get(id);
			if (ent != null) {
				return ent;
			} else {
				return toAdd.get(id);
			}
		} else {
			return entityMap.get(id);
		}
	}
	
	/**
	 * Updates all entities within the scene
	 * @param deltaTime The time in seconds between the last update and this
	 */
	public void update(double deltaTime) {
		isUpdating = true;
		for (Entity entity : entityMap.values()) {
			entity.update(deltaTime);
		}
		isUpdating = false;
		
		// Add all new
		entityMap.putAll(toAdd);
		toAdd.clear();
		
		// Remove all marked
		for (Integer id : toRemove) {
			entityMap.remove(id);
		}
		toRemove.clear();
	}
	
	/**
	 * Gets all entities of the given type
	 * @param type The type of entity to get
	 * @return An iterable that contains the entities
	 */
	public <T extends Entity> Iterable<T> findEntities(Class<T> type) {
		return Iterables.filter(getAllEntities(), type);
	}
	
	/**
	 * Gets all entities that are within this scene.
	 * NOTE: Care should be taken that no entities are added or removed while iterating on this collection
	 * @return An unmodifiable collection of entities
	 */
	public Collection<Entity> getAllEntities() {
		return Collections.unmodifiableCollection(entityMap.values());
	}
	
	/**
	 * Gets the total number of sticklings that will be spawned
	 * @return The number that will be spawned
	 */
	public int getTotalSticklings() {
		return totalSticklings;
	}
	
	/**
	 * Gets the number of sticklings remaining
	 * @return The number remaining
	 */
	public int getRemainingSticklings() {
		return remainingSticklings;
	}
	
	/**
	 * Updates the number of remaining sticklings
	 * @param remaining The remaining sticklings
	 */
	public void setRemainingSticklings(int remaining) {
		Preconditions.checkArgument(remaining >= 0 && remaining <= totalSticklings);
		remainingSticklings = remaining;
	}
	
	/**
	 * Creates a scene from a level object
	 * @param level The level to load
	 * @return The Scene with the loaded terrain data
	 * @throws IOException Thrown if an error occurd while loading the terrain mask
	 */
	public static Scene fromLevel(Level level) throws IOException {
		Preconditions.checkNotNull(level);
		
		Image terrainMask = new Image(level.getTerrainMaskURL().openStream());
		
		TerrainData terrainData = TerrainLoader.load(terrainMask);
		return new Scene(terrainData, level);
	}
}
