package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercolationCellGrid extends GameOfLifeCellGrid {
    private static final int HUNDRED = 100;

    private List<Cell> openCells = new ArrayList<>();
    private int initPercolated = 0;
    private int initBlocked = 0;

    public PercolationCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeControlPanel() {
        initializeControlPanel("PercolationControls");
    }

    protected void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        // type: "PercentBlocked", "NumberPercolated"
        if (type.equals("PercentBlocked")) {
            this.initBlocked = (int) (inputPercentage * getGridOfCells().size() / HUNDRED);
        } else {
            this.initPercolated = (int) inputPercentage;
        }
        openCells.clear();
        createEmptyMap();
        randomlyInitializeGrids();
        assignNeighborsToEachCell();
        System.out.println(type);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new PercolationCell(row, col, CellState.OPEN));
        openCells.add(getGridOfCells().get(point));
    }

    private void randomlyInitializeGrids() {
        createEmptyMap();
        for (int i = 0; i < initBlocked; i++) {
            addToRandomCell(CellState.BLOCKED);
        }
        for (int i = 0; i < initPercolated; i++) {
            addToRandomCell(CellState.PERCOLATED);
        }
    }

    private void addToRandomCell(CellState state) {
        Cell openCell = openCells.get((int) (Math.random() * openCells.size()));
        openCell.setState(state);
        openCells.remove(openCell);
    }

    protected void createEmptyMap() {
        clearMap();
        openCells.clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}
