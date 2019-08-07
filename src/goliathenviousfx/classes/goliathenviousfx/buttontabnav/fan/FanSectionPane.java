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
package goliathenviousfx.buttontabnav.fan;

import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import goliathenviousfx.custom.TileDisplayPane;

public class FanSectionPane extends SectionContentPane
{
    //private final GenericReadableTable tablePane;
    private final GenericControllableSliderBox speedController;
    
    public FanSectionPane(NvGPU g)
    {
        super(NvSettings.getNvGPUInstance(g).getNvFan().getNvTarget());
        
        List<ReadOnlyNvReadable> attrs = new ArrayList<>();
        attrs.add(NvSMI.getNvGPUInstance(g).getFanSpeed());
        attrs.add(NvSettings.getNvGPUInstance(g).getNvFan().getFanTargetSpeed());
        attrs.add(NvSettings.getNvGPUInstance(g).getNvFan().getFanRPMSpeed());
        
        speedController = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getNvFan().getFanTargetSpeed());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(new TileDisplayPane(attrs));
        super.addTo(space);
        super.addTo(speedController);
    }
}
