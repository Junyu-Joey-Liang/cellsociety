package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;


public class GameOfLifeXml extends AbstractXml {
    public GameOfLifeXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }



    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.ALIVE, xmlFilePath);
    }

}
