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
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.TileDisplayPane;
import java.util.ArrayList;
import java.util.List;

public class GPUPerformanceLimitReasons extends SectionContentPane
{   
    public GPUPerformanceLimitReasons(NvGPU g)
    {
        super(g.getNvTarget() + " Performance Limiters");
        
        List<ReadOnlyNvReadable> rdbls = new ArrayList<>();
        
        rdbls.add(NvSMI.getNvGPUInstance(g).getIdlePerformanceLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getAppClockSettingsPerformanceLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getSoftwarePowerCapPerformanceLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getHardwareThermalSlowdownPerformanceLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getHardwarePowerBrakeSlowdownPerformanceLimit());
        rdbls.add(NvSMI.getNvGPUInstance(g).getSyncBoostPerformanceLimit());
        
        super.addTo(new TileDisplayPane(rdbls));
    }
}
