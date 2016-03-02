package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.util.DoubleLinkedListWithWindow;
import nl.tudelft.jpacman.util.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Angeall on 27/02/2016.
 * Board with infinite squares, to be generated on the fly
 */
public class InfiniteBoard extends Board {
    public final int BOARD_LIMIT = 8;
    protected DoubleLinkedListWithWindow<DoubleLinkedListWithWindow<Square>> columns =
            new DoubleLinkedListWithWindow<>();
    protected int leftOffset = 0;
    protected int upOffSet = 0;
    protected boolean toExtendLeft = false;
    protected boolean toExtendRight = false;
    protected boolean toExtendTop = false;
    protected boolean toExtendBottom = false;
    protected int lastYMoved;
    protected int lastXMoved;
    /**
     * Creates a new infinite board.
     *
     * @param grid
     *                  The grid of squares with grid[x][y] being the square at column x, row y.
     */
    InfiniteBoard(Square[][] grid) {
        super(grid);
        for(Square[] column : grid){
            this.columns.addLast(new DoubleLinkedListWithWindow<>(Arrays.asList(column), 0, column.length-1));
            assert this.columns.getLast().size() == column.length;
        }
        this.columns.setWindow(0, columns.size()-1);
        assert this.columns.getLast() == this.columns.getWindowTail().getData();
        assert this.columns.size() == grid.length;
    }

    @Override
    public Square squareAt(int x, int y) {
        assert withinBorders(x, y);
        Square result = columns.getFromWindow(x).getFromWindow(y);
        assert result != null : "Follows from invariant.";
        // If there is a unit that is not a Pellet: maybe we should extend the board
        //     We do this here because this method is called each time that the board is refreshed
        result.getOccupants().stream().filter(unit -> !(unit instanceof Pellet)).forEach(unit -> {
            // If the unit is placed 3 columns away from an "unknown" zone, we should extend the columns
            if (x <= BOARD_LIMIT - columns.getWindowHeadIndex())
                toExtendLeft = true;
            else if (x >= getWidth() - BOARD_LIMIT + (columns.size() - columns.getWindowTailIndex()))
                toExtendRight = true;
            // If the unit is place 3 lines away from an "unknown" zone, we should extend the lines
            if (y <= BOARD_LIMIT - columns.getFirst().getWindowHeadIndex())
                toExtendTop = true;
            else if (y >= getHeight() - BOARD_LIMIT + (columns.getFirst().size() -
                    columns.getFirst().getWindowTailIndex()))
                toExtendBottom = true;
            // If a Player is located on the side of the board : slide the visible board
            if(unit instanceof Player){
                if(x != lastXMoved) {
                    if (x <= BOARD_LIMIT) {
                        lastXMoved = x;
                        moveLeftVisible();
                    } else if (x >= columns.getWindowSize() - 1 - BOARD_LIMIT) {
                        lastXMoved = x;
                        moveRightVisible();
                    }
                }
                if (y != lastYMoved) {
                    if(y <= BOARD_LIMIT) {
                        lastYMoved = y;
                        moveUpVisible();
                    }
                    else if(y >= columns.getFirst().getWindowSize() - 1 - BOARD_LIMIT){
                        lastYMoved = y;
                        moveDownVisible();
                    }
                }
            }
        });
        return result;
    }

    /**
     * This method is a test method, it accepts negative indices (e.g. the -1 line index represents a line that has
     *      been added after the creation of this board).
     * @param x The line index
     * @param y The column index
     * @return The square at the [x][y] position
     */
    public Square squareAtUnchecked(int x, int y) {
        x = x+leftOffset;
        y = y+upOffSet;
        return columns.get(x).get(y);
    }

    /**
     * Returns the visible width of this board, i.e. the amount of columns.
     *
     * @return The width of this board.
     */
    public int getWidth() {
        return columns.getWindowSize();
    }

    /**
     * Returns the visible height of this board, i.e. the amount of rows.
     *
     * @return The height of this board.
     */
    public int getHeight() {
        return columns.getFirst().getWindowSize();
    }



    @Override
    public boolean withinBorders(int x, int y){
        x += columns.getWindowHeadIndex();
        y += columns.getFirst().getWindowHeadIndex();
        return x >= columns.getWindowHeadIndex() && x <= columns.getWindowTailIndex() &&
                y >= columns.getFirst().getWindowHeadIndex() && y <= columns.getFirst().getWindowTailIndex();
    }

    /**
     * Add a new {@link Square} column to the right of the board
     * @param column
     *                  The new column of squares to insert at the right of the board
     */
    public void addColumnRight(Square[] column){
        assert column.length == this.columns.getLast().size();
        int initSize = columns.size();
        Node<Square> explorerNode = columns.getLast().getHead();
        DoubleLinkedListWithWindow<Square> newList = new DoubleLinkedListWithWindow<>(Arrays.asList(column),
                                                                                columns.getLast().getWindowHeadIndex(),
                                                                                columns.getLast().getWindowTailIndex());
        Node<Square> newListExplorerNode = newList.getHead();
        for(int x = 0; x<column.length; x++) {
            if (x != 0) {
                newListExplorerNode.getData().link(newListExplorerNode.getPrevious().getData(), Direction.NORTH);
            }
            if (x != column.length - 1){
                newListExplorerNode.getData().link(newListExplorerNode.getNext().getData(), Direction.SOUTH);
            }
            explorerNode.getData().link(newListExplorerNode.getData(), Direction.EAST);
            newListExplorerNode.getData().link(explorerNode.getData(), Direction.WEST);
            explorerNode = explorerNode.getNext();
            newListExplorerNode = newListExplorerNode.getNext();
        }
        this.columns.addLast(newList);
        assert columns.size() > initSize;
        this.toExtendRight = false;
    }

