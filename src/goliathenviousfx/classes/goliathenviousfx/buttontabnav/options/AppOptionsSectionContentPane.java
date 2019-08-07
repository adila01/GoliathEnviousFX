package goliathenviousfx.buttontabnav.options;

import goliathenviousfx.settings.AppSettings;
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

public class AppOptionsSectionContentPane extends SectionContentPane
{
    private final GenericComboBooleanPropertyPane smiOnly;
    private final GenericComboBooleanPropertyPane compactMonitors;
    private final GenericComboBooleanPropertyPane allowUnderclock;
    private final GenericComboBooleanPropertyPane transferRatePowerMizer;
    
    public AppOptionsSectionContentPane()
    {
        super("Goliath Envious FX Options");
        
        TilePane tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(16*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile smiOnlyTile = new Tile();
        smiOnlyTile.setSettingProperty(AppSettings.getNvSMIOnly());
        
        Tile compactMonitorTile = new Tile();
        compactMonitorTile.setSettingProperty(AppSettings.getCompactMonitors());
        
        Tile allowUnderclockTile = new Tile();
        allowUnderclockTile.setSettingProperty(AppSettings.getAllowUnderclocking());
        
        Tile transferRateSettingTile = new Tile();
        transferRateSettingTile.setSettingProperty(AppSettings.getTransferRatePowerMizer());
        
        tilePane.getChildren().add(smiOnlyTile);
        tilePane.getChildren().add(compactMonitorTile);
        tilePane.getChildren().add(allowUnderclockTile);
        tilePane.getChildren().add(transferRateSettingTile);
        
        List<Space> spaces = new ArrayList<>();
        
        for(int i = 0; i < 4; i++)
        {
            spaces.add(new Space(true));
            spaces.get(i).setMinHeight(1*GoliathEnviousFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathEnviousFX.SCALE);
        }
        
        smiOnly = new GenericComboBooleanPropertyPane(AppSettings.getNvSMIOnly());
        compactMonitors = new GenericComboBooleanPropertyPane(AppSettings.getCompactMonitors());
        allowUnderclock = new GenericComboBooleanPropertyPane(AppSettings.getAllowUnderclocking());
        transferRatePowerMizer = new GenericComboBooleanPropertyPane(AppSettings.getTransferRatePowerMizer());
        
        super.addTo(tilePane);
        super.addTo(spaces.get(0));
        super.addTo(smiOnly);
        super.addTo(spaces.get(1));
        super.addTo(compactMonitors);
        super.addTo(spaces.get(2));
        super.addTo(allowUnderclock);
        super.addTo(spaces.get(3));
        super.addTo(transferRatePowerMizer);
    }
}
