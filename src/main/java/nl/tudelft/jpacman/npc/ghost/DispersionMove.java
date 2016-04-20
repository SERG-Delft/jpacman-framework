package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;

import java.util.List;

/**
 * The ghost dispersion move. This class implements the function <code>nextMove</code> from interface
 * <code>{@link MoveStrategy}</code>.
 *
 * @author NONO TATOU P.G.
 */
public class DispersionMove implements MoveStrategy{
    /**
     * The ghost at which implement this move.
     */
    private Ghost ghost;

    /**
     * <code>directionCounter</code>: Useful to determine the next direction to take.
     * <code>pathLength</code>: The length path to follow.
     */
    private int directionCounter, pathLength;

    /**
     * The path to follow.
     */
    private Direction [] cycle;

    public DispersionMove(Ghost g){
        this.ghost = g;
        this.directionCounter = -1;
    }

    @Override
    public Direction nextMove(){
        Square origin = getGhost().getSquare();
        Square home = getGhost().getHome();
        Direction d = getGhost().randomMove();

        if(origin == home || getGhost().isInMyCorner()) {
            d = cycle();
        }else{
            List<Direction> path = Navigation.shortestPath(origin,home,getGhost());

            if (path != null && !path.isEmpty()) d = path.get(0);
        }

        return d;
    }

    /**
     * Compute the next direction to take in the path to follow.
     *
     * @return
     *      The direction to take.
     */
    private Direction cycle(){
        getGhost().inMyCorner(true);

        return this.cycle[nextDirection()];
    }

    /**
     * @return The ghost at which this is implemented.
     */
    private Ghost getGhost(){ return this.ghost; }

    /**
     * Increments the direction counter.
     *
     * @return
     *      The number of directions already followed.
     */
    private int getDirectionCounter(){ return ++this.directionCounter; }

    /**
     * Set the direction counter to '-1'.
     */
    public void resetDirectionCounter(){
        getGhost().inMyCorner(false);

        this.directionCounter = -1;
    }

    /**
     * Set the path to follow by the ghost.
     *
     * @param c
     *      The path to follow by the ghost.
     */
    protected void setCycle(Direction[] c){ this.cycle = c; }

    /**
     * Set the path length to follow by the ghost.
     *
     * @param l
     *      The path length to follow.
     */
    protected void setCycleLength(int l){ this.pathLength = l; }

    /**
     * Computes the next direction to take.
     *
     * @return
     *      The next direction to take.
     */
    private int nextDirection(){ return getDirectionCounter() % this.pathLength;}
}
