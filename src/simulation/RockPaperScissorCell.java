package simulation;

import java.util.Arrays;
import java.util.List;

public class RockPaperScissorCell extends Cell {
    private static final CellState ROCK = CellState.ROCK;
    private static final CellState PAPER = CellState.PAPER;
    private static final CellState SCISSOR = CellState.SCISSOR;
    private static final CellAttribute THRESHOLD = CellAttribute.THRESHOLD;

    public static final List<CellState> STATES_LIST = Arrays.asList(ROCK, PAPER, SCISSOR);
    private int threshold;

    public RockPaperScissorCell(int row, int col, CellState state, int threshold) {
        super(row, col, state);
        this.threshold = threshold;
        putAttribute(THRESHOLD, threshold);
    }

    @Override
    public void check() {
        if (changeToWinner()) {
            setNextState(winningCellState());
        } else {
            setNextState(getState());
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private CellState winningCellState() {
        int index = STATES_LIST.indexOf(getState());
        index++;
        index %= STATES_LIST.size();
        return STATES_LIST.get(index);
    }

    private boolean changeToWinner() {
        return countNeighborsWithState(winningCellState()) >= threshold;
    }
}
