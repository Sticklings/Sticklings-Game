package sticklings.scene;

public abstract class Entity {
	public Entity(Scene scene) {
		
	}
	
	public final int getX() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public final int getY() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public final Scene getScene() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public final void remove() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public abstract void update();
}
