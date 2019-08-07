package goliathenviousfx.custom;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathEnviousFX;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class TileDisplayPane extends TilePane
{
    public TileDisplayPane(List<ReadOnlyNvReadable> rdbls)
    {
        super();
        super.setStyle("-fx-background-color: -fx-theme-header;");
        super.setAlignment(Pos.CENTER);
        super.setPadding(new Insets(16*GoliathEnviousFX.SCALE));
        super.setVgap(8*GoliathEnviousFX.SCALE);
        super.setHgap(8*GoliathEnviousFX.SCALE);
        
        for(int i = 0; i < rdbls.size(); i++)
        {
            Tile tile = new Tile();
            tile.setNvReadable(rdbls.get(i));
            super.getChildren().add(tile);
        }
    }
}
