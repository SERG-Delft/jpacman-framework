package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Created by helldog136 on 26/02/16.
 */
public class MultiGhostPlayerCollisions extends PlayerCollisions{
    
    @Override
    public void collide(Unit mover, Unit collidedOn) {

        if (mover instanceof HunterGhostPlayer) {
            playerColliding((HunterGhostPlayer) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        }
    }
    
    protected void playerColliding(HunterGhostPlayer player, Unit collidedOn) {
        if (collidedOn instanceof HunterGhostPlayer) {
            playerVersusPlayer(player, (HunterGhostPlayer) collidedOn);
        }

        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }
    }

    private void playerVersusPlayer(HunterGhostPlayer player1, HunterGhostPlayer player2) {
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
    public void playerVersusPellet(HunterGhostPlayer player, Pellet pellet) {
        if(player.isActive()) {
            pellet.leaveSquare();
            player.addPoints(pellet.getValue());
        }
    }
}
