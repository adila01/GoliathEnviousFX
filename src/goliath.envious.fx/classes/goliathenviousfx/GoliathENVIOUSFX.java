package goliathenviousfx;

import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.threads.AttributeUpdatesThread;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GoliathENVIOUSFX extends Application
{
    public static double SCALE = 1;
    public static Font FONT;
    
    private static AttributeUpdatesThread high;
    private static AttributeUpdatesThread med;
    private static AttributeUpdatesThread low;
    private static AttributeUpdatesThread fan;
    private static AttributeUpdatesThread power;
    
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

        high = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getInstance(NvGPU.getPrimaryNvGPU()).getHighUpdateFrequencyAttributes()), Thread.MAX_PRIORITY, 0);
        high.start();
        
        fan = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getInstance(NvGPU.getPrimaryNvGPU()).getNvFan().getNvReadables()), Thread.MIN_PRIORITY, 600);
        fan.start();
        
        med = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getInstance(NvGPU.getPrimaryNvGPU()).getMediumUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 750);
        med.start();
        
        low = new AttributeUpdatesThread(new ArrayList<>(NvSettings.getInstance(NvGPU.getPrimaryNvGPU()).getLowUpdateFrequencyAttributes()), Thread.MIN_PRIORITY, 1000);
        low.start();
        
        power = new AttributeUpdatesThread(new ArrayList<>(NvSMI.READABLES), Thread.MIN_PRIORITY, 1250);
        power.start();
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FONT = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 13*SCALE);
        Scene scene = new Scene(new AppRoot());
        scene.getRoot().setCache(true);
        scene.getRoot().setCacheHint(CacheHint.SPEED);
        scene.getStylesheets().add("goliath/css/Goliath-Envy.css");
        scene.getRoot().setStyle(" -fx-font-size: " + Font.getDefault().getSize()*SCALE + "px;");
        
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.maximizedProperty().addListener(new StageResizeHandler());
        stage.setOpacity(.98);
        stage.setMinWidth(1000*SCALE);
        stage.setMinHeight(500*SCALE);
        stage.setTitle("Goliath Envious FX V1");
        stage.show();
    }
    
    private static class StageResizeHandler implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            //APP_STAGE.sizeToScene();
        }

    }
}
