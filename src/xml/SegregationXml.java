package xml;


import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class SegregationXml extends AbstractXml {
    public SegregationXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.FIRSTAGENT, CellState.SECONDAGENT, xmlFilePath);
    }


}
