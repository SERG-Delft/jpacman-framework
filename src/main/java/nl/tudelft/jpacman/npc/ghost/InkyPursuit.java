package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;

import java.util.List;

/**
 * Created by NONO TATOU
 */
public class InkyPursuit extends PursuitMove{
    private static final int SQUARES_AHEAD = 2;

    public InkyPursuit(Ghost u){
        super(u);
    }

    public Direction nextMove(){
        Direction d = getGhost().randomMove();
        Square origin = getGhost().getSquare();
        Unit blinky = Navigation.findNearest(Blinky.class, origin);
        Unit player = Navigation.findNearest(Player.class, origin);

        if (blinky != null && player != null) {
            Square destination = twoSquaresAway(player.getDirection(), player.getSquare());
            d = myPathTo(blinky.getSquare(),destination);
        }

        return d;
    }

    /**
     * Path followed by Inky depending on Blinky's place and the square at two square in front of Pac-Man.
     *
     * @param blinkyPlace
     * 		The square currently occupied by Blinky.
     *
     * @param destination
     * 		The square at two square in front of Pac-Man.
     *
     * @return
     * 		The next square where to go.
     */
    private Direction myPathTo(Square blinkyPlace, Square destination) {
        Direction d = getGhost().randomMove();
        Square origin = getGhost().getSquare();
        List<Direction> firstHalf = Navigation.shortestPath(blinkyPlace,destination, null);

        if(firstHalf != null){
            for (Direction e : firstHalf) destination = destination.getSquareAt(e);

            List<Direction> path = Navigation.shortestPath(origin,destination, getGhost());

            if (path != null && !path.isEmpty()) d = path.get(0);
        }

        return d;
    }

    /**
     * Locate two squares in front of Pac-Man.
     *
     * @param d
     * 		The direction currently followed by Pac-Man.
     *
     * @param s
     * 		The Pac-Man current position.
     *
     * @return
     * 		Two squares in front of Pac-Man in his current direction of travel.
     */
    private Square twoSquaresAway(Direction d, Square s){

        for (int i = 0; i < SQUARES_AHEAD; i++) s = s.getSquareAt(d);

        return s;
    }
}
