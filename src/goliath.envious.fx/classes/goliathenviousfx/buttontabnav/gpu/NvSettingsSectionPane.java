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
import goliathenviousfx.custom.GenericComboEnumPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTable;
import goliathenviousfx.custom.Space;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;

public class NvSettingsSectionPane extends SectionContentPane
{
    private final GenericReadableTable tablePane;
    private final GenericControllableSliderBox logoBrightnesControl;
    private final GenericComboEnumPane fanModeControl;
    
    public NvSettingsSectionPane(NvGPU g)
    {
        super(g.getTargetString() + " NvSettings Info & Control");
        
        tablePane = new GenericReadableTable(NvSettings.getInstance(g).getNvReadables());
        tablePane.setMinHeight(250*GoliathENVIOUSFX.SCALE);
        tablePane.setMaxHeight(250*GoliathENVIOUSFX.SCALE);
        
        logoBrightnesControl = new GenericControllableSliderBox(NvSettings.getInstance(g).getLogoBrightness());
        
        fanModeControl = new GenericComboEnumPane(NvSettings.getInstance(g).getFanMode());
        
        Space space = new Space(false);
        space.setMinHeight(1*GoliathENVIOUSFX.SCALE);
        space.setMaxHeight(1*GoliathENVIOUSFX.SCALE);
        
        super.add(tablePane, 0, super.getRowCount());
        super.add(logoBrightnesControl, 0, super.getRowCount());
        super.add(space, 0, super.getRowCount());
        super.add(fanModeControl, 0, super.getRowCount());
    }
    
}
