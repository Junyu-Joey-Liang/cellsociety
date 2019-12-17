package userInterface;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.SLIDER_FONT_SIZE;

/**
 * Simulation multiple choice drop-down class
 */
public class SimulationChoice {
    private static final int SPACING = 10;
    private ChoiceBox choiceBox;
    private HBox myHBox;

    /**
     * Constructor for simulation drop-down, sets the font and name, and choices
     *
     * @param choices choices in the drop down as String array
     * @param label title label fo the drop-down
     */
    public SimulationChoice(String[] choices, String label) {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(choices));
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(SLIDER_FONT_SIZE));
        myHBox = new HBox();
        myHBox.setSpacing(SPACING);
        this.myHBox.getChildren().add(myLabel);
        this.myHBox.getChildren().add(choiceBox);
    }

    /**
     * Get the choice box object
     *
     * @return the choice box object
     */
    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }

    protected HBox getMyHBox() {
        return myHBox;
    }


}
