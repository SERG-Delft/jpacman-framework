package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;

/**
 * Created by NONO TATOU
 */
public class InkyDispersion extends DispersionMove{
    private final Direction [] cycle = {Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.EAST,
                                        Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTH,
                                        Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.WEST,
                                        Direction.WEST, Direction.WEST, Direction.WEST, Direction.WEST,
                                        Direction.WEST, Direction.WEST, Direction.NORTH, Direction.NORTH,
                                        Direction.EAST, Direction.EAST, Direction.EAST, Direction.NORTH,
                                        Direction.NORTH, Direction.EAST};

    public InkyDispersion(Ghost g) {
        super(g);
        setCycle(this.cycle);
        setCycleLength(this.cycle.length);
    }
}