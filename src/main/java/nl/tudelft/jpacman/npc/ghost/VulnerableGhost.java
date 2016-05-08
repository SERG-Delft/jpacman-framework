package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.Map;

/**
 * Create a ghost. This ghost can be eat when pacman eat a super pellet.
 *
 * @author Corentin Ducruet
 */
public abstract class VulnerableGhost extends Ghost{


    /**
     * The default score value of a ghost.
     */
    public static final int SCORE = 200;

    /**
     * Time in milli second before the respawn of a ghost.
     */
    public static final long RESPAWN_TIME = 5000;

    /**
     * The sprite map of vulnerable ghost, one sprite for each direction.
     */
    private static  Map<Direction, Sprite> vulSprite;

    /**
     * if ghost is the hunter or the hunted.
     */
    private boolean hunter;

    /**
     * The initial position of the ghost, util for is respawn.
     */
    private Square initialPosition;

    /**
     * Creates a new ghost.
     *
     * @param spriteMap The sprites for every direction.
     */
    protected VulnerableGhost(Map<Direction, Sprite> spriteMap) {
        super(spriteMap);
        hunter=true;
    }

    public VulnerableGhost(Map<Direction, Sprite> spriteMap, boolean aH, String strategy){
        super(spriteMap,aH,strategy);
        hunter=true;
    }
    public void respawn(){
        occupy(initialPosition);
        hunter=true;
    }

    @Override
    public Sprite getSprite() {
        if(hunter)
            return super.getSprite();
        return vulSprite.get(getDirection());
    }

    /**
     *
     * @return the sprite of a vulnerable ghost.
     */
    public static Map<Direction, Sprite> getVulSprite() {
        return vulSprite;
    }

    /**
     * set the vulnerable sprite.
     * @param vulSprite a map of vulnerable sprite.
     */
    public static void setVulSprite(Map<Direction, Sprite> vulSprite) {
        VulnerableGhost.vulSprite = vulSprite;
    }

    /**
     *
     * @return true if ghost is the hunter.
     */
    public boolean isHunter() {
        return hunter;
    }

    /**
     * Set the ghost hunter or not.
     * @param hunter
     */
    public void setHunter(boolean hunter) {
        this.hunter = hunter;
    }


    /**
     * get the initial position of the ghost.
     */
    public Square getInitialPosition() {
        return initialPosition;
    }

    /**
     * set the initial position of the ghost.
     * @param initialPosition
     */
    public void setInitialPosition(Square initialPosition) {
        this.initialPosition = initialPosition;
    }
}
