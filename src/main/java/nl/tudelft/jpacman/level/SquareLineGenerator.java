package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.Collection;

/**
 * Created by Angeall on 02/03/2016.
 */
public class SquareLineGenerator {
    protected BoardFactory boardFactory;
    protected LevelFactory levelFactory;

    public SquareLineGenerator(){
        PacManSprites pacManSprites = new PacManSprites();
        this.boardFactory = new BoardFactory(pacManSprites);
        this.levelFactory = new LevelFactory(pacManSprites, new GhostFactory(pacManSprites));
    }

    public Square[] generateSquareLine(Collection<Square> neighbourSquares){
        Square[] newSquareLine = new Square[neighbourSquares.size()];
        int i = 0;
        for(Square neighbour : neighbourSquares){
            //TODO : find a way to generate squares
            newSquareLine[i] = this.boardFactory.createGround();
            this.levelFactory.createPellet().occupy(newSquareLine[i]);
            i++;
        }
        return newSquareLine;
    }
}
