package xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class RockPaperScissorsXml extends AbstractXml {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private ResourceBundle resourceBundle;


    public RockPaperScissorsXml(UserInterface myUserInterface){
        super(myUserInterface);
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        percentage = new ArrayList<>();
    }


    //sets up simulation parameters
    public void setUpSimulationParameters() {
        super.setUpSimulationParameters();
        for (int i = 0; i < this.numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            int percent = Integer.parseInt(n.getElementsByTagName("Percent").item(0).getTextContent());
            percentage.add(percent);
        }

    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) {

        this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_savingFile"));



    }


}
