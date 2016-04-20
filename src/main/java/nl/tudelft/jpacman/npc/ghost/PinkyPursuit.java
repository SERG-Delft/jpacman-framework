package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;

/**
 * Created by NONO TATOU
 */
public class PinkyPursuit extends PursuitMove{

    private static final int SQUARES_AHEAD = 4;

    public PinkyPursuit(Ghost u) {
        super(u);
    }

    @Override
    public Direction nextMove() {
        Direction d = getGhost().randomMove();
        Square origin = getGhost().getSquare();
        Unit player = Navigation.findNearest(Player.class, origin);

        if (player != null) {
            Square destination = fourSquaresAway(player.getDirection(), player.getSquare());

            d = myPathTo(destination);
        }

        return d;
    }

    /**
     * Locate four squares in front of Pac-Man.
     *
     * @param d
     * 		The direction currently followed by Pac-Man.
     *
     * @param s
     * 		The Pac-Man current position.
     *
     * @return
     * 		Four squares in front of Pac-Man in his current direction of travel.
     */
    private Square fourSquaresAway(Direction d, Square s){
        for (int i = 0; i < SQUARES_AHEAD; i++) s = s.getSquareAt(d);

        return s;
    }
}
