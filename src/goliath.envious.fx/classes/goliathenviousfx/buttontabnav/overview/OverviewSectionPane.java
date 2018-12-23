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
import goliath.nvsettings.targets.NvSettingsGPU;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class OverviewSectionPane extends SectionContentPane
{
    private final NvSettingsGPU gpu;
    private final TilePane flowPane;
    
    public OverviewSectionPane(NvSettingsGPU g)
    {
        super(g.getTargetString() + " Overview");
        
        flowPane = new TilePane();
        flowPane.setStyle("-fx-background-color: -fx-theme-header;");
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE));
        flowPane.setVgap(8*GoliathENVIOUSFX.SCALE);
        flowPane.setHgap(30*GoliathENVIOUSFX.SCALE);
        flowPane.minWidthProperty().bind(super.widthProperty().multiply(.85));
        flowPane.maxWidthProperty().bind(super.widthProperty().multiply(.85));
        
        Tile gpuNameTile = new Tile();
        gpuNameTile.addLabel(g.idProperty().get() + " Name");
        gpuNameTile.addLabel(g.nameProperty());
        
        Tile gpuTemp = new Tile();
        gpuTemp.setNvReadable(g.getCoreTemp());
        
        Tile gpuIdTile = new Tile();
        gpuIdTile.addLabel(g.idProperty().get() + " NvSettings ID");
        gpuIdTile.addLabel(g.idProperty());
        
        Tile gpuUUIDTile = new Tile();
        gpuUUIDTile.setNvReadable(g.getUuid());
        
        Tile gpuCores = new Tile();
        gpuCores.setNvReadable(g.getCUDACores()); 
        
        Tile coreUsage = new Tile();
        coreUsage.setNvReadable(g.getCoreUsage()); 
        
        Tile usedMemory = new Tile();
        usedMemory.setNvReadable(g.getUsedGPUMemory()); 
        
        Tile coreClock = new Tile();
        coreClock.setNvReadable(g.getCoreClock()); 
        
        Tile memoryClock = new Tile();
        memoryClock.setNvReadable(g.getMemoryClock()); 
        
        Tile totalMemory = new Tile();
        totalMemory.setNvReadable(g.getDedicatedMemory()); 

        Tile memoryInterface = new Tile();
        memoryInterface.setNvReadable(g.getMemoryInterface()); 
        
        Tile memoryBandwidthUsage = new Tile();
        memoryBandwidthUsage.setNvReadable(g.getMemoryUsage()); 
        
        Tile pcieGen = new Tile();
        pcieGen.setNvReadable(g.getPCIeGen());

        Tile pcieCurrentWidth = new Tile();
        pcieCurrentWidth.setNvReadable(g.getPCIeCurrentWidth());
        
        Tile pcieCurrentSpeed = new Tile();
        pcieCurrentSpeed.setNvReadable(g.getPCIeCurrentSpeed());
        
        Tile pcieUsage = new Tile();
        pcieUsage.setNvReadable(g.getPCIeUsage());
        
        Tile coreOCSupported = new Tile();
        coreOCSupported.addLabel("Overclocking Support");
        
        if(g.getCoreOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            coreOCSupported.addLabel("Enabled");
        else
            coreOCSupported.addLabel("Disabled");
        
        Tile voltageSupport = new Tile();
        voltageSupport.addLabel("Voltage Support");
        
        if(g.getVoltageOffset().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            voltageSupport.addLabel("Enabled");
        else
            voltageSupport.addLabel("Disabled");
        
        Tile fanControlSupport = new Tile();
        fanControlSupport.addLabel("Fan Control Support");
        
        if(g.getFanMode().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            fanControlSupport.addLabel("Enabled");
        else
            fanControlSupport.addLabel("Disabled");
        
        
        Tile powerLimitSupport = new Tile();
        powerLimitSupport.addLabel("Power Management Support");
        
        if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
            powerLimitSupport.addLabel("Enabled");
        else if(NvSMI.getPowerLimit().getOperationalStatus().equals(OperationalStatus.READABLE))
            powerLimitSupport.addLabel("Disabled - Root Required");
        else
            powerLimitSupport.addLabel("Disabled - Not Supported");
        
        flowPane.getChildren().add(gpuNameTile);
        flowPane.getChildren().add(gpuIdTile);
        flowPane.getChildren().add(gpuCores);
        flowPane.getChildren().add(gpuUUIDTile);
        flowPane.getChildren().add(coreUsage);
        flowPane.getChildren().add(coreClock);
        flowPane.getChildren().add(memoryClock);
        flowPane.getChildren().add(gpuTemp);
        flowPane.getChildren().add(usedMemory);
        flowPane.getChildren().add(totalMemory);
        flowPane.getChildren().add(memoryInterface);
        flowPane.getChildren().add(memoryBandwidthUsage);
        flowPane.getChildren().add(pcieGen);
        flowPane.getChildren().add(pcieCurrentWidth);
        flowPane.getChildren().add(pcieCurrentSpeed);
        flowPane.getChildren().add(pcieUsage);
        flowPane.getChildren().add(coreOCSupported);
        flowPane.getChildren().add(voltageSupport);
        flowPane.getChildren().add(fanControlSupport);
        flowPane.getChildren().add(powerLimitSupport);
        
        gpu = g;
        
        super.getChildren().add(flowPane);
    }
}
