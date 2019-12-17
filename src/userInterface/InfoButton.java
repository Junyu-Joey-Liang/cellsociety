package userInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


/**
 * button to show info about simulation
 */
public class InfoButton extends SimulationButton {
    private Alert alert;
    private static final String STYLE = "-fx-background-radius: 5em; " +
            "-fx-min-width: 40px; " +
            "-fx-min-height: 40px; " +
            "-fx-max-width: 40px; " +
            "-fx-max-height: 40px;" +
            "-fx-background-color: MediumSeaGreen";
    private static final String CLOSE_MSG = "Pressed OK to close.";


    /**
     * Constructor for info button
     *
     * @param name name of button
     * @param title title to show at pop up window
     * @param header header of pop up window
     * @param content contain of pop up window
     */
    public InfoButton(String name, String title, String header, String content) {
        super(name);
        this.setStyle(STYLE);
        this.setText(name);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction() {
//        alert.showAndWait().ifPresent(rs -> {
//            if (rs == ButtonType.OK) {
//                System.out.println(CLOSE_MSG);
//            }
//        });
        alert.showAndWait();
    }

}
