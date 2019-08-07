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
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.TileDisplayPane;
import java.util.ArrayList;
import java.util.List;

public class GPUUtilizationSectionPane extends SectionContentPane
{
    public GPUUtilizationSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " Utilization");
        
        List<ReadOnlyNvReadable> rdbls = new ArrayList<>();
        
        rdbls.add(NvSMI.getNvGPUInstance(g).getCoreUtilization());
        rdbls.add(NvSMI.getNvGPUInstance(g).getMemoryUtilization());
        rdbls.add(NvSettings.getNvGPUInstance(g).getPCIeUtilization());
        rdbls.add(NvSettings.getNvGPUInstance(g).getVideoEngineUtilization());
        rdbls.add(NvSettings.getNvGPUInstance(g).getVideoDecoderUtilization());
        rdbls.add(NvSettings.getNvGPUInstance(g).getVideoEncoderUtilization());
        
        super.addTo(new TileDisplayPane(rdbls));
    }
}
