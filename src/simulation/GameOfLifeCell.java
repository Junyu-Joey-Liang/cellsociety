package simulation;

import java.util.List;

public class GameOfLifeCell extends Cell {

    private static final CellState ALIVE = CellState.ALIVE;
    private static final CellState DEAD = CellState.DEAD;
    public static final List<CellState> STATES_LIST = List.of(DEAD, ALIVE);
    private static final int ADEQUATEPOPULATION = 2;
    private static final int PERFECTPOPULATION = 3;

    public GameOfLifeCell(int r, int c, CellState state) {
        super(r, c, state);
        setPossibleStates(STATES_LIST);
    }

    @Override
    public void check() {
        System.out.println(getNeighbors().size());
        int aliveNeighbors = countNeighborsWithState(ALIVE);
        if (willLive(aliveNeighbors)) {
            setNextState(ALIVE);
        } else {
            setNextState(DEAD);
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean willLive(int aliveNeighbors) {
        return deadAndWillLive(aliveNeighbors) || aliveAndWillLive(aliveNeighbors);
    }

    private boolean deadAndWillLive(int aliveNeighbors) {
        return getState() == DEAD && aliveNeighbors == PERFECTPOPULATION;
    }

    private boolean aliveAndWillLive(int aliveNeighbors) {
        return getState() == ALIVE && (aliveNeighbors == ADEQUATEPOPULATION || aliveNeighbors == PERFECTPOPULATION);
    }
}