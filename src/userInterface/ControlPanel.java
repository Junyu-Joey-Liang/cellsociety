package userInterface;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * Control panel that wraps a column of controls.
 */
public class ControlPanel {
    private static final double PADDING_TOP = 100;
    private static final double PADDING_OTHER = 0;
    private static final String RESOURCE_FILE_PATH = "resources/ControlResources";
    private static final int SPACING = 15;
    private ResourceBundle resourceBundle;
    private VBox myColPane;

    /**
     * Constructor, initializes the view.
     */
    public ControlPanel() {
        myColPane = new VBox();
        myColPane.setSpacing(SPACING);
        myColPane.setPadding(new Insets(PADDING_TOP, PADDING_OTHER, PADDING_OTHER, PADDING_OTHER));
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
    }


    /**
     * Get the column panel node
     *
     * @return col pane view node
     */
    public VBox getMyColPane() {
        return myColPane;
    }

    /**
     * Get resource bundle for controls
     *
     * @return resource bundle for controls
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}
