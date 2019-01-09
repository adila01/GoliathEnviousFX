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
package goliathenviousfx.buttontabnav.reactions;

import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.TextArea;

public class ReactionDescSectionContentPane extends SectionContentPane
{
    public ReactionDescSectionContentPane()
    {
        super("Envious Reactions Description & Usage");
        
        DoubleBinding widthBind = super.widthProperty().multiply(.85);
        
        TextArea area = new TextArea();
        area.setFocusTraversable(false);
        area.setEditable(false);
        area.minWidthProperty().bind(widthBind);
        area.maxWidthProperty().bind(widthBind);
        area.setMinHeight(200*GoliathENVIOUSFX.SCALE);
        
        area.setText("Envious Reactions is an advanced feature that allows binding of GPU data to equal said data.\n\n"
                + "This allows for easy automated dynamic control over certain controllable GPU attributes but can be dangerous.\n\n"
                + "You may also optionally set a non-zero offset that can be used to manipulate the dependant GPU data.\n\n"
                + "Some NvReactions may require you to change the values for other NvAttributes before they may be used.\n\n"
                + "If the dependant NvAttribute and the specified offset is less/greater than the NvAttribute's controller's min/max\n\n"
                + "then the min/max will be used instead.");
        
        
        super.getChildren().add(area);
    }
}
