package simulation;

import userInterface.CellShapeType;
import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeighborManager {

    private static final Point W = new Point(0, -1);
    private static final Point E = new Point(0, 1);
    private static final Point S = new Point(1, 0);
    private static final Point N = new Point(-1, 0);
    private static final Point SW = new Point(1, -1);
    private static final Point SE = new Point(1, 1);
    private static final Point NW = new Point(-1, -1);
    private static final Point NE = new Point(-1, 1);
    private static final Point WW = new Point(0, -2);
    private static final Point EE = new Point(0, 2);
    private static final Point SWW = new Point(1, -2);
    private static final Point SEE = new Point(1, 2);
    private static final Point NWW = new Point(-1, -2);
    private static final Point NEE = new Point(-1, 2);
    private static final Point NN = new Point(-2, 0);
    private static final Point SS = new Point(2, 0);

    private static final List<Point> squareAllNeighbors = List.of(NW, N, NE, E, SE, S, SW, W);
    private static final List<Point> squareEdgeNeighbors = List.of(N, E, S, W);
    private static final List<Point> downwardTriangleNeighbors = List.of(NWW, NW, N, NE, NEE, EE, E, SE, S, SW, W, WW);
    private static final List<Point> upwardTriangleNeighbors = List.of(NW, N, NE, EE, E, SEE, SE, S, SW, SWW, W, WW);
    private static final List<Point> downwardTriangleEdgeNeighbors = List.of(N, E, W);
    private static final List<Point> upwardTriangleEdgeNeighbors = List.of(E, S, W);
    private static final List<Point> leftSidedRowHexagonNeighbors = List.of(NW, NN, N, S, SS, SW);
    private static final List<Point> rightSidedRowHexagonNeighbors = List.of(N, NN, NE, SE, SS, S);

    private static final String defaultConfig = "11111111";

    private List<Point> allowedNeighbor;
    private List<Point> edgeNeighbors;
    private boolean toroidal;
    private CellShapeType cellShapeType;

    //default -> put "11111111" for eightBit.
    public NeighborManager(String eightBit, CellShapeType cellShapeType, boolean toroidal) {
        this.cellShapeType = cellShapeType;
        allowedNeighbor = calcAllowedAllNeighbors(eightBit);
        edgeNeighbors = calcAllowedEdgeNeighbors(eightBit);
        this.toroidal = toroidal;
    }

    /**
     * Assigns neighbors to all cells. Includes corner neighbors.
     */
    public void assignAllNeighbors(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
        for (Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addNeighborsToCell(cell, gridOfCells, numOfRows, numOfCols, calcActualNeighbors(cell.getRow(), cell.getCol()));
            System.out.println(cell.getNeighbors().size());
        }
    }

    /**
     * Assigns neighbors to all cells. Does not include corner neighbors.
     */
    public void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
        for (Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addNeighborsToCell(cell, gridOfCells, numOfRows, numOfCols, calcActualEdgeNeighbors(cell.getRow(), cell.getCol()));
            System.out.println(cell.getNeighbors().size());
        }
    }

    /**
     * Returns ordered list of directions of actual neighbors. order goes clockwise from neighbor at NW direction.
     * Method needed for ant foraging, as direction of ant influences its movement.
     */
    public List<Point> getOrderedNeighborDirections(Cell cell, int numOfRows, int numOfCols) {
        List<Point> list = new ArrayList<>();
        list.addAll(getAcutalNeighborDirections(cell, numOfRows, numOfCols, calcActualNeighbors(cell.getRow(), cell.getCol())));
        return list;
    }

    private List<Point> getAcutalNeighborDirections(Cell cell, int numOfRows, int numOfCols, List<Point> directions) {
        List<Point> actualNeighbors = new ArrayList<>();
        for (Point direction : directions) {
            int row = cell.getRow();
            int col = cell.getCol();
            row += direction.getRow();
            col += direction.getCol();
            if (!toroidal) {
                boolean rowOutOfBounds = row<0 || row >= numOfRows;
                boolean colOutOfBounds = col<0 || col >= numOfCols;
                if (rowOutOfBounds || colOutOfBounds) {
                    continue;
                }
            }
            actualNeighbors.add(direction);
        }
        return actualNeighbors;
    }

    private void addNeighborsToCell(Cell cell, Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols, List<Point> directions) {
        for (Point direction : getAcutalNeighborDirections(cell, numOfRows, numOfCols, directions)) {
            cell.addNeighbor(direction, cellFromPoint(gridOfCells, cell.getRow() + direction.getRow(),
                    cell.getCol() + direction.getCol(), numOfRows, numOfCols));
        }
    }

    private Cell cellFromPoint(Map<Point, Cell> gridOfCells, int row, int col, int numOfRows, int numOfCols) {
        if (row < 0) {
            row += numOfRows;
        } else if (row >= numOfRows) {
            row -= numOfRows;
        }

        if (col < 0) {
            col += numOfCols;
        } else if (col >= numOfCols) {
            col -= numOfCols;
        }

        return gridOfCells.get(new Point(row, col));
    }

    private List<Point> shapeNeighbor() {
        if (cellShapeType == CellShapeType.RECTANGLE) {
            return squareAllNeighbors;
        } else if (cellShapeType == CellShapeType.TRIANGLE) {
            return upwardTriangleNeighbors;
        } else {
            return rightSidedRowHexagonNeighbors;
        }
    }

    private List<Point> shapeEdgeNeighbor() {
        if (cellShapeType == CellShapeType.RECTANGLE) {
            return squareEdgeNeighbors;
        } else if (cellShapeType == CellShapeType.TRIANGLE) {
            return upwardTriangleEdgeNeighbors;
        } else {
            return rightSidedRowHexagonNeighbors;
        }
    }

    private List<Point> calcAllowedAllNeighbors(String str) {
        if (str.equals(defaultConfig)) {
            return shapeNeighbor();
        } else {
            List<Point> list = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '1' && shapeNeighbor().contains(squareAllNeighbors.get(i))) {
                    list.add(squareAllNeighbors.get(i));
                }
            }
            return list;
        }
    }

    private List<Point> calcAllowedEdgeNeighbors(String str) {
        if (str.equals(defaultConfig)) {
            return shapeEdgeNeighbor();
        } else {
            List<Point> list = new ArrayList<>();
            for (int i = 1; i < str.length(); i += 2) {
                if (str.charAt(i) == '1' && shapeEdgeNeighbor().contains(squareAllNeighbors.get(i))) {
                    list.add(squareAllNeighbors.get(i));
                }
            }
            System.out.println(list.toString());
            return list;
        }
    }

    private List<Point> calcActualNeighbors(int row, int col) {
        if (downWardTriangle(row, col)) {
            return copyOfList(downwardTriangleNeighbors);
        } else if (upwardTriangle(row, col)) {
            return copyOfList(upwardTriangleNeighbors);
        } else if(rightSidedRowHexagon(row)) {
            return copyOfList(rightSidedRowHexagonNeighbors);
        } else if(leftSidedRowHexagon(row)) {
            return copyOfList(leftSidedRowHexagonNeighbors);
        }
        return copyOfList(allowedNeighbor);
    }

    private List<Point> calcActualEdgeNeighbors(int row, int col) {
        if (downWardTriangle(row, col)) {
            return copyOfList(downwardTriangleEdgeNeighbors);
        } else if (upwardTriangle(row, col)) {
            return copyOfList(upwardTriangleEdgeNeighbors);
        } else if(cellShapeType == CellShapeType.RECTANGLE) {
            return copyOfList(edgeNeighbors);
        }
        return calcActualNeighbors(row, col);
    }

    private List<Point> copyOfList(List<Point> lists) {
        ArrayList<Point> temp = new ArrayList<>();
        temp.addAll(lists);
        return temp;
    }

    private boolean downWardTriangle(int row, int col) {
        return cellShapeType == CellShapeType.TRIANGLE && (row+col) % 2 == 0;
    }

    private boolean upwardTriangle(int row, int col) {
        return (row+col) % 2 != 0 && cellShapeType == CellShapeType.TRIANGLE;
    }

    private boolean leftSidedRowHexagon(int row) {
        return cellShapeType == CellShapeType.HEXAGON && row%2 != 0;
    }

    private boolean rightSidedRowHexagon(int row) {
        return row%2 == 0 && cellShapeType == CellShapeType.HEXAGON;
    }
}
