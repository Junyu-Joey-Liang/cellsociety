package userInterface;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

/**
 * Class for the simulation line chart graph for state percentages.
 */
public class SimulationGraph extends LineChart {
    private static final String X_LABEL = "Time";
    private static final String Y_LABEL = "Percentage";
    private static final int HEIGHT = 230;
    private static final int WIDTH = 600;
    private NumberAxis myX;
    private NumberAxis myY;
    protected ArrayList<XYChart.Series> seriesList = new ArrayList<>();


    protected SimulationGraph(NumberAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);
        myX = xAxis;
        myY = yAxis;
        myX.setLabel(X_LABEL);
        myY.setLabel(Y_LABEL);
        this.setPrefSize(WIDTH, HEIGHT);
        this.setCreateSymbols(false);
    }

    protected void addLine(Series line){
        this.getData().add(line);
        this.seriesList.add(line);
    }

}
