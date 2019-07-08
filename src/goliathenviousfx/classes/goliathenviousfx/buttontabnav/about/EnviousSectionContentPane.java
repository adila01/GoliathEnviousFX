package goliathenviousfx.buttontabnav.about;

import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class EnviousSectionContentPane extends SectionContentPane
{
    List<BorderPane> panes;
    
    public EnviousSectionContentPane()
    {
        super("About Goliath Envious");
        
        List<Space> spaces = new ArrayList<>();
        
        for(int i = 0; i < 2; i++)
        {
            spaces.add(new Space(true));
            spaces.get(i).setMinHeight(1*GoliathEnviousFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathEnviousFX.SCALE);
        }
        
        panes = new ArrayList<>();
        
        for(int i = 0; i < 3; i++)
        {
            panes.add(new BorderPane());
            panes.get(i).setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        }
        
        panes.get(0).setLeft(new Label("Nvidia Driver Version"));
        panes.get(0).setRight(new Label(NvSMI.getPrimaryNvGPUInstance().getDriverVersion().getValue().toString()));
        
        panes.get(1).setLeft(new Label("Nvidia GPU Count"));
        panes.get(1).setRight(new Label(String.valueOf(NvGPU.getNvGPUList().size())));
        
        panes.get(2).setLeft(new Label("Force Unsupported Features"));
        panes.get(2).setRight(new Label(Boolean.toString(NvGPU.getPrimaryNvGPU().getForceUnsupportedFeatures())));
        
        super.addTo(panes.get(0));
        super.addTo(spaces.get(0));
        super.addTo(panes.get(1));
        super.addTo(spaces.get(1));
        super.addTo(panes.get(2));
    }
}
