package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.List;

/**
 * level factory for super pellet level
 * @author  Corentin Ducruet
 */
public class LevelFactorySuperPellet extends LevelFactory{
    /**
     * Creates a new level factory.
     *
     * @param spriteStore  The sprite store providing the sprites for units.
     * @param ghostFactory
     */
    public LevelFactorySuperPellet(PacManSprites spriteStore, GhostFactory ghostFactory) {
        super(spriteStore, ghostFactory);
    }


    @Override
    public Level createLevel(Board board, List<NPC> ghosts,
                             List<Square> startPositions) {

        // We'll adopt the simple collision map for now.

        PlayerCollisionsSuperPellet collisionMap = new PlayerCollisionsSuperPellet();

        LevelSuperPellet level = new LevelSuperPellet(board, ghosts, startPositions, collisionMap);
        collisionMap.addLevel(level);
        return level;
    }
}
