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
package goliathenviousfx;

import goliathenviousfx.custom.Space;
import goliathenviousfx.menu.AppMenu;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;

public class AppRoot extends VBox
{
    private static AppRoot INSTANCE;
    
    private final AppMenu menuBar;
    
    public static AppRoot getInstance()
    {
        return INSTANCE;
    }
    
    public AppRoot()
    {
        super();
        super.setFillWidth(true);
        
        INSTANCE = this;
        
        menuBar = new AppMenu();
        
        
        DoubleBinding heightBinding = super.heightProperty().multiply(.001);
        
        Space topSpace = new Space(false);
        topSpace.minHeightProperty().bind(heightBinding);
        topSpace.maxHeightProperty().bind(heightBinding);
        topSpace.minWidthProperty().bind(super.widthProperty());
        topSpace.maxWidthProperty().bind(super.widthProperty());
        
        Space bottomSpace = new Space(false);
        bottomSpace.minHeightProperty().bind(heightBinding);
        bottomSpace.maxHeightProperty().bind(heightBinding);
        bottomSpace.minWidthProperty().bind(super.widthProperty());
        bottomSpace.maxWidthProperty().bind(super.widthProperty());
        
        super.getChildren().add(menuBar);
        super.getChildren().add(topSpace);
        super.getChildren().add(new MainAppContentBox());
        super.getChildren().add(bottomSpace);
        super.getChildren().add(new AppStatusBar());
    }
}
