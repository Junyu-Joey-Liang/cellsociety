package simulation;

/**
 * Needed for ant foraging and other types of simulation in which each grid has more than one individual/elements that
 * acts separately
 */
public abstract class Individual {
    private GridInfo myGridInfo;
    private boolean moved = false;
    private boolean dead = false;

    public Individual(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    public abstract void move();

    public GridInfo getMyGridInfo() {
        return myGridInfo;
    }

    public void setMyGridInfo(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean bool) {
        moved = bool;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean bool) {
        dead = bool;
    }
}
