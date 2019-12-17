package simulation;

import java.util.Arrays;
import java.util.List;

public class FireCell extends Cell {
    private static final CellAttribute PROBCATCH = CellAttribute.PROBCATCH;
    private static final CellState TREE = CellState.TREE;
    private static final CellState FIREEMPTY = CellState.FIREEMPTY;
    private static final CellState BURNING = CellState.BURNING;
    public static final List<CellState> STATES_LIST = Arrays.asList(TREE, FIREEMPTY, BURNING);
    private static final double HUNDRED = 100;

    private double probCatch;

    public FireCell(int row, int col, CellState state, double probCatch) {
        super(row, col, state);
        this.probCatch = probCatch;
        putAttribute(PROBCATCH, (int) probCatch);
        setPossibleStates(STATES_LIST);
    }


    @Override
    public void check() {
        if (getState() == CellState.TREE) {
            if (isFireNearby() && Math.random() * HUNDRED <= probCatch) {
                setNextState(BURNING);
            } else {
                setNextState(CellState.TREE);
            }
        } else {
            setNextState(FIREEMPTY);
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean isFireNearby() {
        System.out.println(getNeighbors().size());
        for (Cell neighbor : getNeighbors().values()) {
            if (neighbor.getState() == CellState.BURNING) {
                return true;
            }
        }
        return false;
    }
}
