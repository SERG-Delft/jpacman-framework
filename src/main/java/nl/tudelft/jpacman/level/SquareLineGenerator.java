package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Angeall on 02/03/2016.
 */
public class SquareLineGenerator {
    protected static int verticalWait = 0;
    protected boolean canCreateVerticalPath = false;

    protected BoardFactory boardFactory;
    protected LevelFactory levelFactory;

    public SquareLineGenerator(){
        PacManSprites pacManSprites = new PacManSprites();
        this.boardFactory = new BoardFactory(pacManSprites);
        this.levelFactory = new LevelFactory(pacManSprites, new GhostFactory(pacManSprites));
    }

    public Square[] generateSquareLine(Collection<Square> neighbourSquares, InfiniteLevel level){
        Square[] newSquareLine = new Square[neighbourSquares.size()];
        int i = 0;
        Random randomizer = new Random();
        if(verticalWait > 0) verticalWait--;
        int horizontalWait = 0;
        boolean justCreatedAnHorizontalSquare = false;
        for(Square neighbour : neighbourSquares){
            if(horizontalWait > 0) horizontalWait--;
            if(neighbour instanceof BoardFactory.Ground && horizontalWait == 0 &&
                    (!justCreatedAnHorizontalSquare || verticalWait == 0)) {
                newSquareLine[i] = this.boardFactory.createGround();
                int randInt = randomizer.nextInt(500);
                if(randInt < 498) this.levelFactory.createPellet().occupy(newSquareLine[i]);
                else {
                    NPC ghost =  this.levelFactory.createGhost();
                    ghost.occupy(newSquareLine[i]);
                    level.putNewGhosst(ghost);
                }
                if(verticalWait == 0) {
                    canCreateVerticalPath = true;
                    verticalWait = randomizer.nextInt(3) + 1;
                }
                justCreatedAnHorizontalSquare = true;
                horizontalWait = randomizer.nextInt(3) + 1;
            }
            else if (canCreateVerticalPath) {
                newSquareLine[i] = this.boardFactory.createGround();
                int randInt = randomizer.nextInt(500);
                if(randInt < 498) this.levelFactory.createPellet().occupy(newSquareLine[i]);
                else this.levelFactory.createGhost().occupy(newSquareLine[i]);
            }
            else {
                newSquareLine[i] = this.boardFactory.createWall();
            }
            i++;
        }
        canCreateVerticalPath = false;
        return newSquareLine;
    }
}
