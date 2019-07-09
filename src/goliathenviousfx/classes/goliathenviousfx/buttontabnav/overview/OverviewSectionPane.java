/*
 * The MIT License
 *
 * Copyright 2018 ty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliathenviousfx.buttontabnav.overview;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class OverviewSectionPane extends SectionContentPane
{
    private final TilePane tilePane;
    
    public OverviewSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " Overview");

        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(15*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile gpuNameTile = new Tile();
        gpuNameTile.setNvReadable(g.getNameNvReadable());
        
        Tile gpuTemp = new Tile();
        gpuTemp.setNvReadable(NvSMI.getNvGPUInstance(g).getCoreTemp());
        
        Tile gpuIdTile = new Tile();
        gpuIdTile.setNvReadable(NvSMI.getNvGPUInstance(g).getBIOSVersion());
        
        Tile gpuUUIDTile = new Tile();
        gpuUUIDTile.setNvReadable(NvSettings.getNvGPUInstance(g).getUuid());
        
        Tile gpuCores = new Tile();
        gpuCores.setNvReadable(NvSettings.getNvGPUInstance(g).getCUDACores()); 
        
        Tile coreUsage = new Tile();
        coreUsage.setNvReadable(NvSMI.getNvGPUInstance(g).getCoreUtilization()); 
        
        Tile usedMemory = new Tile();
        usedMemory.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryUsed()); 
        
        Tile coreClock = new Tile();
        coreClock.setNvReadable(NvSMI.getNvGPUInstance(g).getCoreClock()); 
        
        Tile memoryClock = new Tile();
        memoryClock.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryClock()); 
        
        Tile totalMemory = new Tile();
        totalMemory.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryTotal()); 

        Tile memoryInterface = new Tile();
        memoryInterface.setNvReadable(NvSettings.getNvGPUInstance(g).getMemoryInterface()); 
        
        Tile memoryBandwidthUsage = new Tile();
        memoryBandwidthUsage.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryUtilization()); 
        
        Tile pcieGen = new Tile();
        pcieGen.setNvReadable(NvSMI.getNvGPUInstance(g).getPCIeGen());

        Tile pcieCurrentWidth = new Tile();
        pcieCurrentWidth.setNvReadable(NvSMI.getNvGPUInstance(g).getPCIeCurrentWidth());
        
        Tile pcieCurrentSpeed = new Tile();
        pcieCurrentSpeed.setNvReadable(NvSettings.getNvGPUInstance(g).getPCIeCurrentSpeed());
        
        Tile pcieUsage = new Tile();
        pcieUsage.setNvReadable(NvSettings.getNvGPUInstance(g).getPCIeUtilization());
        
        Tile coreOCSupported = new Tile();
        coreOCSupported.addLabel("Overclocking Support");
        
        if(NvSettings.getNvGPUInstance(g).getCoreOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            coreOCSupported.addLabel("Enabled");
        else
            coreOCSupported.addLabel("Disabled");
        
        Tile voltageSupport = new Tile();
        voltageSupport.addLabel("Voltage Support");
        
        if(NvSettings.getNvGPUInstance(g).getVoltageOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            voltageSupport.addLabel("Enabled");
        else
            voltageSupport.addLabel("Disabled");
        
        Tile fanControlSupport = new Tile();
        fanControlSupport.addLabel("Fan Control Support");
        
        if(NvSettings.getNvGPUInstance(g).getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            fanControlSupport.addLabel("Enabled");
        else
            fanControlSupport.addLabel("Disabled");
        
        Tile powerLimitSupport = new Tile();
        powerLimitSupport.addLabel("Power Management Support");
        
        if(NvSMI.getPrimaryNvGPUInstance().getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            powerLimitSupport.addLabel("Enabled");
        else if(NvSMI.getPrimaryNvGPUInstance().getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE))
            powerLimitSupport.addLabel("Disabled - Root Required");
        else
            powerLimitSupport.addLabel("Disabled - Not Supported");

        tilePane.getChildren().add(gpuNameTile);
        tilePane.getChildren().add(gpuIdTile);
        tilePane.getChildren().add(gpuCores);
        tilePane.getChildren().add(gpuUUIDTile);
        tilePane.getChildren().add(coreUsage);
        tilePane.getChildren().add(coreClock);
        tilePane.getChildren().add(memoryClock);
        tilePane.getChildren().add(gpuTemp);
        tilePane.getChildren().add(usedMemory);
        tilePane.getChildren().add(totalMemory);
        tilePane.getChildren().add(memoryInterface);
        tilePane.getChildren().add(memoryBandwidthUsage);
        tilePane.getChildren().add(pcieGen);
        tilePane.getChildren().add(pcieCurrentWidth);
        tilePane.getChildren().add(pcieCurrentSpeed);
        tilePane.getChildren().add(pcieUsage);
        tilePane.getChildren().add(coreOCSupported);
        tilePane.getChildren().add(voltageSupport);
        tilePane.getChildren().add(fanControlSupport);
        tilePane.getChildren().add(powerLimitSupport);
        
        super.addTo(tilePane);
    }
}
