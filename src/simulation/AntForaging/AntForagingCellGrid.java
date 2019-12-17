package simulation.AntForaging;

import simulation.CellGrid;
import simulation.CellState;
import utils.Point;

import java.util.Map;

public class AntForagingCellGrid extends CellGrid {

    private int maxAnt;
    private double evaporation;
    private double diffusion;
    //private double evaporation;

    public AntForagingCellGrid(int numRows, int numCols, int maxAnt, double evaporation, double diffusion) {
        super(numRows, numCols);
        this.maxAnt = maxAnt;
        this.evaporation = evaporation;
        this.diffusion = diffusion;
    }

    @Override
    protected void sliderAction(String type, double inputPercentage) {
        //to be implemented
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
        AntForagingCell cell = new AntForagingCell(row, col, CellState.EMPTY, maxAnt, evaporation, diffusion);
        addToGridOfCells(point, cell);
        cell.addOrderedNeighborDirections(getNeighborManager().getOrderedNeighborDirections(cell, getNumOfRows(), getNumOfCols()));
    }

    @Override
    protected void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}
