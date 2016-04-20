package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;

/**
 * Created by NONO TATOU
 */
public class PinkyDispersion extends DispersionMove{
    private final Direction [] cycle = {Direction.WEST,Direction.WEST, Direction.SOUTH,Direction.SOUTH,
                                        Direction.EAST,Direction.EAST, Direction.EAST,Direction.EAST,
                                        Direction.NORTH,Direction.NORTH,Direction.WEST,Direction.WEST};

    public PinkyDispersion(Ghost g) {
        super(g);
        setCycle(this.cycle);
        setCycleLength(this.cycle.length);
    }


}
