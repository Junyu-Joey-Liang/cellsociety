package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cell {

    private CellState state;
    private CellState nextState;
    private Point coord; // x is row, y is col


    private List<Cell> cornerNeighbors;
    private List<Cell> edgeNeighbors;
    private Map<Point, Cell> neighbors = new HashMap<>();
    private Map<CellAttribute, Integer> attributes = new HashMap<>();

    private List<CellState> possibleStates = new ArrayList<>();

    public Cell(int row, int col, CellState state) {
        this.state = state;
        this.nextState = state;
        this.coord = new Point(row, col);

        cornerNeighbors = new ArrayList<>();
        edgeNeighbors = new ArrayList<>();
    }

    public CellState getNextState() {
        return nextState;
    }

    /**
     * Sets the next-state of the cell.
     */
    public void setNextState(CellState nextState) {
        this.nextState = nextState;
    }

    /**
     * Must be overrided by individual cell classes. Check neighbors and determine its next state.
     */
    public abstract void check();

    /**
     * Change state to next-state.
     */
    public abstract void changeState();

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    /**
     * Called by gridManager to assign neighbor. The cell will know which cell is its neighbor located
     * at a certain direction.
     */
    public void addNeighbor(Point direction, Cell cell) {
        neighbors.put(direction, cell);
    }

    public Map<Point, Cell> getNeighbors() {
        Map<Point, Cell> copyOfNeighbors = new HashMap<>();
        for (Point point : neighbors.keySet()) {
            copyOfNeighbors.put(point, neighbors.get(point));
        }
        return copyOfNeighbors;
    }

    /**
     * called when the cell needs to move.
     */
    public void moveToDifferentCell(Cell other) {
        nextState = other.getState();
        other.setNextState(state);
    }

    public int getRow() {
        return coord.getRow();
    }

    public void setRow(int row) {
        coord.setRow(row);
    }

    public int getCol() {
        return coord.getCol();
    }

    public void setCol(int col) {
        coord.setCol(col);
    }

    public void clearNeighbors() {
        clearEdgeNeighbors();
        clearCornerNeighbors();
        neighbors.clear();
    }

    public void clearEdgeNeighbors() {
        edgeNeighbors.clear();
    }

    public void clearCornerNeighbors() {
        cornerNeighbors.clear();
    }

    /**
     * Method to get the value of specific parameters of each individual cell class. This method was made
     * to prevent casting.
     */
    public void putAttribute(CellAttribute cellAttribute, int value) {
        attributes.put(cellAttribute, value);
    }

    public int getAttribute(CellAttribute cellAttribute) {
        return attributes.get(cellAttribute);
    }

    public List<CellState> getPossibleStates() {
        List<CellState> states = new ArrayList<>();
        states.addAll(possibleStates);
        return states;
    }

    protected void setPossibleStates(List<CellState> possibleStates) {
        this.possibleStates = new ArrayList<>();
        this.possibleStates.addAll(possibleStates);
    }

    /**
     * Method called when the cell is clicked by user
     */
    protected void setNextStateOnClick() {
        System.out.println(possibleStates.size());
        if (!possibleStates.contains(state)) {
            System.out.println("Fix code");
            return;
        }
        int index = possibleStates.indexOf(getState());
        if (possibleStates.size() == index + 1) {
            index = -1;
        }
        setState(possibleStates.get(index + 1));
        //setNextState(getState());
    }

    @Override
    public boolean equals(Object other) {
        return other.getClass().equals(this.getClass()) && other.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return (coord.toString() + "Cell").hashCode();
    }

    protected int countNeighborsWithState(CellState state) {
        int count = 0;
        for (Cell cell : getNeighbors().values()) {
            if (cell.getState() == state) {
                count++;
            }
        }
        return count;
    }
}
