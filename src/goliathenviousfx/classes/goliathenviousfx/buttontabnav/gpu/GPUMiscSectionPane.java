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
import goliathenviousfx.custom.GenericComboEnumPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTable;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;

public class GPUMiscSectionPane extends SectionContentPane
{
    private final GenericReadableTable tablePane;
    private final GenericControllableSliderBox logoBrightnesControl;
    private final GenericComboEnumPane fanModeControl;
    
    public GPUMiscSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " All Attributes & Misc");

        List<ReadOnlyNvReadable> allReadables = new ArrayList<>();
        allReadables.addAll(NvGPU.getPrimaryNvGPU().getNvReadables());
        allReadables.add(NvSettings.getPrimaryNvGPUInstance().getUuid());
        allReadables.addAll(NvSMI.getPrimaryNvGPUInstance().getNvReadables());
        
        if(!GoliathEnviousFX.smiOnly)
        {
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCUDACores());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCurrentPerformanceLevel());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoEngineUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoEncoderUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoDecoderUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getPCIeUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCoreOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getMemoryOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVoltageOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCurrentVoltage());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getPowerMizerMode());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getNvGPULogoBrightness());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getFanMode());  
            allReadables.addAll(NvSettings.getPrimaryNvGPUInstance().getNvFan().getNvReadables());
        }
        
        tablePane = new GenericReadableTable(allReadables);
        tablePane.setMinHeight(250*GoliathEnviousFX.SCALE);
        tablePane.setMaxHeight(250*GoliathEnviousFX.SCALE);
        
        logoBrightnesControl = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getNvGPULogoBrightness());
        
        fanModeControl = new GenericComboEnumPane(NvSettings.getNvGPUInstance(g).getFanMode());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(tablePane);
        super.addTo(fanModeControl);
        super.addTo(space);
        super.addTo(logoBrightnesControl);
    }
    
}
