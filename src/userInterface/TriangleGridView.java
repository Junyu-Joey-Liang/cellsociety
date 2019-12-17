package userInterface;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Grid view class for triangle cells
 */
public class TriangleGridView extends AbstractGridView {
    private static final double START_POINT = 0.0;
    private static final double WIDTH_PADDING_RATIO = 0.5;
    private static final int WIDTH_RATIO = 2;
    private Group myTriangleGroup;


    protected TriangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
        myTriangleGroup = new Group();
        getGridPane().getChildren().add(myTriangleGroup);
    }

    @Override
    public void generateBlankGrid() {
        Rectangle rectangle = new Rectangle(GRID_WIDTH, this.getGridHeight());
        rectangle.setFill(Color.LIGHTBLUE);
        myTriangleGroup.getChildren().add(rectangle);
    }

    @Override
    public void createGrid() {
        boolean rowFlag = true;
        double width = GRID_WIDTH / (WIDTH_PADDING_RATIO + getGridManager().getCellGrid().getNumOfCols() / WIDTH_RATIO);
        double height = this.getGridHeight() / getGridManager().getCellGrid().getNumOfRows();
        for (int r = 0; r < getGridManager().getCellGrid().getNumOfRows(); r++) {
            for (int c = 0; c < getGridManager().getCellGrid().getNumOfCols() / WIDTH_RATIO; c++) {
                Polygon upTriangle = new Polygon();
                upTriangle.getPoints().addAll(
                        START_POINT, START_POINT,
                        width, START_POINT,
                        width / WIDTH_RATIO, height);
                upTriangle.setLayoutY(r * height);
                myTriangleGroup.getChildren().add(upTriangle);

                Polygon downTriangle = new Polygon();
                downTriangle.getPoints().addAll(
                        width / WIDTH_RATIO, START_POINT,
                        START_POINT, height,
                        width, height);
                downTriangle.setLayoutY(r * height);
                myTriangleGroup.getChildren().add(downTriangle);
                final int row = r;
                final int upCol = rowFlag ? c * WIDTH_RATIO : c * WIDTH_RATIO + 1;
                final int downCol = rowFlag ? c * WIDTH_RATIO + 1 : c * WIDTH_RATIO;
                upTriangle.setLayoutX(rowFlag ? c * width : (c + WIDTH_PADDING_RATIO) * width);
                upTriangle.setFill(getGridManager().getColorOfCellAtPoint(row, upCol));
                upTriangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row, upCol));
                downTriangle.setLayoutX(rowFlag ? (c + WIDTH_PADDING_RATIO) * width : c * width);
                downTriangle.setFill(getGridManager().getColorOfCellAtPoint(row, downCol));
                downTriangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row, downCol));
            }
            rowFlag = !rowFlag;
        }
        getGridManager().getCellGrid().setCellShapeType(CellShapeType.TRIANGLE);
    }

    @Override
    public void displayGrid() {
        myTriangleGroup.getChildren().clear();
        createGrid();
    }

    private void changeState(int r, int c) {
        this.getGridManager().getCellGrid().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }


}

