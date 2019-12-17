package userInterface;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


/**
 * Grid view class for hexagon cells
 */
public class HexagonGridView extends AbstractGridView {
    private static final double WIDTH_PADDING_RATIO = 0.25;
    private static final double WIDTH_PADDING_RATIO_LONG = 0.75;
    private static final double WIDTH_EACH_HEX_RATIO = 1.5;
    private static final double HEIGHT_EACH_HEX_RATIO = 0.5;
    private static final int HEIGHT_OF_GRID_RATIO = 2;
    private static final double START_POINT = 0.0;
    private Group myHexagonGroup;


    protected HexagonGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
        this.setGridHeight(this.getGridHeight() / HEIGHT_OF_GRID_RATIO);
        myHexagonGroup = new Group();
        getGridPane().getChildren().add(myHexagonGroup);
    }

    @Override
    public void generateBlankGrid() {
        Rectangle rectangle = new Rectangle(GRID_WIDTH, this.getGridHeight());
        rectangle.setFill(Color.LIGHTBLUE);
        myHexagonGroup.getChildren().add(rectangle);
    }

    @Override
    public void createGrid() {
        boolean rowFlag = true;
        double width = GRID_WIDTH / (getGridManager().getCellGrid().getNumOfCols() * WIDTH_EACH_HEX_RATIO + WIDTH_PADDING_RATIO);
        double height = this.getGridHeight() / (getGridManager().getCellGrid().getNumOfRows() * HEIGHT_EACH_HEX_RATIO + HEIGHT_EACH_HEX_RATIO);
        for (int r = 0; r < getGridManager().getCellGrid().getNumOfRows(); r++) {
            for (int c = 0; c < getGridManager().getCellGrid().getNumOfCols(); c++) {
                Polygon hexagon = new Polygon();
                hexagon.getPoints().addAll(
                        START_POINT, height * HEIGHT_EACH_HEX_RATIO,
                        WIDTH_PADDING_RATIO * width, START_POINT,
                        WIDTH_PADDING_RATIO_LONG * width, START_POINT,
                        width, height / HEIGHT_OF_GRID_RATIO,
                        WIDTH_PADDING_RATIO_LONG * width, height,
                        WIDTH_PADDING_RATIO * width, height);
                hexagon.setLayoutY(r * height * HEIGHT_EACH_HEX_RATIO);
                hexagon.setLayoutX(rowFlag ? WIDTH_PADDING_RATIO_LONG * width + c * WIDTH_EACH_HEX_RATIO * width : c * WIDTH_EACH_HEX_RATIO * width);
                myHexagonGroup.getChildren().add(hexagon);
                hexagon.setFill(getGridManager().getColorOfCellAtPoint(r, c));
                final int row = r;
                final int col = c;
                hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row, col));
            }
            rowFlag = !rowFlag;
        }
        getGridManager().getCellGrid().setCellShapeType(CellShapeType.HEXAGON);
    }

    @Override
    public void displayGrid() {
        myHexagonGroup.getChildren().clear();
        createGrid();
    }

    private void changeState(int r, int c) {
        getGridManager().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }


}

