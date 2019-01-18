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
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
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
        super(g.getTargetString() + " Overclocking");
        
        spaces = new ArrayList<>();
        
        for(int i = 0; i < 2; i++)
        {
            spaces.add(new Space(false));
            spaces.get(i).setMinHeight(1*GoliathENVIOUSFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathENVIOUSFX.SCALE);
        }
        
        coreControl = new GenericControllableSliderBox(NvSettings.getInstance(g).getCoreOffset());
        
        memoryControl = new GenericControllableSliderBox(NvSettings.getInstance(g).getMemoryOffset());
        
        voltageControl = new GenericControllableSliderBox(NvSettings.getInstance(g).getVoltageOffset());

        super.add(coreControl, 0, super.getRowCount());
        super.add(spaces.get(0), 0, super.getRowCount());
        super.add(memoryControl, 0, super.getRowCount());
        super.add(spaces.get(1), 0, super.getRowCount());
        super.add(voltageControl, 0, super.getRowCount());
    }
}
