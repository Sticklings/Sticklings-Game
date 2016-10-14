package sticklings.scene.sticklings;

import sticklings.Game;
import sticklings.util.Location;
/**
 *
 * @author swann
 */
public class ExploderStickling extends Stickling {
    private static final int WALK_TIME = 6;
    private int DEATH_TIME = 5;
    
    private State state;
    private int stepCount;
    private int nextStateChange;

    public ExploderStickling(){
        setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/exploder.png"));
        setTextureOffset(new Location(-10, -10));
        setState(State.Walking);
    }
    
    private void setState(State state){
        this.state = state;
        stepCount = 0;
        if (state == State.Exploding){
            Location pos = getLocation();
            getScene().getTerrain().clearCircleAt((int)pos.x, (int)pos.y, 50);
            remove();
        }else {
            nextStateChange = WALK_TIME;
	}
    }

    @Override
    protected void operate() {
        //System.out.print(String.format("%d", DEATH_TIME));
        if (stepCount > nextStateChange){
            DEATH_TIME = DEATH_TIME -1;
            if (DEATH_TIME == 0){
                setState(State.Exploding);
            } else {
                setState(State.Walking);
            }
        }else {
            ++stepCount;
	}
    }
    
    @Override
    public void copyFrom(Stickling other) {
    	super.copyFrom(other);
    	locomotor.setAllowedMovement(other.locomotor.getAllowedMovement());
    }
    
    private enum State {
		Exploding,
		Walking
    }
}
