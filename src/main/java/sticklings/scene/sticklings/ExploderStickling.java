package sticklings.scene.sticklings;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
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
    
    private Label death_counter = new Label();
    
    public ExploderStickling(){
        death_counter.setAlignment(Pos.CENTER);        
        death_counter.setTextFill(Paint.valueOf("White"));
        
        setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/exploder.png"));
        setTextureOffset(new Location(-10, -10));
        setState(State.Walking);
    }
    
    private void setState(State state){
        this.state = state;
        stepCount = 0;
        if (state == State.Exploding){
            Location pos = getLocation();
            getScene().getTerrain().clearCircleAt((int)pos.x, (int)pos.y, 30);
            remove();
        }else {
            locomotor.allowMovement(MovementType.Walk);
            nextStateChange = WALK_TIME;
	}
    }

    @Override
    protected void operate() {
        death_counter.setLayoutX(getLocation().x);
        death_counter.setLayoutY(getLocation().y + 20);
        death_counter.setText(String.format("%d", DEATH_TIME));
        System.out.print(String.format("%d", DEATH_TIME));
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
    
    private enum State {
		Exploding,
		Walking
    }
}
