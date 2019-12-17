package xml;

import userInterface.AbstractGridView;
import userInterface.UserInterface;
import java.io.File;

public class AntForagingXml extends AbstractXml {


    public AntForagingXml(UserInterface myUserInterface) {
        super(myUserInterface);


    }


    //sets up the simulation for ant foraging
    protected void setUpSimulationParameters(){
        super.setUpSimulationParameters();
        this.diffusion = Double.parseDouble(doc.getElementsByTagName("Diffusion").item(0).getTextContent());
        this.evaporation = Double.parseDouble(doc.getElementsByTagName("Evaporation").item(0).getTextContent());
        this.maxAnts = Integer.parseInt(doc.getElementsByTagName("MaxAnts").item(0).getTextContent());
        this.birthRate = Integer.parseInt(doc.getElementsByTagName("Birthrate").item(0).getTextContent());
    }

    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile)  {
        this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_savingFile"));

    }
}
