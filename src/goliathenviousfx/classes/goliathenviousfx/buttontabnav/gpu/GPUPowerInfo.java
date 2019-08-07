/*
 * The MIT License
 *
 * Copyright 2019 ty.
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
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.Tile;
import goliathenviousfx.custom.TileDisplayPane;
import java.util.ArrayList;
import java.util.List;

public class GPUPowerInfo extends SectionContentPane
{
    private final GenericControllableSliderBox powerControl;
    
    public GPUPowerInfo(NvGPU g)
    {
        super(g.getNvTarget() + " Power");
        
        List<ReadOnlyNvReadable> rdbls = new ArrayList<>();
        
        rdbls.add(NvSMI.getNvGPUInstance(g).getDefaultPowerLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getEnforcedPowerLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getMinPowerLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getMaxPowerLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getPowerLimit()); 
        rdbls.add(NvSMI.getNvGPUInstance(g).getPowerDraw());
        
        powerControl = new GenericControllableSliderBox(NvSMI.getNvGPUInstance(g).getPowerLimit());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(new TileDisplayPane(rdbls));
        super.addTo(space);
        super.addTo(powerControl);
    }
}
