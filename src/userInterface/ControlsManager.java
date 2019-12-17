package userInterface;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Manager for all control objects in the simulation
 */
public class ControlsManager {
    private static final int SPACING = 15;
    private static final Insets PADDING = new Insets(70, 50, 50, 50);
    private HBox myPane;
    private VBox myConstantCol;
    private VBox mySimulationCol;

    /**
     * Constructor, initializes the view
     */
    public ControlsManager() {
        myPane = new HBox(SPACING);
        myPane.setPadding(PADDING);
        myConstantCol = new VBox(SPACING);
        mySimulationCol = new VBox(SPACING);
        myPane.getChildren().add(myConstantCol);
        myPane.getChildren().add(mySimulationCol);
    }


    /**
     * Get col pane of general controls
     *
     * @return col pane of general controls
     */
    public VBox getMyConstantCol() {
        return myConstantCol;
    }

    /**
     * Get col pane of simulation-specific controls
     *
     * @return col pane of simulation-specific controls
     */
    public VBox getMySimulationCol() {
        return mySimulationCol;
    }

    /**
     * Get the pane for all controls
     *
     * @return pane of all controls
     */
    public HBox getMyPane() {
        return myPane;
    }

    /**
     * add slider to controls
     *
     * @param slider slide to add to controls
     */
    public void addSlider(SimulationSlider slider) {
        myConstantCol.getChildren().add(slider.getvBox());
    }

    /**
     * add choice box to controls
     *
     * @param choice choice box to add to controls
     */
    public void addChoiceBox(SimulationChoice choice) {
        myConstantCol.getChildren().add(choice.getMyHBox());
    }


    /**
     * add button to controls
     *
     * @param button button to add to controls
     */
    public void addButton(SimulationButton button) {
        myConstantCol.getChildren().add(button);
    }
}
