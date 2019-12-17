package simulation;

import java.util.List;

public class PercolationCell extends Cell {
    private static final CellState PERCOLATED = CellState.PERCOLATED;
    private static final CellState OPEN = CellState.OPEN;
    private static final CellState BLOCKED = CellState.BLOCKED;
    public static final List<CellState> STATES_LIST = List.of(OPEN, BLOCKED, PERCOLATED);

    public PercolationCell(int row, int col, CellState state) {
        super(row, col, state);
        setPossibleStates(STATES_LIST);
    }

    @Override
    public void check() {
        if (getState() == OPEN && percolatedCellNearby()) {
            setNextState(PERCOLATED);
        } else {
            setNextState(getState());
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean percolatedCellNearby() {
        for (Cell neighbor : getNeighbors().values()) {
            if (neighbor.getState() == PERCOLATED) {
                return true;
            }
        }
        return false;
    }
}