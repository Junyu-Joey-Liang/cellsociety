package userInterface;

import javafx.scene.paint.Color;
import simulation.AntForaging.AntForagingCell;
import simulation.AntForaging.AntForagingCellGrid;
import simulation.*;
import utils.Point;
import xml.AbstractXml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that manages the cell grid, and connects backend and visualization
 */
public class GridManager {
    private AbstractXml myXml;
    private CellGrid cellGrid;
    private Map<Point, CellState> initialConfigMap;
    private List<CellState> stateList;

    /**
     * initialize cell grid from xml
     *
     * @param myXml xml constructed from config file
     */
    public void initializeMyCellGrid(AbstractXml myXml) {
        this.myXml = myXml;
        Map configMap = new HashMap<Point, CellState>();

        switch (myXml.getMyTitle()) {
            case "Segregation":
                setSegregation(configMap);
                break;
            case "Game Of Life":
                setGameOfLife(configMap);
                break;
            case "Wa-Tor":
                setWaTor(configMap);
                break;
            case "Percolation":
                setPercolation(configMap);
                break;
            case "Fire":
                setFire(configMap);
                break;
            case "Ant Foraging":
                setAnt(configMap);
                System.out.println(myXml.getMaxAnts());
                System.out.println(myXml.getEvaporation());
                System.out.println(myXml.getDiffusion());
                break;
            case "Rock Paper Scissors":
                cellGrid = new RockPaperScissorGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate(), myXml.getPercentage());
                this.stateList = RockPaperScissorCell.STATES_LIST;
        }
        cellGrid.initializeGrids(initialConfigMap);
        cellGrid.assignNeighborsToEachCell();
        System.out.println(myXml.getCellGridColNum());
    }

    /**
     * change edge type of the cell grid
     *
     * @param type edge type to change to
     */
    public void changeEdgeTypeOfGrid(String type) {
        if(type.equals("toroidal")) {
            getCellGrid().setGridLimit(GridLimit.TOROIDAL);
        } else if(type.equals("finite")) {
            getCellGrid().setGridLimit(GridLimit.FINITE);
        } else if(type.equals("infinite")) {
            getCellGrid().setGridLimit(GridLimit.INFINITE);
        }
    }

    /**
     * set number of rows in grid
     *
     * @param numOfRows number of rows in grid
     */
    public void setGridNumOfRows(int numOfRows) {
        getCellGrid().userSetNumOfRows(numOfRows);
    }

    /**
     * set number of cols in grid
     *
     * @param numOfCols number of cols in grid
     */
    public void setGridNumOfCols(int numOfCols) {
        getCellGrid().userSetNumOfCols(numOfCols);
    }

    /**
     * get the cell grid of simulation
     *
     * @return cell grid of the simulation
     */
    public CellGrid getCellGrid() {
        return cellGrid;
    }

    private void setSegregation(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            if (i == 0) {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FIRSTAGENT);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SECONDAGENT);
                }
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = SegregationCell.STATES_LIST;
        cellGrid = new SegregationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate(), myXml.getRate());

    }

    private void setGameOfLife(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            for (int j = 0; j < size; j++) {
                configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.ALIVE);
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = GameOfLifeCell.STATES_LIST;
        cellGrid = new GameOfLifeCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());
    }

    private void setWaTor(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            if (i == 0) {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SHARK);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FISH);
                }
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = WaTorCell.STATES_LIST;
        cellGrid = new WaTorCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getMaturityArray(), myXml.getEnergyArray());
    }

    private void setPercolation(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            if (i == 0) {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.PERCOLATED);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BLOCKED);
                }
            }

        }
        this.initialConfigMap = configMap;
        this.stateList = PercolationCell.STATES_LIST;
        cellGrid = new PercolationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());

    }

    private void setFire(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            if (i == 0) {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BURNING);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FIREEMPTY);
                }
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = FireCell.STATES_LIST;
        cellGrid = new FireCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate());

    }

    private void setAnt(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            if (i == 0) {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.HOME);
                }
            } else {
                for (int j = 0; j < size; j++) {
                    configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FOOD);
                }
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = AntForagingCell.STATES_LIST;
        cellGrid = new AntForagingCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getMaxAnts(), myXml.getEvaporation(),
                myXml.getDiffusion());

    }


    protected void updateCellGrid(){
        this.getCellGrid().checkAllCells();
        this.getCellGrid().changeAllCells();
    }

    protected Color getColorOfCellAtPoint(int r, int c) {
        return this.getCellGrid().stateOfCellAtPoint(r, c).getMyColor();
    }

    protected void setStateOfCellAtPointOnClick(int r, int c) {
        getCellGrid().setStateOfCellAtPointOnClick(r, c);
    }

    protected void resetCellGrid() {
        if (initialConfigMap == null) {
            return;
        }
        getCellGrid().initializeGrids(initialConfigMap);
        getCellGrid().assignNeighborsToEachCell();
    }


    protected List<CellState> getStateList() {
        List<CellState> list = new ArrayList<>();
        list.addAll(stateList);
        return list;
    }


}
