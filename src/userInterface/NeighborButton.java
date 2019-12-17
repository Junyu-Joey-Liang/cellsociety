package userInterface;

import static userInterface.VisualizationConstants.CENTER_COLOR;
import static userInterface.VisualizationConstants.NEIGHBOR_STYLE;

/**
 * individual neighbor selector button class.
 */
public class NeighborButton extends SimulationButton {
    public static final int CENTER = 4;
    private static final int BUTTON_WIDTH = 40;

    private static final String EMPTYSTYLE = "-fx-border-color: black;" + "-fx-background-color: White";
    private static final String CLICKEDSTYLE = "-fx-border-color: black;" + "-fx-background-color: LightBlue";
    private int idx;
    private boolean chosen = false;

    protected NeighborButton(int idx) {
        super("");
        this.idx = idx;
        this.setPrefSize(BUTTON_WIDTH, BUTTON_WIDTH);
        this.setStyle(NEIGHBOR_STYLE);
        if (idx == CENTER) {
            this.setStyle(CENTER_COLOR);
        }
    }

    /**
     * Get the idx of this neighbor button
     *
     * @return idx of this neighbor button
     */
    public int getIdx() {
        return idx;
    }

    /**
     * Get whether this neighbor is chosen
     *
     * @return whether this neighbor is chosen
     */
    public boolean isChosen() {
        return chosen;
    }

    /**
     * Flip the chosen bit for this neighbor button.
     */
    public void flipChosen() {
        if (idx == CENTER) {
            return;
        }
        chosen = !chosen;
        if (chosen) {
            this.setStyle(CLICKEDSTYLE);
        } else {
            this.setStyle(EMPTYSTYLE);
        }
    }
}
