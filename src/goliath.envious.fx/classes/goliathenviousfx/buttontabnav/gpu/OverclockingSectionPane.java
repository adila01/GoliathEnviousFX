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

import goliath.nvsettings.targets.NvSettingsGPU;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.DoubleBinding;

public class OverclockingSectionPane extends SectionContentPane
{ 
    private final NvSettingsGPU gpu;
    private final List<Space> spaces;
    private final GenericControllableSliderBox coreControl;
    private final GenericControllableSliderBox memoryControl;
    private final GenericControllableSliderBox voltageControl;
    
    public OverclockingSectionPane(NvSettingsGPU g)
    {
        super(g.getTargetString() + " Overclocking");
        
        gpu = g;
        
        spaces = new ArrayList<>();
        DoubleBinding bind = super.widthProperty().multiply(.85);
        
        for(int i = 0; i < 2; i++)
        {
            spaces.add(new Space(false));
            spaces.get(i).minHeightProperty().bind(super.heightProperty().multiply(.003));
            spaces.get(i).maxHeightProperty().bind(super.heightProperty().multiply(.005));
            spaces.get(i).minWidthProperty().bind(bind);
            spaces.get(i).maxWidthProperty().bind(bind);
        }
        
        coreControl = new GenericControllableSliderBox(g.getCoreOffset());
        coreControl.minWidthProperty().bind(bind);
        coreControl.maxWidthProperty().bind(bind);
        
        memoryControl = new GenericControllableSliderBox(g.getMemoryOffset());
        memoryControl.minWidthProperty().bind(bind);
        memoryControl.maxWidthProperty().bind(bind);
        
        voltageControl = new GenericControllableSliderBox(g.getVoltageOffset());
        voltageControl.minWidthProperty().bind(bind);
        voltageControl.maxWidthProperty().bind(bind);

        super.getChildren().add(coreControl);
        super.getChildren().add(spaces.get(0));
        super.getChildren().add(memoryControl);
        super.getChildren().add(spaces.get(1));
        super.getChildren().add(voltageControl);
    }
}
