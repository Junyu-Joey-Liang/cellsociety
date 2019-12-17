package simulation;

import userInterface.CellShapeType;
import userInterface.ControlPanel;
import userInterface.NeighborButton;
import userInterface.SimulationSlider;
import utils.Point;
import userInterface.NeighborButtonGrid;
import java.util.HashMap;
import java.util.Map;

import static userInterface.NeighborButtonGrid.NUM_COL;
import static userInterface.NeighborButtonGrid.NUM_ROW;
import static userInterface.VisualizationConstants.NEIGHBOR_CHOSEN;
import static userInterface.VisualizationConstants.NEIGHBOR_STYLE;

public abstract class CellGrid {

    private static final String INITIAL_CONFIG = "11111111";

    private Map<Point, Cell> gridOfCells = new HashMap<>();
    private int[] neighbor = new int[NUM_COL * NUM_ROW];
    private GridLimit gridLimit = GridLimit.FINITE;
    private CellShapeType cellShapeType = CellShapeType.RECTANGLE;
    private int numOfRows;
    private int numOfCols;
    private ControlPanel controlPanel;
    private NeighborManager neighborManager;
    private String neighborConfig = INITIAL_CONFIG;

    public CellGrid(int numRows, int numCols) {
        this.numOfRows = numRows;
        this.numOfCols = numCols;
        this.controlPanel = new ControlPanel();
        createNeighborManager();
    }
    /**
     * Initializes the control panel. This method is called by GridManager when cellGrid object is formed,
     * to put the right sliders on the screen.
     */
    public void initializeControlPanel() {
        this.controlPanel.getMyColPane().getChildren().clear();
    }

    /**
     * Called internally by individual cellGrid inside the individual implementation of initializeControlPanel.
     */
    protected void initializeControlPanel(String controlsType) {
        this.getControlPanel().getMyColPane().getChildren().clear();
        if (this.cellShapeType == CellShapeType.RECTANGLE) {
            addNeighborButton();
        }
        String[] controlsList = getControlPanel().getResourceBundle().getString(controlsType).split(",");
        for (String controlType : controlsList) {
            SimulationSlider segregationSlider = createSliderFromResourceFile(controlType);
            segregationSlider.getMySlider().valueProperty().addListener(e ->
                    sliderAction(controlType, (double) Math.round(segregationSlider.getMySlider().getValue())));
        }
    }

    /**
     * Adds a grid of nine rectangular cells to enable user to pick neighbors for rectangular cells.
     */
    private void addNeighborButton() {
        NeighborButtonGrid neighborButtonGrid = new NeighborButtonGrid();
        this.getControlPanel().getMyColPane().getChildren().add(neighborButtonGrid.getMyView());
        for (NeighborButton button : neighborButtonGrid.getButtonList()) {
            button.setOnAction(e -> {
                if (button.getIdx() != NeighborButton.CENTER) {
                    button.flipChosen();
                    if (button.isChosen()) {
                        button.setStyle(NEIGHBOR_CHOSEN);
                        neighbor[button.getIdx()] = 1;
                    } else {
                        button.setStyle(NEIGHBOR_STYLE);
                        neighbor[button.getIdx()] = 0;
                    }
                    setNeighborConfig(makeNeighborString());
                    System.out.println(neighborConfig);
                }
            });
        }
    }

