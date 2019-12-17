package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import utils.Point;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class WaTorXml extends AbstractXml {


    public WaTorXml(UserInterface myUserInterFace) {
        super(myUserInterFace);
    }

    public void setUpSimulationParameters() {
        super.setUpSimulationParameters();


        for (int i = 0; i < this.numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            this.EnergyArray.add(Integer.parseInt(n.getElementsByTagName("Energy").item(0).getTextContent()));
            this.MaturityArray.add(Integer.parseInt(n.getElementsByTagName("Mature").item(0).getTextContent()));
        }


    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        Document document = stageXml();
        Map<Point, Cell> myMap = myGridView.getGridManager().getCellGrid().getGridOfCells();
        List<List<Integer>> colArray = new ArrayList<>();
        List<List<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, CellState.SHARK, CellState.FISH, colArray, rowArray);
        addAgents(document, "0", rowArray.get(0), colArray.get(0),
                getMaturityArray().get(0), getEnergyArray().get(0));
        addAgents(document, "1", rowArray.get(1), colArray.get(1),
                getMaturityArray().get(1), getEnergyArray().get(1));
        try{
            createXmlFilePath(document, xmlFilePath);
        }
        catch (TransformerException e){
            logger.log(Level.SEVERE, "an exception was thrown", e);
        }


    }

}
