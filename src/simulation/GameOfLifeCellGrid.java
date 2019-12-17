package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameOfLifeCellGrid extends CellGrid {

    private static final int INITIALALIVE = 5;
    private int initialAliveNum = INITIALALIVE;
    private static final int HUNDRED = 100;

    private List<Cell> deadCells = new ArrayList<>();

    public GameOfLifeCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeControlPanel() {
        initializeControlPanel("GameofLifeControls");
    }

    @Override
    protected void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        this.initialAliveNum = (int) (getGridOfCells().size() * inputPercentage / HUNDRED);

        System.out.println(type);
        createEmptyMap();
        randomlyInitialize();
        assignNeighborsToEachCell();
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
        addToGridOfCells(point, new GameOfLifeCell(row, col, CellState.DEAD));
        deadCells.add(getGridOfCells().get(point));
    }

    @Override
    protected void createEmptyMap() {
        clearMap();
        deadCells.clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }

    private void randomlyInitialize() {
        createEmptyMap();
        for (int i = 0; i < initialAliveNum; i++) {
            Cell cell = deadCells.get((int) (Math.random() * deadCells.size()));
            deadCells.remove(cell);
            System.out.println(deadCells.size());
            cell.setState(CellState.ALIVE);
        }
    }
}
