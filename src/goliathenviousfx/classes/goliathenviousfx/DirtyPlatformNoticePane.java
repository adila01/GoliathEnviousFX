package goliathenviousfx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DirtyPlatformNoticePane extends VBox
{
    public DirtyPlatformNoticePane()
    {
        super();
        super.setAlignment(Pos.CENTER);
        super.setSpacing(50*GoliathEnviousFX.SCALE);
        
        Label title = new Label("Dirty Platform Notice");
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font(30*GoliathEnviousFX.SCALE));
        
        Label body = new Label("You are using an unstable graphics driver and Goliath Envious FX will not run.\n\n"
                + "This is likely due to updating the graphics driver without restarting.\n\n"
                + "Please contact your Linux distribution developers about this.");
        body.setAlignment(Pos.BASELINE_CENTER);
        body.setFont(Font.font(20*GoliathEnviousFX.SCALE));
        
        super.getChildren().add(title);
        super.getChildren().add(body);
    }
}
