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

import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GenericComboEnumPane<E> extends BorderPane
{
    private final ReadOnlyNvReadable<E> readable;
    private final ComboBox<E> combo;
    private final Button apply;
    private final Button reset;
    private final Label text;
    
    public GenericComboEnumPane(ReadOnlyNvReadable<E> rdbl)
    {
        super();
        super.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        readable = rdbl;
        readable.valueProperty().addListener(new ReadableListener());
        
        text = new Label(rdbl.displayNameProperty().get());
        text.setAlignment(Pos.CENTER_LEFT);
        
        combo = new ComboBox<>(FXCollections.observableArrayList(rdbl.getController().get().getAllValues().getAllInRange()));
        combo.getSelectionModel().select(rdbl.getCurrentValue());
        
        apply = new Button("Apply");
        apply.setOnMouseClicked(new ApplyHandler());
        apply.prefWidthProperty().bind(super.widthProperty().multiply(.1));
        apply.prefHeightProperty().bind(super.heightProperty().multiply(.25));
        
        reset = new Button("Reset");
        reset.setOnMouseClicked(new ResetHandler());
        reset.prefWidthProperty().bind(super.widthProperty().multiply(.1));
        reset.prefHeightProperty().bind(super.heightProperty().multiply(.25));
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10*GoliathENVIOUSFX.SCALE);
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.setTop(text);
        super.setBottom(buttonBox);
        super.setRight(combo);
    }
    
    private class ReadableListener implements ChangeListener<E>
    {
        @Override
        public void changed(ObservableValue<? extends E> observable, E oldValue, E newValue)
        {
            combo.setValue(newValue);
        }
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                readable.getController().get().setValue(combo.getValue());
                AppStatusBar.setActionText(readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
            }
        }          
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                readable.getController().get().reset();
                AppStatusBar.setActionText(readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ControllerResetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
            }
            combo.setValue(readable.getCurrentValue());
        }          
    }
}
