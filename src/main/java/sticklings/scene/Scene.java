package sticklings.scene;

public class Scene {
	public Scene() {
		
	}
	
	public void update() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public Entity getEntity(int id) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public <T extends Entity> Iterable<T> findEntities(Class<T> type) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public Iterable<Entity> getAllEntities() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
