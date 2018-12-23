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
package goliathenviousfx.buttontabnav.smi;

import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTablePane;

public class SMISectionPane extends SectionContentPane
{
    private final GenericReadableTablePane smiTable;
    private final GenericControllableSliderBox powerSlider;
    
    public SMISectionPane()
    {
        super("NvSMI Info & Control");
        
        smiTable = new GenericReadableTablePane(NvSMI.READABLES);
        
        powerSlider = new GenericControllableSliderBox(NvSMI.getPowerLimit());
        powerSlider.minWidthProperty().bind(super.widthProperty().multiply(.85));
        powerSlider.maxWidthProperty().bind(super.widthProperty().multiply(.85));
        
        super.getChildren().add(smiTable);
        super.getChildren().add(powerSlider);
    }
}
