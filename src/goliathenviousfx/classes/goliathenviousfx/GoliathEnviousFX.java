package goliathenviousfx;

import goliath.envious.abstracts.SettingProperty;
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
import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.utility.EnviousPlatform;
import goliathenviousfx.settings.AppSettings;
import goliathenviousfx.threads.ChartUpdateThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class GoliathEnviousFX extends Application
{
    public static double SCALE = 1.0;
    public static Font FONT;
    public static Stage STAGE;
    
    private static AttributeUpdatesThread nvSettingsThread;
    private static AttributeUpdatesThread nvSMIThread;
    private static ChartUpdateThread chart;
    
    public static void main(String[] args) throws UpdateFailedException
    {   
        loadProperties();
        
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
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageOffset());
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageCurrent().getOperationalStatus().equals(OperationalStatus.READABLE))
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageCurrent());
            
            if(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            {
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanMode());
                settingsreadables.add(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvFan().getFanTargetSpeed());
            }  
            
            NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getFanSpeed().valueProperty().addListener(new FanSpeedListener(NvGPU.getNvGPUList().get(i)));
        }
        
        nvSettingsThread = new AttributeUpdatesThread(new ArrayList<>(settingsreadables), Thread.MIN_PRIORITY, 65 / NvGPU.getNvGPUList().size());
        
        if(!AppSettings.getNvSMIOnly().getValue())
            nvSettingsThread.start();
        
        ArrayList<ReadOnlyNvReadable> smireadables = new ArrayList<>();
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
            smireadables.addAll(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvReadablesExceptUpdateFrequency(UpdateFrequency.NEVER));
        
        nvSMIThread = new AttributeUpdatesThread(new ArrayList<>(smireadables), Thread.MIN_PRIORITY, 50 / NvGPU.getNvGPUList().size());
        nvSMIThread.start();
        
        chart = new ChartUpdateThread();
        chart.start();
        
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        this.STAGE = stage;
        
        FONT = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()*SCALE);
        
        Scene scene;
        
        if(EnviousPlatform.CURRENT.dirtyPlatformProperty().get() == false)
            scene = new Scene(new AppRoot());
        else
            scene = new Scene(new DirtyPlatformNoticePane());
        
        EnviousPlatform.CURRENT.dirtyPlatformProperty().addListener(new DirtyPlatformListener());
        
        scene.getStylesheets().add("goliath/css/Goliath-Envy.css");
        scene.getRoot().setStyle("-fx-font-size: " + Font.getDefault().getSize()*SCALE + "px;");
        
        stage.setScene(scene);
        stage.setOnCloseRequest(new CloseHandler());
        stage.setOpacity(.98);
        stage.setMinWidth(950*SCALE);
        stage.setMinHeight(480*SCALE);
        stage.setTitle("Goliath Envious FX V1.1");
        stage.showingProperty().addListener(new ShowingListener());
        stage.show();
    }

    private static class DirtyPlatformListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            STAGE.close();
            
            Stage stage = new Stage();
            stage.setMinWidth(950*SCALE);
            stage.setMinHeight(480*SCALE);
            stage.setMaximized(true);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Goliath Envious FX Dirty Platform Notice");
            stage.setScene(new Scene(new DirtyPlatformNoticePane()));
            stage.getScene().getStylesheets().add("goliath/css/Goliath-Envy.css");
            stage.show();
        }
    }
    
    private static class ShowingListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
        {
            if(t1 == false)
                return;
            
            STAGE.setMaximized(true);
            STAGE.setResizable(false);
        }
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
            try
            {
                NvSettings.getNvGPUInstance(gpu).getNvFan().getFanRPMSpeed().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public static void saveProperties()
    {
        File appPropFile;
        File apiPropFile;
        
        try
        {
            appPropFile = new File("GoliathEnviousFX.properties");
            apiPropFile = new File("GoliathEnvious.properties");
            
            
             if(!appPropFile.exists())
                appPropFile.createNewFile();
             
             if(!apiPropFile.exists())
                 apiPropFile.createNewFile();
            
            SettingProperty.getSettingProperties("goliath.envious").store(new FileOutputStream(apiPropFile), "settings changes");
            SettingProperty.getSettingProperties("goliath.envious.fx").store(new FileOutputStream(appPropFile), "settings changes");
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void loadProperties()
    {
        File appPropFile;
        File apiPropFile;
        
        try
        {
            appPropFile = new File("GoliathEnviousFX.properties");
            apiPropFile = new File("GoliathEnvious.properties");
            
            
             if(!appPropFile.exists())
                appPropFile.createNewFile();
             
             if(!apiPropFile.exists())
                 apiPropFile.createNewFile();
            
            SettingProperty.getSettingProperties("goliath.envious").load(new FileInputStream(apiPropFile));
            SettingProperty.getSettingProperties("goliath.envious.fx").load(new FileInputStream(appPropFile));
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
