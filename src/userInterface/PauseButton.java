package userInterface;

import javafx.animation.Animation;

/**
 * Class for pause button int he simulation
 */
public class PauseButton extends SimulationButton {
    private boolean paused = false;
    private Animation myAnimation;
    private String pauseName;
    private String resumeName;

    /**
     * constructor for pause button
     *
     * @param animation animation of the program
     * @param pause name of the button to pause
     * @param resume name of the button to resume
     */
    public PauseButton(Animation animation, String pause, String resume) {
        super(pause);
        this.pauseName = pause;
        this.resumeName = resume;
        this.myAnimation = animation;
        this.setOnAction(value -> buttonAction());
    }

    /**
     * reset the pause button to initial state
     */
    public void resetPauseButton() {
        paused = false;
        this.setText(pauseName);
    }

    private void buttonAction() {
        paused = !paused;
        if (paused) {
            myAnimation.pause();
            this.setText(resumeName);
        } else {
            myAnimation.play();
            this.setText(pauseName);
        }
    }


}
