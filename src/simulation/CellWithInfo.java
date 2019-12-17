package simulation;

import utils.Point;

import java.util.List;
import java.util.Map;

/**
 * CellInfo is an extension of Cell and is used for simulations in which each cell needs a patch of ground
 * that stores information.
 */
public abstract class CellWithInfo extends Cell {
    private GridInfo myGridInfo;

    public CellWithInfo(int row, int col, CellState state) {
        super(row, col, state);
    }

    @Override
    public void check() {
        if (!myGridInfo.neighborAssigned()) {
            addNeighborsToMyGridInfo();
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
        myGridInfo.update();
    }

    public abstract void addOrderedNeighborDirections(List<Point> neighbors);

    public GridInfo getMyGridInfo() {return myGridInfo;}

    public void setMyGridInfo(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    /**
     * The only use of instanceOf and casting is in this method. I decided to keep this in there,
     * as I thought there may be a simulation in which the individuals can move to only certain cells in the grid.
     * In this case, the gridInfo of the cell should not have a connection with some cells (cells that is not an
     * object of CellWithInfo).
     */
    protected void addNeighborsToMyGridInfo() {
        Map<Point, Cell> myNeighbors = getNeighbors();

        for (Point direction : myNeighbors.keySet()) {
            Cell cell = myNeighbors.get(direction);

            if (!(cell instanceof CellWithInfo)) {
                return;
            }
            CellWithInfo cellWithInfo = (CellWithInfo) cell;
            myGridInfo.addNeighbor(direction, cellWithInfo.getMyGridInfo());
        }
    }
}