    private String makeNeighborString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM_COL; i++) {
            sb.append(neighbor[i]);
        }
        sb.append(neighbor[(NUM_ROW - 1) * NUM_COL - 1]);
        for (int i = NUM_COL * NUM_ROW - 1; i > NUM_COL * (NUM_ROW - 1) - 1; i--) {
            sb.append(neighbor[i]);
        }
        sb.append(neighbor[NUM_COL]);
        return sb.toString();
    }

    /**
     * Must be overrided by all individual cellGrid objects. Type is the slider that the user moved,
     * and inputPercentage is the value chosen by the user.
     */
    protected abstract void sliderAction(String type, double inputPercentage);

    /**
     * Creates the right types of sliders for each simulation
     */
    public SimulationSlider createSliderFromResourceFile(String controlType) {
        int minVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "min"));
        int maxVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "max"));
        int defaultVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "default"));
        String title = getControlPanel().getResourceBundle().getString(controlType + "." + "title");
        SimulationSlider slider = new SimulationSlider(minVal, maxVal, defaultVal, title);
        controlPanel.getMyColPane().getChildren().add(slider.getvBox());
        return slider;
    }

    /**
     * Must be overrided by all individual cellGrid objects. Called by gridManager object to initialize the grid
     * according to configuration written in the xml file.
     */
    public abstract void initializeGrids(Map<Point, CellState> configMap);

    /**
     * Must be called at each cycle. Cellgrid makes the cell check its neighbors, and the cell will decide
     * its next state according to its hidden internal implementations.
     */
    //Iterates through all cells and change nextState
    public void checkAllCells() {
        for(Cell cell : gridOfCells.values()) {
            cell.check();
        }
    }

    /**
     * Must be called after checkAllCells(). The states of cells are updated.
     */
    public void changeAllCells() {
        for(Cell cell : gridOfCells.values()) {
            cell.changeState();
        }
        cellGridExpand();
    }

    /**
     * Method for graph implementation. Returns a map that holds the population of each state.
     */
    public Map<CellState,Integer> countStates(){
        Map<CellState,Integer> stateCounts = new HashMap<>();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                if (!stateCounts.containsKey(this.stateOfCellAtPoint(row,col))){
                    stateCounts.put(this.stateOfCellAtPoint(row,col),1);
                }
                else{
                    stateCounts.replace(this.stateOfCellAtPoint(row,col),stateCounts.get(this.stateOfCellAtPoint(row,col))+1);
                }
            }
        }
        return stateCounts;
    }

    /**
     * If there is a cell at this point, it will now be 'empty', which is the default state. If there is no cell
     * at the point, there will now be an empty cell at the point.
     */
    public abstract void addEmptyStateToCell(int row, int col);

    /**
     * Must be called for initializing by gridView.
     */
    public void assignNeighborsToEachCell() {
        createNeighborManager();
        neighborManager.assignAllNeighbors(gridOfCells, numOfRows, numOfCols);
    }

    /**
     * Method for setting different types of neighbor configuration;
     */
    public void setNeighborConfig(String neighborConfig) {
        this.neighborConfig = neighborConfig;
        assignNeighborsToEachCell();
    }

    /**
     * Method for infinite grids. If the user chose the grid to be infinite, the grid will expand
     * when there is a nonEmpty cell at the edge.
     */
    protected void cellGridExpand() {
        //only executed when gridLimit is Infinite
        if (gridLimit != GridLimit.INFINITE) {
            return;
        }
        boolean expand = false;
        if (!isColEmpty(0)) {
            expand = true;
            addCol(true);
        }
        if (!isColEmpty(getNumOfCols() - 1)) {
            expand = true;
            addCol(false);
        }
        if (!isRowEmpty(0)) {
            expand = true;
            addRow(true);
        }
        if (!isRowEmpty(getNumOfRows() - 1)) {
            expand = true;
            addRow(false);
        }
        if (expand) {
            assignNeighborsToEachCell();
        }
    }

    public Map<Point, Cell> getGridOfCells() {
        return gridOfCells;
    }

    /**
     * Clears the map. Used for re-initializing cellGrid.
     */
    public void clearMap() {
        gridOfCells.clear();
    }

    /**
     * Called externally to put a specific cell to a point.
     */
    public void addToGridOfCells(Point point, Cell cell) {
        gridOfCells.put(point, cell);
    }

    public CellState stateOfCellAtPoint(int row, int col) {
        return gridOfCells.get(new Point(row, col)).getState();
    }

    /**
     * Changes the state of the cell when the user clicks the cell.
     */
    public void setStateOfCellAtPointOnClick(int row, int col) {
        gridOfCells.get(new Point(row, col)).setNextStateOnClick();
    }

    public NeighborManager getNeighborManager() {
        return neighborManager;
    }

    /**
     * Called when the shape of the cell changes. Neighbors are re-assigned.
     */
    public void setCellShapeType(CellShapeType cellShapeType) {
        this.cellShapeType = cellShapeType;
        assignNeighborsToEachCell();
    }
    /**
        * Called when the edge type of the cell changes. Neighbors are re-assigned.
     */
    public void setGridLimit(GridLimit gridLimit) {
        this.gridLimit = gridLimit;
        assignNeighborsToEachCell();
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    /**
     * Called when user changes the number of rows. Map is formed again and neighbors are re-assigned.
     */
    public void userSetNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
        createEmptyMap();
        assignNeighborsToEachCell();
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    /**
     * Called when user changes the number of rows. Map is formed again and neighbors are re-assigned.
     */
    public void userSetNumOfCols(int numOfCols) {
        this.numOfCols = numOfCols;
        createEmptyMap();
        assignNeighborsToEachCell();

    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    private boolean isRowEmpty(int rowNum) {
        for (int col = 0; col < numOfCols; col++) {
            Cell cell = cellFromPoint(rowNum, col);
            if (cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fills the grid with empty cells. Must be called at the start of initializeGrid().
     */
    protected void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }

    private boolean isColEmpty(int colNum) {
        for (int row = 0; row < numOfRows; row++) {
            Cell cell = cellFromPoint(row, colNum);
            if (cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    private void createEmptyRow(int row) {
        for (int col = 0; col < getNumOfCols(); col++) {
            addEmptyStateToCell(row, col);
        }
    }

    private void createEmptyCol(int col) {
        for (int row = 0; row < getNumOfRows(); row++) {
            addEmptyStateToCell(row, col);
        }
    }

    private void addRow(boolean top) {
        numOfRows++;
        createEmptyRow(numOfRows - 1);
        if(top) {
            for (int i = numOfRows - 1; i >= 1; i--) {
                for (int j = 0; j < numOfCols; j++) {
                    gridOfCells.put(new Point(i, j), cellFromPoint(i - 1, j));
                }
            }
            createEmptyRow(0);
        }
    }

    private void addCol(boolean left) {
        numOfCols++;
        createEmptyCol(numOfCols - 1);
        if(left) {
            for (int col = numOfCols - 1; col >= 1; col--) {
                for (int row = 0; row < numOfRows; row++) {
                    gridOfCells.put(new Point(row, col), cellFromPoint(row, col - 1));
                }
            }
            createEmptyCol(0);
        }
    }

    private Cell cellFromPoint(int row, int col) {
        return getGridOfCells().get(new Point(row, col));
    }

    /**
     * Creates a new NeighborManger object according to the configuration of neighbors, cell shape, and edge type.
     * This object will be used for assigning neighbors correctly.
     */
    protected void createNeighborManager() {
        neighborManager = new NeighborManager(neighborConfig, cellShapeType, gridLimit == GridLimit.TOROIDAL);
    }
}