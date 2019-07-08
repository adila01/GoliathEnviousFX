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
package goliathenviousfx.buttontabnav.gpu;

import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class OverclockingSectionPane extends SectionContentPane
{ 
    private final List<Space> spaces;
    private final GenericControllableSliderBox coreControl;
    private final GenericControllableSliderBox memoryControl;
    private final GenericControllableSliderBox voltageControl;
    
    public OverclockingSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " Overclocking");
        
        spaces = new ArrayList<>();
        
        for(int i = 0; i < 3; i++)
        {
            spaces.add(new Space(true));
            spaces.get(i).setMinHeight(1*GoliathEnviousFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathEnviousFX.SCALE);
        }
        
        TilePane tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(15*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile coreSpeed = new Tile();
        coreSpeed.setNvReadable(NvSMI.getNvGPUInstance(g).getCoreClock());
        
        Tile coreOffset = new Tile();
        coreOffset.setNvReadable(NvSettings.getNvGPUInstance(g).getCoreOffset());
        
        Tile memoryClock = new Tile();
        memoryClock.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryClock());
        
        Tile memoryOffset = new Tile();
        memoryOffset.setNvReadable(NvSettings.getNvGPUInstance(g).getMemoryOffset());
        
        Tile voltage = new Tile();
        voltage.setNvReadable(NvSettings.getNvGPUInstance(g).getCurrentVoltage());
        
        Tile voltageOffset = new Tile();
        voltageOffset.setNvReadable(NvSettings.getNvGPUInstance(g).getVoltageOffset());
        
        tilePane.getChildren().add(coreSpeed);
        tilePane.getChildren().add(coreOffset);
        tilePane.getChildren().add(memoryClock);
        tilePane.getChildren().add(memoryOffset);
        tilePane.getChildren().add(voltage);
        tilePane.getChildren().add(voltageOffset);
        
        coreControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getCoreOffset());
        
        memoryControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getMemoryOffset());
        
        voltageControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getVoltageOffset());

        super.addTo(tilePane);
        super.addTo(spaces.get(0));
        super.addTo(coreControl);
        super.addTo(spaces.get(1));
        super.addTo(memoryControl);
        super.addTo(spaces.get(2));
        super.addTo(voltageControl);
    }
}
