package goliathenviousfx.buttontabnav.about;

import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class JavaSectionContentPane extends SectionContentPane
{
    List<BorderPane> panes;
    
    public JavaSectionContentPane()
    {
        super("About Java & JavaFX");
        
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
        
        panes.get(0).setLeft(new Label("Java Version"));
        panes.get(0).setRight(new Label(System.getProperty("java.runtime.version")));
        
        panes.get(1).setLeft(new Label("Java GTK Version"));
        panes.get(1).setRight(new Label(System.getProperty("jdk.gtk.version")));
        
        panes.get(2).setLeft(new Label("JavaFX Version"));
        panes.get(2).setRight(new Label(System.getProperty("javafx.runtime.version")));
        
        super.addTo(panes.get(0));
        super.addTo(spaces.get(0));
        super.addTo(panes.get(1));
        super.addTo(spaces.get(1));
        super.addTo(panes.get(2));
    }
}
