package simulation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SegregationCell extends Cell {

    private static final CellState EMPTY = CellState.EMPTY;
    public static final List<CellState> STATES_LIST =
            Collections.unmodifiableList(Arrays.asList(EMPTY, CellState.FIRSTAGENT, CellState.SECONDAGENT));
    private static final CellState DISATISFIED = CellState.DISATISFIED;
    private static final double HUNDRED = 100.0;
    private int agentPercent;

    public SegregationCell(int row, int col, CellState state, int agentPercent) {
        super(row, col, state);

        putAttribute(CellAttribute.AGENTPERCENT, agentPercent);
        this.agentPercent = agentPercent;
        setPossibleStates(STATES_LIST);
    }

    @Override
    public void check() {
        //if empty, next state is empty.
        if (getState() == EMPTY) {
            setNextState(CellState.EMPTY);
            return;
        }
        int cellsWithSameState = 0;
        int notEmptyNeighbors = 0;
        for (Cell other : getNeighbors().values()) {
            if (other.getState() != EMPTY) {
                if (other.getState() == getState()) {
                    cellsWithSameState++;
                }
                notEmptyNeighbors++;
            }
        }
        if (cellsWithSameState < notEmptyNeighbors * agentPercent / HUNDRED) {
            setNextState(DISATISFIED);
        } else {
            setNextState(getState());
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

}
