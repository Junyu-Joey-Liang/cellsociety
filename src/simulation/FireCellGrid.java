package simulation;

import utils.Point;

import java.util.HashMap;
import java.util.Map;

public class FireCellGrid extends GameOfLifeCellGrid {
    private double probCatch;
    private Map myConfigMap = new HashMap<Point, CellState>();

    public FireCellGrid(int numRows, int numCols, double probCatch) {
        super(numRows, numCols);

        this.probCatch = probCatch;

    }

    @Override
    public void initializeControlPanel() {
        initializeControlPanel("FireControls");
    }

    @Override
    protected void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        // type: "PropCatch"
        this.probCatch = inputPercentage;
        createEmptyMap();
        initializeGrids(myConfigMap);
        assignNeighborsToEachCell();
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
            myConfigMap.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void assignNeighborsToEachCell() {
        createNeighborManager();
        getNeighborManager().assignEdgeNeighbors(getGridOfCells(), getNumOfRows(), getNumOfCols());
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new FireCell(row, col, CellState.TREE, probCatch));
    }
}