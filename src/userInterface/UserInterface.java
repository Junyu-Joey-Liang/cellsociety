package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static userInterface.VisualizationConstants.ERROR_MSG_FONT_SIZE;
import static userInterface.VisualizationConstants.FILE_PATH_FONT_SIZE;

/**
 * UserInterface class that manages all the visualization and user interactions of the program.
 */
public class UserInterface {
    private static final Font TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);
    private static final Font ERROR_FONT = Font.font("Arial", FontWeight.BOLD, ERROR_MSG_FONT_SIZE);
    private static final Font FILE_FONT = Font.font("Arial", FontWeight.BOLD, FILE_PATH_FONT_SIZE);
    private static final int ERROR_MSG_TIME_LIMIT = 5;
    private static final int SPACING = 15;
    private static final double PADDING_TOP = 20;
    private static final double PADDING_OTHER = 50;
    private AbstractGridView myGridView;
    private VBox firstColumn;
    private ControlsManager myControlsManager;
    private int numOfCols;
    private int numOfRows;
    private Text simulationTitle;
    private int errorMsgTimer = -1;
    private Text errorMsg;
    private Text simulationFilePath;
    private PauseButton pauseButton;


    /**
     * Constructor
     *
     * @param simulationName Name of the simulation
     */
    public UserInterface(String simulationName) {
        this.simulationTitle = new Text(simulationName);
        simulationTitle.setFont(TITLE_FONT);
        myGridView = new RectangleGridView(numOfCols, numOfRows);
        myControlsManager = new ControlsManager();
    }

    /**
     * Initialize the scene of the simulation
     *
     * @return scene of simulation
     */
    public Group setScene() {
        var root = new Group();
        firstColumn = new VBox(SPACING);
        HBox hBox = new HBox(SPACING);
        hBox.setPadding(new Insets(PADDING_TOP, PADDING_OTHER, PADDING_OTHER, PADDING_OTHER));
        firstColumn.getChildren().addAll(simulationTitle, myGridView.getMyView());
        hBox.getChildren().add(firstColumn);
        hBox.getChildren().add(myControlsManager.getMyPane());
        root.getChildren().add(hBox);
        return root;
    }

    /**
     * update the view of the simulation at each simulation step
     */
    public void update() {
        myGridView.updateGrid();
        if (this.errorMsgTimer != -1) {
            errorMsgTimer++;
        }
        if (this.errorMsgTimer > ERROR_MSG_TIME_LIMIT) {
            this.getMyControlsManager().getMyConstantCol().getChildren().remove(this.errorMsg);
            this.errorMsgTimer = -1;
        }
    }

    /**
     * initialize and visualization simulation control
     */
    public void addSimulationControls() {
        this.getMyGridView().getGridManager().getCellGrid().initializeControlPanel();
        this.getMyControlsManager().getMySimulationCol().getChildren().clear();
        this.getMyControlsManager().getMySimulationCol().getChildren().add(this.getMyGridView().getGridManager().getCellGrid().getControlPanel().getMyColPane());
    }

    /**
     * get grid view for this simulation
     *
     * @return grid view for this simulation
     */
    public AbstractGridView getMyGridView() {
        return myGridView;
    }


    /**
     * get controls manager
     *
     * @return controls manager
     */
    public ControlsManager getMyControlsManager() {
        return myControlsManager;
    }

    /**
     * change the title of the simulation
     *
     * @param simulationTitle new simulation title
     */
    public void changeTitle(String simulationTitle) {
        this.simulationTitle.setText(simulationTitle);
    }

    /**
     * set number of rows of the grid
     *
     * @param n number of rows to set
     */
    public void setNumOfRows(int n) {
        numOfRows = n;
        this.getMyGridView().setNumOfRows(n);
    }

    /**
     * set number of cols of the grid
     *
     * @param n number of cols to set
     */
    public void setNumOfCols(int n) {
        numOfCols = n;
        this.getMyGridView().setNumOfCols(n);
    }

    /**
     * Display the specified error message
     *
     * @param errorMessage error message to display
     */
    public void displayErrorMsg(String errorMessage) {
        if (errorMsgTimer != -1) {
            this.getMyControlsManager().getMyConstantCol().getChildren().remove(this.errorMsg);
        }
        this.errorMsg = new Text(errorMessage);
        this.errorMsg.setFont(ERROR_FONT);
        this.errorMsg.setFill(Color.ORANGE);
        this.getMyControlsManager().getMyConstantCol().getChildren().add(this.errorMsg);
        this.errorMsgTimer = 0;
    }

    /**
     * Display simulation file path for the users
     *
     * @param filePath simulation file path to display
     */
    public void displaySimulationFilePath(String filePath) {
        this.myControlsManager.getMyPane().getChildren().remove(this.simulationFilePath);
        this.simulationFilePath = new Text(filePath);
        this.simulationFilePath.setFont(FILE_FONT);
        this.simulationFilePath.setFill(Color.CORNFLOWERBLUE);
        this.myControlsManager.getMyPane().getChildren().add(this.simulationFilePath);
    }

    /**
     * Change the shape of the cellls in the grid
     *
     * @param type cell shape to change to
     */
    public void setCellShape(CellShapeType type) {
        if (type == CellShapeType.RECTANGLE) {

            myGridView = new RectangleGridView(numOfRows, numOfCols);
        }
        if (type == CellShapeType.TRIANGLE) {
            myGridView = new TriangleGridView(numOfRows, numOfCols);
        }
        if (type == CellShapeType.HEXAGON) {
            myGridView = new HexagonGridView(numOfRows, numOfCols);
        }
        myGridView.generateBlankGrid();
        firstColumn.getChildren().clear();
        firstColumn.getChildren().addAll(simulationTitle, myGridView.getMyView());
    }

    /**
     * get the pause button for the simulation
     *
     * @return  pause button for the simulation
     */
    public PauseButton getPauseButton() {
        return pauseButton;
    }

    /**
     * set the pause button for the simulation
     *
     * @param button pause button for the simulation
     */
    public void setPauseButton(PauseButton button) {
        pauseButton = button;
    }
}
