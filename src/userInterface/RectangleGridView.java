package userInterface;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


/**
 * Grid view class for rectangle cells
 */
public class RectangleGridView extends AbstractGridView {

    protected RectangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
    }

    @Override
    public void createGrid() {
        double regWidth = GRID_WIDTH / getGridManager().getCellGrid().getNumOfCols();
        double regHeight = this.getGridHeight() / getGridManager().getCellGrid().getNumOfRows();
        for (int r = 0; r < getGridManager().getCellGrid().getNumOfRows(); r++) {
            for (int c = 0; c < getGridManager().getCellGrid().getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(regWidth, regHeight);
                shape.setFill(this.getGridManager().getColorOfCellAtPoint(r, c));
                getGridPane().add(shape, c, r);
                final int row = r;
                final int col = c;
                shape.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeStateAtMouseClick(row, col));
            }
        }
    }

    @Override
    public void displayGrid() {
        getGridPane().getChildren().clear();
        createGrid();
    }

    private void changeStateAtMouseClick(int r, int c) {
        getGridManager().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }
}

