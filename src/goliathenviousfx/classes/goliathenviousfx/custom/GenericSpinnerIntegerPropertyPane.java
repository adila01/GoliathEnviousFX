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
package goliathenviousfx.custom;

import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathEnviousFX;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class GenericSpinnerIntegerPropertyPane<E> extends BorderPane
{
    private final Spinner<Integer> spinner;
    private final Integer defaultValue;
    private final String property;
    private final Button apply;
    private final Button reset;
    private final Label text;
    
    public GenericSpinnerIntegerPropertyPane(String name, String prop, Spinner<Integer> spin)
    {
        super();
        super.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        property = prop;
        spinner = spin;
        spinner.setPrefWidth(100*GoliathEnviousFX.SCALE);
        spinner.valueProperty().addListener(new SpinnerListener());
        
        defaultValue = spinner.getValue();
        
        text = new Label(name);
        text.setAlignment(Pos.CENTER_LEFT);
        
        apply = new Button("Apply");
        apply.setTooltip(new Tooltip("Set value to " + spin.getValue()));
        apply.setPrefWidth(Integer.MAX_VALUE);
        apply.setOnMouseClicked(new ApplyHandler());
        
        reset = new Button("Reset");
        reset.setTooltip(new Tooltip("Reset value to " + spin.getValue()));
        reset.setPrefWidth(Integer.MAX_VALUE);
        reset.setOnMouseClicked(new ResetHandler());
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setMinWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.setMaxWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.setTop(text);
        super.setBottom(buttonBox);
        super.setRight(spin);
    }
    
    private class SpinnerListener implements ChangeListener<Integer>
    {
        @Override
        public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1)
        {
            apply.setTooltip(new Tooltip("Set value to " + t1.toString()));
        }
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {

            System.getProperties().setProperty(property, spinner.getValue().toString());
            GoliathEnviousFX.saveProperties();
            AppStatusBar.setActionText(text.getText() + " has been set to " + spinner.getValue().toString() + ". A restart is required for this to take affect.");
        }          
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            System.getProperties().setProperty(property, String.valueOf(defaultValue));
            GoliathEnviousFX.saveProperties();
            AppStatusBar.setActionText(property + " has been reset to " + defaultValue + ". A restart is required for this to take affect.");
            
            spinner.getValueFactory().setValue(defaultValue);
        }          
    }
}
