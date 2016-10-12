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

/**
 *
 * @author swann
 */
public class SwimmerStickling extends Stickling {
    private static final int SWIM_TIME = 6;
    private static final int WALK_TIME = 6;
    
    public SwimmerStickling() {
        setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/swimmer.png"));
        setTextureOffset(new Location(-10, -10));
    }

    @Override
    protected void operate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