    /**
     * Add a new {@link Square} column to the left of the board
     * @param column
     *                 The new column of squares to insert at the left of the board
     */
    public void addColumnLeft(Square[] column){
        assert (column.length == this.columns.getFirst().size());
        Node<Square> explorerNode = columns.getFirst().getHead();
        assert explorerNode.getPrevious() == null;
        for(int x = 0; x<column.length; x++) {
            if (x != 0) {
                column[x].link(column[x-1], Direction.NORTH);
            }
            if (x != column.length - 1){
                column[x].link(column[x+1], Direction.SOUTH);
            }
            explorerNode.getData().link(column[x], Direction.WEST);
            column[x].link(explorerNode.getData(), Direction.EAST);
            explorerNode = explorerNode.getNext();
        }
        DoubleLinkedListWithWindow<Square> newList = new DoubleLinkedListWithWindow<>(Arrays.asList(column),
                columns.getFirst().getWindowHeadIndex(),
                columns.getFirst().getWindowTailIndex());
        this.columns.addFirst(newList);
        this.leftOffset++;
        this.toExtendLeft = false;
    }

    /**
     * Add a new {@link Square} line to the top of the board
     * @param line
     *              The new line of squares to insert at the top of the board
     */
    public void addLineTop(Square[] line){
        assert line.length == columns.size();
        int i = 0;
        Node<DoubleLinkedListWithWindow<Square>> columnExplorer = columns.getHead();
        DoubleLinkedListWithWindow<Square> column;
        while(columnExplorer != null){
            column = columnExplorer.getData();
            column.addFirst(line[i]);
            if(i != 0) {
                column.getFirst().link(columnExplorer.getPrevious().getData().getFirst(), Direction.WEST);
                columnExplorer.getPrevious().getData().getFirst().link(column.getFirst(), Direction.EAST);
            }
            column.getFirst().link(column.getHead().getNext().getData(), Direction.SOUTH);
            column.getHead().getNext().getData().link(column.getFirst(), Direction.NORTH);
            columnExplorer = columnExplorer.getNext();
            i++;
        }
        this.upOffSet++;
        this.toExtendTop = false;
    }

    /**
     * Add a new {@link Square} line to the bottom of the board
     * @param line
     *                  The new line of squares to insert at the bottom of the board
     */
    public void addLineBottom(Square[] line){
        assert line.length == columns.size();
        int i = 0;
        Node<DoubleLinkedListWithWindow<Square>> columnExplorer = columns.getHead();
        DoubleLinkedListWithWindow<Square> column;
        while(columnExplorer != null){
            column = columnExplorer.getData();
            column.addLast(line[i]);
            if(i != 0) {
                column.getLast().link(columnExplorer.getPrevious().getData().getLast(), Direction.WEST);
                columnExplorer.getPrevious().getData().getLast().link(column.getLast(), Direction.EAST);
            }
            column.getLast().link(column.getTail().getPrevious().getData(), Direction.NORTH);
            column.getTail().getPrevious().getData().link(column.getLast(), Direction.SOUTH);
            columnExplorer = columnExplorer.getNext();
            i++;
        }
        this.toExtendBottom = false;
    }

    /**
     * Get the leftmost column of the board (even if invisible)
     * @return The leftmost column of the board
     */
    public Collection<Square> getLeftColumn(){
        return columns.getFirst();
    }

    /**
     * Get the rightmost column of the board (even if invisible)
     * @return The rightmost column of the board
     */
    public Collection<Square> getRightColumn(){
        return columns.getLast();
    }

    /**
     * Get the bottom line of the board (even if invisible)
     * @return The bottom line of the board
     */
    public Collection<Square> getBottomLine(){
        return columns.stream().map(DoubleLinkedListWithWindow<Square>::getLast).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get the top line of the board (even if invisible)
     * @return The top line of the board
     */
    public Collection<Square> getTopLine(){
        return columns.stream().map(DoubleLinkedListWithWindow<Square>::getFirst).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Move the visible part of the board one line down
     */
    public void moveDownVisible() {
        assert this.columns.getFirst().getWindowTailIndex() != this.columns.getFirst().size() - 1;
        Node<DoubleLinkedListWithWindow<Square>> columnExplorer = columns.getHead();
        while (columnExplorer != null) {
            columnExplorer.getData().slideWindowRight();
            columnExplorer = columnExplorer.getNext();
        }
    }

    /**
     * Move the visible part of the board one line up
     */
    public void moveUpVisible(){
        assert this.columns.getFirst().getWindowHeadIndex() != 0;
        Node<DoubleLinkedListWithWindow<Square>> columnExplorer = columns.getHead();
        while (columnExplorer != null) {
            columnExplorer.getData().slideWindowLeft();
            columnExplorer = columnExplorer.getNext();
        }
    }

    /**
     * Move the visible part of the board one column left
     */
    public void moveLeftVisible(){
        assert this.columns.getWindowHeadIndex() != 0;
        this.columns.slideWindowLeft();
    }

    /**
     * Move the visible part of the board one column right
     */
    public void moveRightVisible(){
        assert this.columns.getWindowTailIndex() != this.columns.size()-1;
        this.columns.slideWindowRight();
    }

    public boolean isToExtendLeft() {
        return toExtendLeft;
    }

    public boolean isToExtendRight() {
        return toExtendRight;
    }

    public boolean isToExtendTop() {
        return toExtendTop;
    }

    public boolean isToExtendBottom() {
        return toExtendBottom;
    }
}
