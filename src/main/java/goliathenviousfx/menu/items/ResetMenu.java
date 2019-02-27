package goliathenviousfx.menu.items;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.interfaces.NvControllable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ResetMenu extends Menu
{
    public ResetMenu()
    {
        super("Reset");
        
        super.getItems().add(new MenuItem("All"));
        super.getItems().get(0).setOnAction(new ResetAllHandler());
        
        for(int i = 0; i < NvSettings.getPrimaryInstance().getControllableAttributes().size(); i++)
        {
            if(NvSettings.getPrimaryInstance().getControllableAttributes().get(i).getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            {
                super.getItems().add(new MenuItem(NvSettings.getPrimaryInstance().getControllableAttributes().get(i).getControlName()));
                super.getItems().get(super.getItems().size()-1).setOnAction(new ResetHandler(NvSettings.getPrimaryInstance().getControllableAttributes().get(i)));
            }
        }
        
        if(NvSMI.getPrimaryInstance().getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            super.getItems().add(new MenuItem(NvSMI.getPrimaryInstance().getPowerLimit().getController().get().getControlName()));
            super.getItems().get(super.getItems().size()-1).setOnAction(new ResetHandler(NvSMI.getPrimaryInstance().getPowerLimit().getController().get()));
        }
    }
    
    private class ResetAllHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                NvSettings.getPrimaryInstance().resetControllables();
            }
            catch (ControllerResetFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    private class ResetHandler implements EventHandler<ActionEvent>
    {
        private NvControllable controller;
        
        public ResetHandler(NvControllable cont)
        {
            controller = cont;
        }
        
        
        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                controller.reset();
                
                if(NvSMI.getPrimaryInstance().getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
                    NvSMI.getPrimaryInstance().getPowerLimit().getController().get().reset();
            }
            catch (ControllerResetFailedException ex)
            {
                ex.printStackTrace();
            }
        }
        
    }
}
