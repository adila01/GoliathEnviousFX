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

import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class ReactionDescSectionContentPane extends SectionContentPane
{
    public ReactionDescSectionContentPane()
    {
        super("Envious Reactions Description & Usage");
        
        Label desc = new Label();
        desc.setPrefWidth(Integer.MAX_VALUE);
        desc.setAlignment(Pos.CENTER);
        desc.setStyle("-fx-background-color: -fx-theme-header;");
        
        desc.setPrefHeight(200*GoliathEnviousFX.SCALE);
        
        desc.setText("NvReactions is an advanced feature that allows binding of GPU attributes to other controllable Nvidia attributes.\n\n"
                + "This allows for easy automated control over certain controllable GPU attributes but can be dangerous.\n\n"
                + "You may also optionally set a non-zero offset that can be used to manipulate the applied value.\n\n"
                + "Some NvReactions may require you to change the values for other nvidia attributes before they may be used.\n\n"
                + "If the dependant Nvidia attribute and the specified offset is greater or less then\n\n"
                + "the automated Nvidia attribute controller's min/max then the min/max will be used instead.");
        
        
        super.addTo(desc);
    }
}
