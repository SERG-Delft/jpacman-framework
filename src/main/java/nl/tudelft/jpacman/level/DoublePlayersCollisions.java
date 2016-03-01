package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *
 * @author Arie van Deursen, 2014
 *
 */

public class DoublePlayersCollisions implements CollisionMap {

    @Override
    public void collide(Unit mover, Unit collidedOn) {

        if(mover instanceof  GhostPlayer){
            ghostPlayerColliding((GhostPlayer)mover, collidedOn);
        }
        else if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        }
    }

    private void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
        }

        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }

        if(collidedOn instanceof GhostPlayer){
            playerVersusGhostPlayer(player, (GhostPlayer)collidedOn);
        }
    }

    private void ghostColliding(Ghost ghost, Unit collidedOn) {
        if(collidedOn instanceof GhostPlayer) {
            //nothing to do
        }
        else if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
        }
    }

    private void ghostPlayerColliding(GhostPlayer ghostPlayer, Unit collidedOn){
        if(collidedOn instanceof Player){
            playerVersusGhostPlayer((Player) collidedOn, ghostPlayer);
        }
        if(collidedOn instanceof Ghost){
            //nothing to do
        }
    }



    /**
     * Actual case of player bumping into ghost or vice versa.
     *
     * @param player The player involved in the collision.
     * @param ghost The ghost involved in the collision.
     */
    public void playerVersusGhost(Player player, Ghost ghost) {
        player.setAlive(false);
    }

    public void playerVersusGhostPlayer(Player player, GhostPlayer ghostPlayer){
        player.setAlive(false);
        ghostPlayer.addPoints(100000);
    }

    /**
     * Actual case of player consuming a pellet.
     *
     * @param player The player involved in the collision.
     * @param pellet The pellet involved in the collision.
     */
    public void playerVersusPellet(Player player, Pellet pellet) {
        pellet.leaveSquare();
        player.addPoints(pellet.getValue());
    }

}
