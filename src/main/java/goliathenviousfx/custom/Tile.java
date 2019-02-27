/* 
 * The MIT License
 *
 * Copyright 2018 Ty Young.
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
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Tile extends VBox
{   
    private final Tile instance;
    
    public Tile()
    {
        super();
        super.getStyleClass().add("tile");
        super.setPadding(new Insets(8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE,8*GoliathENVIOUSFX.SCALE));
        super.setSpacing(5*GoliathENVIOUSFX.SCALE);
        
        instance = this;
    }
    
    public void setNvReadable(ReadOnlyNvReadable rdbl)
    {
        String text = rdbl.displayNameProperty().get();
        
        if(text.contains(" Performance Limit"))
            text = text.substring(0, text.length()-18);
        
        if(text.contains(" Slowdown"))
            text = text.substring(0, text.length()-9);
        
        super.getChildren().add(new Label(text));
        
        Label lb = new Label(rdbl.displayValueProperty().get());
        rdbl.displayValueProperty().addListener(new ReadableBinder());
        
        super.getChildren().add(lb);
    }
    public void setWidth(int x)
    {
        super.setMinWidth(x);
    }
    public void addLabel(String text)
    {
        super.getChildren().add(new Label(text));
    }
    public void addLabel(ReadOnlyStringProperty text)
    {
        Label label = new Label();
        label.textProperty().bind(text);
        super.getChildren().add(label);
    }
    
    private class ReadableBinder implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            ((Label)instance.getChildren().get(1)).setText(newValue);
        }
    }
}