package goliathenviousfx.buttontabnav.options;

import goliath.envious.utility.EnviousPlatform;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericComboBooleanPropertyPane;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class APIOptionsSectionContentPane extends SectionContentPane
{
    private final GenericComboBooleanPropertyPane force;
    private final GenericComboBooleanPropertyPane memoryAbstraction;
    private final GenericComboBooleanPropertyPane voltageOffsetAbstraction;
    private final GenericComboBooleanPropertyPane voltageCurrentAbstraction;
    
    public APIOptionsSectionContentPane()
    {
        super("Goliath Envious Options");
        
        TilePane tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(16*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile unsupportedFeaturesTile = new Tile();
        unsupportedFeaturesTile.setSettingProperty(EnviousPlatform.CURRENT.getForceUnsupportedFeaturesSetting());
        
        Tile memoryOffsetAbstractionTile = new Tile();
        memoryOffsetAbstractionTile.setSettingProperty(EnviousPlatform.CURRENT.getMemoryOffsetSpeedAbstractionSetting());
        
        Tile voltageOffsetAbstractionTile = new Tile();
        voltageOffsetAbstractionTile.setSettingProperty(EnviousPlatform.CURRENT.getVoltageOffsetAbstractionSetting());
        
        Tile voltageCurrentAbstractionTile = new Tile();
        voltageCurrentAbstractionTile.setSettingProperty(EnviousPlatform.CURRENT.getVoltageCurrentAbstractionSetting());
        
        tilePane.getChildren().add(unsupportedFeaturesTile);
        tilePane.getChildren().add(memoryOffsetAbstractionTile);
        tilePane.getChildren().add(voltageOffsetAbstractionTile);
        tilePane.getChildren().add(voltageCurrentAbstractionTile);
        
        List<Space> spaces = new ArrayList<>();
        
        for(int i = 0; i < 4; i++)
        {
            spaces.add(new Space(true));
            spaces.get(i).setMinHeight(1*GoliathEnviousFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathEnviousFX.SCALE);
        }
        
        force = new GenericComboBooleanPropertyPane(EnviousPlatform.CURRENT.getForceUnsupportedFeaturesSetting());
        memoryAbstraction = new GenericComboBooleanPropertyPane(EnviousPlatform.CURRENT.getMemoryOffsetSpeedAbstractionSetting());
        voltageOffsetAbstraction = new GenericComboBooleanPropertyPane(EnviousPlatform.CURRENT.getVoltageOffsetAbstractionSetting());
        voltageCurrentAbstraction = new GenericComboBooleanPropertyPane(EnviousPlatform.CURRENT.getVoltageCurrentAbstractionSetting());
        
        super.addTo(tilePane);
        super.addTo(spaces.get(0));
        super.addTo(force);
        super.addTo(spaces.get(1));
        super.addTo(memoryAbstraction);
        super.addTo(spaces.get(2));
        super.addTo(voltageOffsetAbstraction);
        super.addTo(spaces.get(3));
        super.addTo(voltageCurrentAbstraction);
    }
}
