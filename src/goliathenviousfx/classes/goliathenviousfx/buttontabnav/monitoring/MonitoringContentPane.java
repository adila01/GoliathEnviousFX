package goliathenviousfx.buttontabnav.monitoring;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.NvTarget;
import goliath.nvsettings.main.NvFan;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsettings.performance.PerformanceLevel;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.settings.AppSettings;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.ContentPane;
import goliathenviousfx.buttontabnav.SectionContentPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;

public class MonitoringContentPane extends ContentPane
{
    public MonitoringContentPane()
    {
        super();
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {   
            List<NvMonitorPane> charts = new ArrayList<>();
            
            PerformanceLevel lev = NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPerfModes().getValue().get(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPerfModes().getValue().size()-1);
            
            int coreMax = lev.coreMaxProperty().get() + NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreOffset().getController().get().getMaxValue();
            int memoryMax = lev.effectiveMaxProperty().get() + NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryOffset().getController().get().getMaxValue();
            
            
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreUtilization(), 100));
            
            if(AppSettings.getNvSMIOnly().getValue())
                charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreClock(), 0));
            else
                charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreClock(), coreMax));
            
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreTemp(), 100));
            
            if(!AppSettings.getNvSMIOnly().getValue() && NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageCurrent().getOperationalStatus().equals(OperationalStatus.READABLE))
                charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageCurrent(), 0));
            
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPowerDraw(), NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMaxPowerLimit().getValue()));
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryUtilization(), 100));
            
            if(AppSettings.getNvSMIOnly().getValue())
                charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryClock(), 0));
            else
                charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryClock(), memoryMax));
            
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryUsed(), NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryTotal().getValue()));

            if(!AppSettings.getNvSMIOnly().getValue())
            {
                charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPCIeUtilization(), 100));
                charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEncoderUtilization(), 100));
                charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoDecoderUtilization(), 100));
                charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEngineUtilization(), 100));
            }
            
            this.addCharts(charts, NvGPU.getNvGPUList().get(i));
        }
    }
    
    public MonitoringContentPane(NvGPU g)
    {
        super();
        
        List<NvMonitorPane> charts = new ArrayList<>();
        
        PerformanceLevel lev = NvSettings.getNvGPUInstance(g).getPerfModes().getValue().get(NvSettings.getNvGPUInstance(g).getPerfModes().getValue().size()-1);

        int coreMax = lev.coreMaxProperty().get() + NvSettings.getNvGPUInstance(g).getCoreOffset().getController().get().getMaxValue();
        int memoryMax = lev.effectiveMaxProperty().get() + NvSettings.getNvGPUInstance(g).getMemoryOffset().getController().get().getMaxValue();
        
        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getCoreUtilization(), 100));
        
        if(AppSettings.getNvSMIOnly().getValue())
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getCoreClock(), 0));
        else
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getCoreClock(), coreMax));
        
        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getCoreTemp(), 100));

        if(!AppSettings.getNvSMIOnly().getValue() && NvSettings.getNvGPUInstance(g).getVoltageCurrent().getOperationalStatus().equals(OperationalStatus.READABLE))
            charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(g).getVoltageCurrent(), 0));

        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getPowerDraw(), NvSMI.getNvGPUInstance(g).getMaxPowerLimit().getValue()));
        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getMemoryUtilization(), 100));
        
        if(AppSettings.getNvSMIOnly().getValue())
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getMemoryClock(), 0));
        else
            charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getMemoryClock(), memoryMax));
        
        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(g).getMemoryUsed(), NvSMI.getNvGPUInstance(g).getMemoryTotal().getValue()));

        if(!AppSettings.getNvSMIOnly().getValue())
        {
            charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(g).getPCIeUtilization(), 100));
            charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(g).getVideoEncoderUtilization(), 100));
            charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(g).getVideoDecoderUtilization(), 100));
            charts.add(new NvMonitorPane(NvSettings.getNvGPUInstance(g).getVideoEngineUtilization(), 100));
        }

        this.addCharts(charts, g);
    }
    
    public MonitoringContentPane(NvFan f)
    {
        super();
        
        List<NvMonitorPane> charts = new ArrayList<>();

        charts.add(new NvMonitorPane(NvSMI.getNvGPUInstance(f.getFanOwner()).getFanSpeed(), 100));
        
        if(!AppSettings.getNvSMIOnly().getValue())
            charts.add(new NvMonitorPane(f.getFanRPMSpeed(), 0));

        this.addCharts(charts, f);
    }
    
    private void addCharts(List<NvMonitorPane> charts, NvTarget targ)
    {
        for(int k = 0; k < charts.size(); k++)
        {
            HBox tmpBox = null;

            if(tmpBox == null || tmpBox.getChildren().size() == 2)
            {
                tmpBox = new HBox();
                tmpBox.setSpacing(16 * GoliathEnviousFX.SCALE);
                tmpBox.setPrefWidth(Integer.MAX_VALUE);

                tmpBox.getChildren().add(new SectionContentPane(targ.getNvTarget() + " " + charts.get(k).getNvReadable().displayNameProperty().get(), charts.get(k)));

                if(k != charts.size()-1 && AppSettings.getCompactMonitors().getValue())
                {
                    tmpBox.getChildren().add(new SectionContentPane(targ.getNvTarget() + " " + charts.get(k+1).getNvReadable().displayNameProperty().get(), charts.get(k+1)));
                    k++;
                } 
                super.addTo(tmpBox);
            }
            else
                tmpBox.getChildren().add(new SectionContentPane(targ.getNvTarget() + " " + charts.get(k).getNvReadable().displayNameProperty().get(), charts.get(k)));
        }
    }
}
