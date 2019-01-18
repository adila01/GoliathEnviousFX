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
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTable;

public class FanSectionPane extends SectionContentPane
{
    private final GenericReadableTable tablePane;
    private final GenericControllableSliderBox speedController;
    
    public FanSectionPane(NvFan fn)
    {
        super(fn.getTargetString() + " Info & Control");
        
        tablePane = new GenericReadableTable(fn.getNvReadables());
        tablePane.setMinHeight(125*GoliathENVIOUSFX.SCALE);
        tablePane.setMaxHeight(125*GoliathENVIOUSFX.SCALE);
        
        speedController = new GenericControllableSliderBox(fn.getFanTargetSpeed());
        
        super.add(tablePane, 0, super.getRowCount());
        super.add(speedController, 0, super.getRowCount());
    }
}
