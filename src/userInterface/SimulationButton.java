package userInterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

/**
 * Simulation button class
 */
public class SimulationButton extends Button {
    /**
     * Constructor for simulation button, sets the font and name
     *
     * @param name name of the button
     */
    public SimulationButton(String name) {
        super();
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText(name);
    }
}
