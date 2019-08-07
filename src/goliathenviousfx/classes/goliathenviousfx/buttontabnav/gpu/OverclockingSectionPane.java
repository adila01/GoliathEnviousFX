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
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.TileDisplayPane;
import java.util.ArrayList;
import java.util.List;

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
        
        List<ReadOnlyNvReadable> rdbls = new ArrayList<>();
        rdbls.add(NvSMI.getNvGPUInstance(g).getCoreClock());
        rdbls.add(NvSettings.getNvGPUInstance(g).getCoreOffset());
        rdbls.add(NvSMI.getNvGPUInstance(g).getMemoryClock());
        rdbls.add(NvSettings.getNvGPUInstance(g).getMemoryOffset());
        rdbls.add(NvSettings.getNvGPUInstance(g).getVoltageCurrent());
        rdbls.add(NvSettings.getNvGPUInstance(g).getVoltageOffset());
        
        coreControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getCoreOffset());
        
        memoryControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getMemoryOffset());
        
        voltageControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getVoltageOffset());

        super.addTo(new TileDisplayPane(rdbls));
        super.addTo(spaces.get(0));
        super.addTo(coreControl);
        super.addTo(spaces.get(1));
        super.addTo(memoryControl);
        super.addTo(spaces.get(2));
        super.addTo(voltageControl);
    }
}
