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

import goliath.nvsettings.main.NvFan;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTablePane;
import javafx.beans.binding.DoubleBinding;

public class FanSectionPane extends SectionContentPane
{
    private final GenericReadableTablePane tablePane;
    private final GenericControllableSliderBox speedController;
    
    public FanSectionPane(NvFan fn)
    {
        super(fn.getTargetString() + " Info & Control");
        
        DoubleBinding widthBind = super.widthProperty().multiply(.85);
        DoubleBinding heightBind = super.heightProperty().multiply(.50);
        
        tablePane = new GenericReadableTablePane(fn.getNvReadables());
        tablePane.minHeightProperty().bind(heightBind);
        tablePane.maxHeightProperty().bind(heightBind);
        
        speedController = new GenericControllableSliderBox(fn.getFanTargetSpeed());
        speedController.minWidthProperty().bind(widthBind);
        speedController.maxWidthProperty().bind(widthBind);
        
        super.getChildren().add(tablePane);
        super.getChildren().add(speedController);
    }
}
