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

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.NvControllable;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathEnviousFX;
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
    private ReadOnlyNvReadable<E> readable;
    private NvControllable<E> controller;
    private final ComboBox<E> combo;
    private final Button apply;
    private Button reset;
    private final Label text;
    
    public GenericComboEnumPane(ReadOnlyNvReadable<E> rdbl)
    {
        super();
        super.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        readable = rdbl;
        readable.valueProperty().addListener(new ReadableListener());
        
        text = new Label(rdbl.displayNameProperty().get());
        text.setAlignment(Pos.CENTER_LEFT);
        
        combo = new ComboBox<>(FXCollections.observableArrayList(rdbl.getController().get().getAllValues().getAllInRange()));
        combo.getSelectionModel().select(rdbl.getValue());
        
        apply = new Button("Apply");
        apply.setPrefWidth(Integer.MAX_VALUE);
        apply.setOnMouseClicked(new ApplyHandler());
        
        reset = new Button("Reset");
        reset.setPrefWidth(Integer.MAX_VALUE);
        reset.setOnMouseClicked(new ResetHandler());
        
        if(!rdbl.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            combo.setDisable(true);
            apply.setDisable(true);
            reset.setDisable(true);
        }
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setMinWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.setMaxWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.setTop(text);
        super.setBottom(buttonBox);
        super.setRight(combo);
    }
    
    public GenericComboEnumPane(NvControllable<E> cont)
    {
        super();
        super.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        controller = cont;
        
        text = new Label(cont.getControlName());
        text.setAlignment(Pos.CENTER_LEFT);
        
        combo = new ComboBox<>(FXCollections.observableArrayList(cont.getAllValues().getAllInRange()));
        combo.getSelectionModel().select(cont.getMinValue());
        
        apply = new Button("Apply");
        apply.setPrefWidth(Integer.MAX_VALUE);
        apply.setOnMouseClicked(new ControllerApplyHandler());
        
        if(!cont.getOperationalStatus().equals(OperationalStatus.CONTROLLABLE))
        {
            combo.setDisable(true);
            apply.setDisable(true);
        }
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setMinWidth(100*GoliathEnviousFX.SCALE);
        buttonBox.setMaxWidth(100*GoliathEnviousFX.SCALE);
        buttonBox.getChildren().add(apply);
        
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
                AppStatusBar.setActionText(readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.nvTargetProperty().get() + " " + readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + ": " + ex.getLocalizedMessage() + ".");
            }
        }          
    }
    
    private class ControllerApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                controller.setValue(combo.getValue());
                AppStatusBar.setActionText(controller.getControlName() + " has been set to " + combo.getValue() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + controller.getControlName() + ": " + ex.getLocalizedMessage() + ".");
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
                AppStatusBar.setActionText(readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ControllerResetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
            }
            combo.setValue(readable.getValue());
        }          
    }
}
