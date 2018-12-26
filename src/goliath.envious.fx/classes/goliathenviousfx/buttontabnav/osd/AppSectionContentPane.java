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
package goliathenviousfx.buttontabnav.osd;

import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Space;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class AppSectionContentPane extends SectionContentPane
{
    private final Label label;
    
    public AppSectionContentPane()
    {
        super("About Goliath Envious FX V1");
        
        DoubleBinding bind = super.widthProperty().multiply(.85);

        label = new Label("Goliath Envious FX is a modern Java/JavaFX frontend for GoliathENVIOUS Linux Nvidia GPU overclocking library.");
        label.setAlignment(Pos.CENTER);
        label.minWidthProperty().bind(bind);
        label.maxWidthProperty().bind(bind);
        label.setStyle("-fx-background-color: -fx-theme-header;");
        label.setPadding(new Insets(8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE));
        
        super.getChildren().add(label);
    }
}
