package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.InfiniteBoard;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.NPC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Angeall on 02/03/2016.
 *
 * A level containing an infinite board
 */
public class InfiniteLevel extends Level {
    /**
     * The grid generator to extend the board of the level
     */
    private SquareGridGenerator generator;
    /**
     * Creates a new level for the board.
     *
     * @param b              The board for the level.
     * @param ghosts         The ghosts on the board.
     * @param startPositions The squares on which players start on this board.
     * @param collisionMap   The collection of collisions that should be handled.
     */
    public InfiniteLevel(InfiniteBoard b, List<NPC> ghosts, List<Square> startPositions, CollisionMap collisionMap) {
        super(b, ghosts, startPositions, collisionMap);
        this.generator = new SquareGridGenerator();
    }

    /**
     * Moves the unit into the given direction if possible and handles all
     * collisions.
     * Check at each move if the infinite board needs to be extended or not.
     *
     * @param unit
     *            The unit to move.
     * @param direction
     *            The direction to move the unit in.
     */
    @Override
    public void move(Unit unit, Direction direction) {
        super.move(unit, direction);
        SquareGridGenerator.GridAndGhosts gridAndGhosts;
        ArrayList<NPC> newGhosts = new ArrayList<>();
        // We check if the board must be extended or not.
        if(getInfiniteBoard().isToExtendLeft()){
            gridAndGhosts = generator.generateSquareGrid(SquareGridGenerator.LEFT,
                                                         getInfiniteBoard().getCurrentWidth(),
                                                         getInfiniteBoard().getCurrentHeight());
            getInfiniteBoard().addGridLeft(gridAndGhosts.getGrid());
            newGhosts.addAll(gridAndGhosts.getGhosts());
        }
        if(getInfiniteBoard().isToExtendRight()){
            gridAndGhosts = generator.generateSquareGrid(SquareGridGenerator.RIGHT,
                                                         getInfiniteBoard().getCurrentWidth(),
                                                         getInfiniteBoard().getCurrentHeight());
            getInfiniteBoard().addGridRight(gridAndGhosts.getGrid());
            newGhosts.addAll(gridAndGhosts.getGhosts());
        }
        if(getInfiniteBoard().isToExtendTop()){
            gridAndGhosts = generator.generateSquareGrid(SquareGridGenerator.TOP,
                                                         getInfiniteBoard().getCurrentWidth(),
                                                         getInfiniteBoard().getCurrentHeight());
            getInfiniteBoard().addGridTop(gridAndGhosts.getGrid());
            newGhosts.addAll(gridAndGhosts.getGhosts());
        }
        if(getInfiniteBoard().isToExtendBottom()){
            gridAndGhosts = generator.generateSquareGrid(SquareGridGenerator.BOTTOM,
                                                         getInfiniteBoard().getCurrentWidth(),
                                                         getInfiniteBoard().getCurrentHeight());
            getInfiniteBoard().addGridBottom(gridAndGhosts.getGrid());
            newGhosts.addAll(gridAndGhosts.getGhosts());
        }
        newGhosts.forEach(this::putNewGhost);
    }

    /**
     * Puts and start a new ghost inside the level
     * @param ghost The ghost to add
     */
    public final void putNewGhost(NPC ghost){
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.execute(new NpcMoveTask(service, ghost));
        this.npcs.put(ghost, service);
    }

    /**
     * Removes a ghost from the board
     * @param ghost The ghost to remove
     */
    public final NPC removeGhost(NPC ghost){
        this.npcs.get(ghost).shutdownNow();
        this.npcs.remove(ghost);
        return ghost;
    }

    @Override
    public int remainingPellets(){
        return 100;
    }

    /**
     * Get the instance of the infinite board of the level
     * @return The infinite board of the level
     */
    public InfiniteBoard getInfiniteBoard(){
        return (InfiniteBoard) super.getBoard();
    }
}
