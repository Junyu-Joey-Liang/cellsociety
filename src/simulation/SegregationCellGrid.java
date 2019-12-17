package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegregationCellGrid extends GameOfLifeCellGrid {

    private static final double HUNDRED = 100.0;
    private static final double HALF = 50;
    private static final String AGENTRATIO = "AgentRatio";
    private static final String EMPTYPERCENT = "EmptyPercent";


    private int agentPercent;
    private double prob = HALF;
    private int emptyCellNumber;
    private List<Cell> emptyCells = new ArrayList<>();
    private List<Cell> dissatisfiedCells = new ArrayList<>();
    private List<Cell> newEmptyCells = new ArrayList<>();

    //more parameters to be added :
    public SegregationCellGrid(int numRows, int numCols, int agentPercent, int emptyPercent) {
        super(numRows, numCols);
        this.agentPercent = agentPercent;
        emptyCellNumber = (int) (numRows * numCols * emptyPercent / HUNDRED);
    }

    @Override
    public void initializeControlPanel() {
        initializeControlPanel("SegregationControls");
    }

    @Override
    protected void sliderAction(String type, double inputPercentage) {
        if (type.equals(AGENTRATIO)) {
            prob = inputPercentage;
        } else if (type.equals(EMPTYPERCENT)) {
            emptyCellNumber = (int) (getNumOfRows() * getNumOfCols() * inputPercentage / HUNDRED);
        } else {
            agentPercent = (int) inputPercentage;
        }
        emptyCells.clear();
        createEmptyMap();
        initializeGrids();
        assignNeighborsToEachCell();
        System.out.println(type);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        //If xml already has the whole configuration, grid will be filled accordingly
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            Cell notEmptyCell = getGridOfCells().get(entry.getKey());
            emptyCells.remove(notEmptyCell);
            notEmptyCell.setState(entry.getValue());
        }

        //If xml does not have the configuration so that all configuration will be randomly(while loop will be in an if statement),
        //according to the percentage of empty cells(parameter of constructor), randomly fills grid.
        initializeGrids();
    }

    public void initializeGrids() {
        while (emptyCells.size() > emptyCellNumber) {
            int index = (int) (emptyCells.size() * Math.random());
            Cell tempCell = emptyCells.get(index);
            emptyCells.remove(tempCell);

            tempCell.setState((Math.random() * HUNDRED > this.prob) ? CellState.FIRSTAGENT : CellState.SECONDAGENT);
        }
    }

    /**
     * Checks all cells. If the next state is dissatisfied, put that cell into a list.
     */
    @Override
    public void checkAllCells() {
        for (Cell cell : getGridOfCells().values()) {
            cell.check();
            if (cell.getNextState() == CellState.DISATISFIED) {
                dissatisfiedCells.add(cell);
            }
        }
    }

    /**
     * Swap dissatisfied cell with empty cell and change state to nextstate
     */
    @Override
    public void changeAllCells() {
        //swapping dissatisfied ones with a random empty one
        while (!dissatisfiedCells.isEmpty()) {
            Cell dissatisfiedCell = dissatisfiedCells.get(0);
            Cell emptyCell = (!emptyCells.isEmpty()) ? emptyCells.get((int) (Math.random() * emptyCells.size()))
                    : newEmptyCells.get((int) (Math.random() * newEmptyCells.size()));

            if (!emptyCells.isEmpty()) {
                emptyCells.remove(emptyCell);
            } else {
                newEmptyCells.remove(emptyCell);
            }
            emptyCell.setNextState(dissatisfiedCell.getState());
            dissatisfiedCells.remove(dissatisfiedCell);
            newEmptyCells.add(dissatisfiedCell);
            dissatisfiedCell.moveToDifferentCell(emptyCell);
            dissatisfiedCell.setState(CellState.EMPTY);
        }
        super.changeAllCells();
        dissatisfiedCells.clear();
        emptyCells.addAll(newEmptyCells);
        newEmptyCells.clear();
        cellGridExpand();
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        Cell segregationCell = new SegregationCell(row, col, CellState.EMPTY, agentPercent);
        addToGridOfCells(point, segregationCell);
        emptyCells.add(segregationCell);
    }
}
