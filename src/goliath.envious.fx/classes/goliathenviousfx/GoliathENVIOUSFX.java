package goliathenviousfx;

import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.threads.AttributeUpdatesThread;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GoliathENVIOUSFX extends Application
{
    public static double SCALE = 1.025;
    public static Font FONT;
    
    private static AttributeUpdatesThread high;
    private static AttributeUpdatesThread med;
    private static AttributeUpdatesThread low;
    private static AttributeUpdatesThread fan;
    private static AttributeUpdatesThread power;
    private static Stage APP_STAGE;
    
    public static void main(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                case "--scale":
                    SCALE = Double.parseDouble(args[i+1]);
                    break;      
            }
        }
        
        NvSettings.init();
        
        high = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getHighUpdateFrequencyAttributes()), Thread.MAX_PRIORITY, 0);
        high.start();
        
        fan = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getFan().getNvReadables()), Thread.MIN_PRIORITY, 600);
        fan.start();
        
        med = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getMediumUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 750);
        med.start();
        
        low = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getPrimaryGPU().getLowUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 1000);
        low.start();
        
        power = new AttributeUpdatesThread(new ArrayList<>(NvSMI.READABLES), Thread.MIN_PRIORITY, 1250);
        power.start();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        APP_STAGE = stage;
        FONT = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 13*SCALE);
        Scene scene = new Scene(new AppRoot());
        scene.getStylesheets().add("goliath/css/Goliath-Envy.css");
        scene.getRoot().setStyle(" -fx-font-size: " + 13*SCALE + "px;");
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.maximizedProperty().addListener(new StageResizeHandler());
        stage.setOpacity(.98);
        stage.setMinWidth(1100*SCALE);
        stage.setMinHeight(550*SCALE);
        stage.setTitle("Goliath Envious FX V1");
        stage.show();
    }
    
    private static class StageResizeHandler implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            APP_STAGE.sizeToScene();
        }

    }
}
