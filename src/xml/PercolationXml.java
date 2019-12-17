package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;


public class PercolationXml extends AbstractXml {
    public PercolationXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.PERCOLATED, CellState.BLOCKED, xmlFilePath);
    }


}
