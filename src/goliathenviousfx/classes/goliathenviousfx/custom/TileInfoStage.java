/*
 * The MIT License
 *
 * Copyright 2019 ty.
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
package goliathenviousfx.custom;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import static goliathenviousfx.GoliathEnviousFX.SCALE;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TileInfoStage extends Stage
{   
    public TileInfoStage(ReadOnlyNvReadable<?> rdbl)
    {
        super();
        super.setMinWidth(950*SCALE);
        super.setMinHeight(480*SCALE);
        super.setWidth(1000);
        super.setHeight(800);
        super.setTitle("Goliath Envious FX " + rdbl.displayNameProperty().get() + " Info");
        
        super.setScene(new Scene(new TileNvReadablePane(rdbl)));
        super.getScene().getStylesheets().add("goliath/css/Goliath-Envy.css");
        //super.getScene().getRoot().setStyle(" -fx-font-size: " + Font.getDefault().getSize()*SCALE + "px;");
    }
}
