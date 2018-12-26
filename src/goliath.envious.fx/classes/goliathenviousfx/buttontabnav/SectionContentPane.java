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
package goliathenviousfx.buttontabnav;

import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.custom.Space;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SectionContentPane extends VBox
{
    public SectionContentPane(String title)
    {
        super();
        super.setAlignment(Pos.CENTER);

        super.getChildren().add(new Label(title));
        
        DoubleBinding width = super.widthProperty().multiply(.85);
        DoubleBinding height = super.heightProperty().multiply(.003);
        
        Space space = new Space(false);
        space.minHeightProperty().bind(height);
        space.maxHeightProperty().bind(height);
        space.minWidthProperty().bind(width);
        space.maxWidthProperty().bind(width);
        
        super.getChildren().add(space);
        
        ((Label)super.getChildren().get(0)).setStyle("-fx-background-color: -fx-theme-header;");
        ((Label)super.getChildren().get(0)).setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, GoliathENVIOUSFX.FONT.getSize()));
        ((Label)super.getChildren().get(0)).setAlignment(Pos.CENTER);
        ((Label)super.getChildren().get(0)).setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        ((Label)super.getChildren().get(0)).minWidthProperty().bind(width);
        ((Label)super.getChildren().get(0)).maxWidthProperty().bind(width);
    }
}
