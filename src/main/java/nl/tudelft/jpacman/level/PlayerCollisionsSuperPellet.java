package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhost;

/**
 * @author Corentin Ducruet
 */
public class PlayerCollisionsSuperPellet extends PlayerCollisions {

    private LevelSuperPellet level;

    public  void addLevel(LevelSuperPellet l){
        level=l;
    }
    @Override
    public void collide(Unit mover, Unit collidedOn) {
        if (mover instanceof Player) {
            if (collidedOn instanceof Ghost) {
                playerVersusGhost((Player) mover, (VulnerableGhost) collidedOn);
            }

            if (collidedOn instanceof SuperPellet) {
                playerVersusSuperPellet((Player) mover, (SuperPellet) collidedOn);
            }
            else if (collidedOn instanceof Pellet) {
                playerVersusPellet((Player) mover, (Pellet) collidedOn);
            }
        }
        else if (mover instanceof Ghost) {
            if (collidedOn instanceof Player) {
                playerVersusGhost((Player) collidedOn,(VulnerableGhost) mover);
            }
        }
    }

    /**
     * collision between ghost and pacman
     * @param player
     * @param ghost
     */
    public void playerVersusGhost(Player player, VulnerableGhost ghost) {
        if(ghost.isHunter())
            player.setAlive(false);
        else {
            ghost.leaveSquare();
            player.addPoints(player.getEatedGhost());
            player.addEatedGhost();
            level.addRespawnGhost(ghost);
        }
    }

    /**
     * when a player is colliding with a super pellet.
     * @param player
     * @param pellet
     */
    public void playerVersusSuperPellet(Player player, SuperPellet pellet) {
        pellet.leaveSquare();
        player.addPoints(pellet.getValue());
        player.superPelletEated();
        level.startGhostHunted(player);
    }
}
