package simulation;

import javafx.scene.paint.Color;

import java.util.ResourceBundle;

public enum CellState {
    ALIVE,
    DEAD,
    EMPTY,

    DISATISFIED,
    FIRSTAGENT,
    SECONDAGENT,

    FIREEMPTY,
    BURNING,
    TREE,

    OPEN,
    PERCOLATED,
    BLOCKED,

    WATER,
    FISH,
    SHARK,

    ROCK,
    PAPER,
    SCISSOR,

    OBSTACLE,
    FOOD,
    HOME,
    ANT,
    ANTFULL;


    private Color myColor;

    public static final String PATH = "resources/StateColorResources";

    CellState() {
        this.myColor = Color.web(ResourceBundle.getBundle(PATH).getString(this.toString()));
    }

    public Color getMyColor() {
        return this.myColor;
    }

}
