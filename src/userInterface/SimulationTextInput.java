package userInterface;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.SLIDER_FONT_SIZE;

/**
 * Class for simulation text input
 */
public class SimulationTextInput {
    private static final int SPACING = 5;
    private TextField textField;
    private VBox myVBox;

    /**
     * Constructor for text input, initialize textInput and label
     *
     * @param label label of text input
     */
    public SimulationTextInput(String label) {
        textField = new TextField();
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(SLIDER_FONT_SIZE));
        myVBox = new VBox(SPACING);
        myVBox.getChildren().add(myLabel);
        myVBox.getChildren().add(textField);
    }

    /**
     * Get view of text input and its label
     *
     * @return text input and its label
     */
    public VBox getMyVBox() {
        return myVBox;
    }

    /**
     * get textField object
     *
     * @return textField object
     */
    public TextField getTextField() {
        return textField;
    }
}
