/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sticklings.scene.sticklings;
import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;
import sticklings.Game;
import sticklings.terrain.TerrainData;
import sticklings.terrain.TerrainType;

/**
 *
 * @author swann
 */
public class SwimmerStickling extends Stickling {
    private static final int SWIM_TIME = 6;
    private static final int WALK_TIME = 6;
    
    private State state;
    private int stepCount;
    private int nextStateChange;
    
    private Location pos;
        
    private TerrainData check;
    
    public SwimmerStickling() {
        setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/swimmer.png"));
        setTextureOffset(new Location(-10, -10));
        locomotor.allowMovement(MovementType.Walk);
        locomotor.allowMovement(MovementType.Swim);
        setState(State.Walking);
    }

    @Override
    protected void operate() {
        check = getScene().getTerrain();
        pos  = getLocation();
        if (stepCount > nextStateChange){
            switch (state) {
                case Swimming:
                    if (check.isType(TerrainType.WATER, (int) pos.x, (int) pos.y +12)){
                        setState(State.Swimming);
                    }
                    if (check.isType(TerrainType.GROUND, (int) pos.x, (int) pos.y+12)){
                        setState(State.Exiting);
                    }
                    break;
                case Walking:
                    if (check.isType(TerrainType.WATER, (int) pos.x, (int) pos.y + 12)){
                        setState(State.Swimming);
                    }
                    if (check.isType(TerrainType.GROUND, (int) pos.x, (int) pos.y + 12)){
                        setState(State.Walking);
                    }break;
                case Exiting: 
                    BasicStickling newStickling = new BasicStickling();
                    newStickling.copyFrom(this);
                    remove();
                    getScene().addEntity(newStickling);
                    break;                        
            }
        } else {
            ++stepCount;
        }
    }
   
    private void setState(State state) {
        this.state = state;
        stepCount = 0;    
        
        if (state == State.Swimming){
            nextStateChange = SWIM_TIME;
        } else {
            nextStateChange = WALK_TIME;
        }
    }
        
    private enum State {
        Swimming,
        Walking,
        Exiting
    }
}
