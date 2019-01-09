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
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericComboEnumPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTablePane;
import goliathenviousfx.custom.Space;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;

public class NvSettingsSectionPane extends SectionContentPane
{
    private final GenericReadableTablePane tablePane;
    private final GenericControllableSliderBox logoBrightnesControl;
    private final GenericComboEnumPane fanModeControl;
    
    public NvSettingsSectionPane(NvGPU g)
    {
        super(g.getTargetString() + " NvSettings Info & Control");
        
        DoubleBinding tableHeightBinding = super.heightProperty().multiply(.50);
        tablePane = new GenericReadableTablePane(NvSettings.getInstance(g).getNvReadables());
        tablePane.minHeightProperty().bind(tableHeightBinding);
        tablePane.maxHeightProperty().bind(tableHeightBinding);
        tablePane.setAlignment(Pos.CENTER);
        
        DoubleBinding bind = super.widthProperty().multiply(.85);
        
        logoBrightnesControl = new GenericControllableSliderBox(NvSettings.getInstance(g).getLogoBrightness());
        logoBrightnesControl.minWidthProperty().bind(bind);
        logoBrightnesControl.maxWidthProperty().bind(bind);
        
        fanModeControl = new GenericComboEnumPane(NvSettings.getInstance(g).getFanMode());
        fanModeControl.minWidthProperty().bind(bind);
        fanModeControl.maxWidthProperty().bind(bind);
        
        DoubleBinding spaceBind = super.heightProperty().multiply(.001);
        Space space = new Space(false);
        space.minHeightProperty().bind(spaceBind);
        space.maxHeightProperty().bind(spaceBind);
        space.minWidthProperty().bind(bind);
        space.maxWidthProperty().bind(bind);
        
        super.getChildren().add(tablePane);
        super.getChildren().add(logoBrightnesControl);
        super.getChildren().add(space);
        super.getChildren().add(fanModeControl);
    }
    
}
