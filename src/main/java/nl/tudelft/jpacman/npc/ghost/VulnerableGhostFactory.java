package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * creator of vulnerable ghost.
 * @author Corentin Ducruet
 */
public class VulnerableGhostFactory extends GhostFactory {
    /**
     * Creates a new ghost factory.
     *
     * @param spriteStore The sprite provider.
     */
    public VulnerableGhostFactory(PacManSprites spriteStore) {
        super(spriteStore);
        VulnerableGhost.setVulSprite(spriteStore.getVulGhost());
    }

    /**
     * Creates a new Blinky / Shadow, the red Ghost.
     *
     * @see Blinky
     * @return A new Blinky.
     */
    public VulnerableGhost createBlinky() {
        return new Blinky(getSprites().getGhostSprite(GhostColor.RED), false,"modeDispersion", cheminB);
    }

    /**
     * Creates a new Pinky / Speedy, the pink Ghost.
     *
     * @see Pinky
     * @return A new Pinky.
     */
    public VulnerableGhost createPinky() {
        return new Pinky(getSprites().getGhostSprite(GhostColor.PINK),false,"modeDispersion", cheminP);
    }

    /**
     * Creates a new Inky / Bashful, the cyan Ghost.
     *
     * @see Inky
     * @return A new Inky.
     */
    public VulnerableGhost createInky() {
        return new Inky(getSprites().getGhostSprite(GhostColor.CYAN), false,"modeDispersion", cheminI);
    }

    /**
     * Creates a new Clyde / Pokey, the orange Ghost.
     *
     * @see Clyde
     * @return A new Clyde.
     */
    public VulnerableGhost createClyde() {
        return new Clyde(getSprites().getGhostSprite(GhostColor.ORANGE),  false,"modeDispersion",cheminC);
    }


}
