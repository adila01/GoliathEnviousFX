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
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.binding.DoubleBinding;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
        super.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        readable = rdbl;
        readable.valueProperty().addListener(new ReadableListener());
        
        text = new Label(rdbl.displayNameProperty().get());
        text.setAlignment(Pos.CENTER_LEFT);
        
        combo = new ComboBox<>(FXCollections.observableArrayList(rdbl.getController().get().getAllValues().getAllInRange()));
        combo.getSelectionModel().select(rdbl.getCurrentValue());
        
        apply = new Button("Apply");
        apply.setPrefWidth(Integer.MAX_VALUE);
        apply.setOnMouseClicked(new ApplyHandler());
        
        reset = new Button("Reset");
        reset.setPrefWidth(Integer.MAX_VALUE);
        reset.setOnMouseClicked(new ResetHandler());
        
        if(!rdbl.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            apply.setDisable(true);
            reset.setDisable(true);
        }
        
        ColumnConstraints conWidthButtons = new ColumnConstraints();
        conWidthButtons.setPercentWidth(10);
        
        GridPane gridBox = new GridPane();
        gridBox.setHgap(10*GoliathENVIOUSFX.SCALE);
        
        gridBox.getColumnConstraints().addAll(conWidthButtons, conWidthButtons);
        
        gridBox.add(apply, gridBox.getColumnCount()-2, 0);
        gridBox.add(reset, gridBox.getColumnCount()-1, 0);
        
        super.setTop(text);
        super.setBottom(gridBox);
        super.setRight(combo);
    }
    
    public GenericComboEnumPane(NvControllable<E> cont)
    {
        super();
        super.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        super.setStyle("-fx-background-color: -fx-theme-header;");
        
        controller = cont;
        
        text = new Label(cont.getControlName());
        text.setAlignment(Pos.CENTER_LEFT);
        
        combo = new ComboBox<>(FXCollections.observableArrayList(cont.getAllValues().getAllInRange()));
        combo.getSelectionModel().select(cont.getMinValue());
        
        apply = new Button("Apply");
        apply.setOnMouseClicked(new ControllerApplyHandler());
        apply.prefWidthProperty().bind(super.widthProperty().multiply(.1));
        
        if(!cont.getOperationalStatus().equals(OperationalStatus.CONTROLLABLE))
            apply.setDisable(true);
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10*GoliathENVIOUSFX.SCALE);
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
                AppStatusBar.setActionText(readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
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
                AppStatusBar.setActionText("Failed to set new value for " + controller.getControlName() + ". Reason: " + ex.getLocalizedMessage() + ".");
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
