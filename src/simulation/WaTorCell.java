package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaTorCell extends Cell {

    private static final CellAttribute ENERGY = CellAttribute.ENERGY;
    private static final CellAttribute INI_ENERGY = CellAttribute.INITIAL_ENERGY;
    private static final CellAttribute SURVIVE = CellAttribute.SURVIVEDTIME;
    private static final CellAttribute REPRODUCE = CellAttribute.REPRODUCTION;
    private static final CellState FISH = CellState.FISH;
    private static final CellState WATER = CellState.WATER;
    private static final CellState SHARK = CellState.SHARK;
    public static final List<CellState> STATES_LIST = Arrays.asList(WATER, FISH, SHARK);

    private boolean moved = false;

    public WaTorCell(int row, int col, CellState state, int reproduce, int energy) {
        super(row, col, state);

        putAttribute(ENERGY, energy);
        putAttribute(SURVIVE, 0);
        putAttribute(INI_ENERGY, energy);
        putAttribute(REPRODUCE, reproduce);
        setPossibleStates(STATES_LIST);
    }

    @Override
    public void check() {
        //If fish was already eaten by a shark, it will remain that way
        if ((getState() == FISH && getNextState() == SHARK) || getState() == WATER) {
            return;
        }

        if (getState() == FISH) {
            checkAndMoveToNeighbor(WATER);
        } else if (getState() == SHARK) {
            checkAndMoveToNeighbor(FISH);
            if (!moved) {
                checkAndMoveToNeighbor(WATER);
            }
        }
        //if this cell cannot move. (baby can only be made when the cell moves)
        if (!moved) {
            putAttribute(SURVIVE, getAttribute(SURVIVE) + 1);
            if (getState() == SHARK) {
                int energy = getAttribute(ENERGY) - 1;
                putAttribute(ENERGY, energy);
                if (energy == 0) {
                    setNextState(WATER);
                }
            }
        }
        moved = false;
    }

    private void checkAndMoveToNeighbor(CellState state) {
        //in order to move to random neighbor that has the required nextState
        // (need to use nextState because the neighbor cell may have already moved)
        List<Cell> possibleNeighbors = new ArrayList<>();
        for (Cell cell : getNeighbors().values()) {
            if (cell.getNextState() == state) {
                possibleNeighbors.add(cell);
            }
        }
        if (possibleNeighbors.isEmpty()) {
            return;
        } else {
            int index = (int) (Math.random() * possibleNeighbors.size());
            moveToDifferentCell(possibleNeighbors.get(index));
        }
        moved = true;
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    @Override
    public void moveToDifferentCell(Cell other) {
        System.out.println(getState() + ", " + getRow() + ", " + getCol());
        System.out.println("to " + other.getState() + ", " + other.getRow() + ", " + other.getCol());
        System.out.println("which will be " + other.getNextState() + ", " + other.getRow() + ", " + other.getCol());
        if (getState() == WATER) {
            return;
        } //empty cells don't move. sharks and fishes can move.
        int energy = getAttribute(ENERGY);
        setNextState(other.getNextState());
        other.setNextState(getState());

        if (getState() == SHARK) {
            if (getNextState() == FISH) {
                System.out.println(other.getAttribute(ENERGY));
                energy += other.getAttribute(ENERGY);
                setNextState(WATER);
            }
            energy--;
            System.out.println(energy);
            if (energy == 0) {
                other.setNextState(WATER);
            }
        }
        other.putAttribute(ENERGY, energy);
        other.putAttribute(REPRODUCE, getAttribute(REPRODUCE));
        other.putAttribute(INI_ENERGY, getAttribute(INI_ENERGY));
        other.putAttribute(SURVIVE, getAttribute(SURVIVE) + 1);

        waterIfDead(other);
        putAttribute(SURVIVE, 0);
    }

    private void waterIfDead(Cell other) {
        if (other.getAttribute(SURVIVE) > other.getAttribute(REPRODUCE)) {
            setNextState(getState());
            putAttribute(ENERGY, getAttribute(INI_ENERGY));
            other.putAttribute(SURVIVE, 0);
        }
    }
}
