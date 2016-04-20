package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;

import java.util.List;

/**
 * Created by NONO TATOU
 */
public class DispersionMove implements MoveStrategy{
    private Ghost ghost;
    private int directionCounter, pathLength;
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

        if(origin == home || this.directionCounter > -1) {
            d = cycle();
        }else{
            List<Direction> path = Navigation.shortestPath(origin,home,getGhost());

            if (path != null && !path.isEmpty()) d = path.get(0);
        }

        return d;
    }

    private Direction cycle(){
        getGhost().atHome(true);

        return this.cycle[nextDirection()];
    }

    private Ghost getGhost(){ return this.ghost; }

    private int getDirectionCounter(){ return ++this.directionCounter; }

    public void resetDirectionCounter(){
        getGhost().atHome(false);

        this.directionCounter = -1;
    }

    protected void setCycle(Direction[] c){ this.cycle = c; }

    protected void setCycleLength(int l){ this.pathLength = l; }

    private int nextDirection(){ return getDirectionCounter() % this.pathLength;}
}
