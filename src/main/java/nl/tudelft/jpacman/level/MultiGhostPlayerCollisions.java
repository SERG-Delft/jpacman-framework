package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.HunterGhostNPC;

/**
 * Created by helldog136 on 26/02/16.
 */
public class MultiGhostPlayerCollisions extends PlayerCollisions{
    
    @Override
    public void collide(Unit mover, Unit collidedOn) {

        if (mover instanceof HunterGameModePlayer) {
            hunterColliding((HunterGameModePlayer) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        }
    }

    protected void hunterColliding(HunterGameModePlayer player, Unit collidedOn) {
        if (collidedOn instanceof HunterGameModePlayer) {
            hunterVersusHunter(player, (HunterGameModePlayer) collidedOn);
        }
        if (collidedOn instanceof Pellet) {
            hunterVersusPellet(player, (Pellet) collidedOn);
        }
    }

    private void hunterVersusHunter(HunterGameModePlayer player1, HunterGameModePlayer player2) {
        //TODO blocking
        if(player1.isHunter() || player2.isHunter()) {
            if(player1.isHunter()) {
                player1.addPoints(player2.hunted());
            }else{
                player2.addPoints(player1.hunted());
            }
        }
    }
    
    

    /**
     * Actual case of player consuming a pellet.
     *
     * @param player The player involved in the collision.
     * @param pellet The pellet involved in the collision.
     */
    public void hunterVersusPellet(HunterGameModePlayer player, Pellet pellet) {
        if(player.isActive()) {
            if(!player.isHunter()) {
                pellet.leaveSquare();
                player.addPoints(pellet.getValue());
            }
        }
    }
}
