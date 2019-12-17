package userInterface;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import simulation.CellState;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.BLANK_GRID_COLOR;

/**
 * Abstract class for simulation cell grid visualizations. Contains the view and manager of simulation grid, and the simulation graph.
 */
public abstract class AbstractGridView {
    private static final int SPACING = 10;
    private static final int HUNDRED = 100;
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    public static final int GRID_WIDTH = Integer.parseInt(ResourceBundle.getBundle(RESOURCE_FILE_PATH).getString("GridWidth"));
    private static final int GRID_HEIGHT = Integer.parseInt(ResourceBundle.getBundle(RESOURCE_FILE_PATH).getString("GridHeight"));
    private GridManager gridManager;
    private GridPane gridPane;
    private SimulationGraph simulationGraph;
    private VBox myView;
    private int simulationRound = 0;
    private int gridHeight = GRID_HEIGHT;
    private int numOfRows;
    private int numOfCols;

    /**
     * Constructor class for abstract grid view. Initializes views of the grid.
     *
     * @param numOfRows number of rows in the grid
     * @param numOfCols number of cols in the grid
     */
    public AbstractGridView(int numOfRows, int numOfCols) {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.gridManager = new GridManager();
        initializeViews();
    }

    /**
     * Clear the view of the simulation grid, and render new display of current status of simulation grid.
     */
    public abstract void displayGrid();

    /**
     * Initialize the nodes according to current simulation grid status.
     */
    public abstract void createGrid();

    /**
     * Generate and display a blank grid.
     */
    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(this.GRID_WIDTH, this.gridHeight);
        shape.setFill(BLANK_GRID_COLOR);
        gridPane.add(shape, 0, 0);
    }

    /**
     * Initialize the simulation line chart graph for state percentages. Add a line for each state.
     */
    public void initializeSimulationGraph() {
        if (this.getGridManager().getCellGrid() != null) {
            List<CellState> stateList = this.gridManager.getStateList();
            for (int i = 0; i < stateList.size(); i++) {
                XYChart.Series line = new XYChart.Series();
                line.setName(stateList.get(i).toString());
                simulationGraph.addLine(line);
                updatePercentageForState(stateList.get(i), simulationRound, this.simulationGraph.seriesList.get(i));
            }
        }
    }

    /**
     * Reset the grid view and simulation graph to the start of the simulation.
     */
    public void resetGridView() {
        this.getGridManager().resetCellGrid();
        simulationGraph.getData().clear();
        simulationGraph.seriesList.clear();
        simulationRound = 0;
        initializeSimulationGraph();
        displayGrid();
    }

    /**
     * Get the grid manager of the simulation.
     *
     * @return grid manager of the simulation
     */
    public GridManager getGridManager() {
        return gridManager;
    }

    protected int getNumOfRows() {
        return numOfRows;
    }

    protected int getNumOfCols() {
        return numOfCols;
    }

    private void initializeViews() {
        gridPane = new GridPane();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        this.simulationGraph = new SimulationGraph(xAxis, yAxis);
        this.myView = new VBox(SPACING);
        this.myView.getChildren().addAll(this.gridPane, this.simulationGraph);
    }

    private void updateSimulationGraph() {
        simulationRound += 1;
        List<CellState> stateList = this.gridManager.getStateList();
        for (int i = 0; i < this.simulationGraph.seriesList.size(); i++) {
            CellState state = stateList.get(i);
            updatePercentageForState(state, simulationRound, this.simulationGraph.seriesList.get(i));
        }
    }

    private void updatePercentageForState(CellState state, int round, XYChart.Series line) {
        Map<CellState, Integer> statesCount = this.gridManager.getCellGrid().countStates();
        statesCount.putIfAbsent(state, 0);
        double percent = ((double) statesCount.get(state) / (getNumOfCols() * getNumOfRows())) * HUNDRED;
        line.getData().add(new XYChart.Data<>(round, percent));
    }
    void updateGrid() {
        if (gridManager == null || gridManager.getCellGrid() == null) {
            return;
        }
        gridManager.updateCellGrid();
        updateSimulationGraph();
        displayGrid();
    }

    GridPane getGridPane() {
        return gridPane;
    }

    void setNumOfRows(int n) {
        numOfRows = n;
    }

    void setNumOfCols(int n) {
        numOfCols = n;
    }

    int getGridHeight() {
        return this.gridHeight;
    }

    void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    VBox getMyView() {
        return myView;
    }
}

