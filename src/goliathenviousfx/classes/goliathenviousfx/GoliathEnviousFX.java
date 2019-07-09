package goliathenviousfx;

import goliath.envious.enums.UpdateFrequency;
import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.ReadOnlyNvReadable;
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
import goliath.envious.enums.OperationalStatus;;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class GoliathEnviousFX extends Application
{
    public static double SCALE = 1.0;
    public static Font FONT;
    public static Stage STAGE;
    
    private static AttributeUpdatesThread high;
    private static AttributeUpdatesThread power;
    
    public static boolean smiOnly = false;
    
    public static void main(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                /*
                case "--scale":
                {
                    //SCALE = Double.parseDouble(args[i+1]);
                    SCALE = 1.0;
                    break;     
                }
                */  
                case "--smionly":
                {
                    smiOnly = true;
                    break;
                }
                    
                case "--force":
                {
                    for(int k = 0; k < NvGPU.getNvGPUList().size(); k++)
                        NvGPU.getNvGPUList().get(k).setForceUnsupportedFeatures(true);
                    
                    break;
                }
                
                default:
                {
                    System.out.println("Unknown Argument: " + args[i]);
                    break;
                }
            }
        }
        
        NvSMI.init();
        NvSettings.init();
        
        ArrayList<ReadOnlyNvReadable> settingsreadables = new ArrayList<>();
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPowerMizerMode());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPerfModes());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCurrentPerformanceLevel());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPCIeUtilization());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEngineUtilization());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEncoderUtilization());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoDecoderUtilization());
            settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPCIeCurrentSpeed());
            
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            {
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreOffset());
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryOffset());
            }
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCurrentVoltage().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            {
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageOffset());
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCurrentVoltage());
            }
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            {
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanMode());
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvFan().getFanTargetSpeed());
            }  
            
            NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanSpeed().valueProperty().addListener(new FanSpeedListener(NvGPU.getNvGPUList().get(i)));
        }
        
        high = new AttributeUpdatesThread(new ArrayList<>(settingsreadables), Thread.MIN_PRIORITY, 65 / NvGPU.getNvGPUList().size());
        
        if(!smiOnly)
            high.start();
        
        ArrayList<ReadOnlyNvReadable> smireadables = new ArrayList<>();
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
            smireadables.addAll(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvReadablesExceptUpdateFrequency(UpdateFrequency.NEVER));
        
        power = new AttributeUpdatesThread(new ArrayList<>(smireadables), Thread.MIN_PRIORITY, 50 / NvGPU.getNvGPUList().size());
        power.start();
        
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        this.STAGE = stage;
        
        FONT = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()*SCALE);
        Scene scene = new Scene(new AppRoot());
        scene.getStylesheets().add("goliath/css/Goliath-Envy.css");
        scene.getRoot().setStyle("-fx-font-size: " + Font.getDefault().getSize()*SCALE + "px;");
        
        stage.setScene(scene);
        stage.setOnCloseRequest(new CloseHandler());
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setOpacity(.98);
        stage.setMinWidth(950*SCALE);
        stage.setMinHeight(480*SCALE);
        stage.setTitle("Goliath Envious FX V1");
        stage.show();
    }

    private static class CloseHandler implements EventHandler<WindowEvent>
    {

        @Override
        public void handle(WindowEvent t)
        {
            Platform.exit();
            System.exit(0);
        }
        
    }
    
    private static class FanSpeedListener implements ChangeListener<Integer>
    {
        private final NvGPU gpu;
        
        public FanSpeedListener(NvGPU g)
        {
            gpu = g;
        }
        
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
        {
            NvSettings.getNvGPUInstance(gpu).getNvFan().getFanRPMSpeed().update();
        }
    }
}
