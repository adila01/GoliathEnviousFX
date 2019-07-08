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

import goliath.envious.enums.Unit;
import goliath.envious.interfaces.NvTarget;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathEnviousFX;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Tile extends VBox
{   
    private ReadOnlyNvReadable<?> readable;
    
    public Tile()
    {
        super();
        super.getStyleClass().add("tile");
        super.setPadding(new Insets(8*GoliathEnviousFX.SCALE,8*GoliathEnviousFX.SCALE,8*GoliathEnviousFX.SCALE,8*GoliathEnviousFX.SCALE));
        super.setSpacing(5*GoliathEnviousFX.SCALE);
        
    }
    
    public void setNvReadable(ReadOnlyNvReadable<?> rdbl)
    {
        readable = rdbl;
        this.setOnMouseClicked(new InfoEvent());
        
        String text = rdbl.displayNameProperty().get();
        
        if(text.contains(" Performance Limit"))
            text = text.substring(0, text.length()-18);
        
        if(text.contains(" Slowdown"))
            text = text.substring(0, text.length()-9);
        
        super.getChildren().add(new Label(text));
        
        Label lb = new Label(rdbl.displayValueProperty().get());
        rdbl.displayValueProperty().addListener(new ReadableBinder());
        
        super.setOnMouseEntered(new MouseEnterHandler());
        super.setOnMouseExited(new MouseExitHandler());
        super.getChildren().add(lb);
    }
    
    
    public void setObjectProperty(ReadOnlyProperty prop)
    {   
        Label lb = new Label();
        lb.setText(prop.getValue().toString());
        prop.addListener(new ObjectInfoListener(lb));

        super.getChildren().add(lb);
    }
    
    public void setMeasurementProperty(ReadOnlyProperty<Unit> prop)
    {   
        Label lb = new Label();
        lb.setText(prop.getValue().getFullString());

        super.getChildren().add(lb);
    }
    
    public void setStringProperty(ReadOnlyProperty<String> prop)
    {   
        Label lb = new Label();
        lb.setText(prop.getValue());
        prop.addListener(new StringInfoListener(lb));

        super.getChildren().add(lb);
    }
    
    public void setNvTargetProperty(ReadOnlyProperty<NvTarget> prop)
    {   
        Label lb = new Label();
        lb.setText(prop.getValue().getNvTarget());
        prop.addListener(new NvTargetInfoListener(lb));
        
        super.getChildren().add(lb);
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
            ((Label)Tile.this.getChildren().get(1)).setText(newValue);
        }
    }
    
    private class StringInfoListener implements ChangeListener<String>
    {
        private final Label label;
        
        public StringInfoListener(Label lb)
        {
            label = lb;
        }
        
        @Override
        public void changed(ObservableValue observable, String oldValue, String newValue)
        {
            label.setText(newValue);
        }
    }
    
    
    private class ObjectInfoListener implements ChangeListener<Object>
    {
        private final Label label;
        
        public ObjectInfoListener(Label lb)
        {
            label = lb;
        }
        
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue)
        {
            label.setText(newValue.toString());
        }
    }
    
    private class NvTargetInfoListener implements ChangeListener<NvTarget>
    {
        private final Label label;
        
        public NvTargetInfoListener(Label lb)
        {
            label = lb;
        }
        
        @Override
        public void changed(ObservableValue observable, NvTarget oldValue, NvTarget newValue)
        {
            label.setText(newValue.getNvTarget());
        }
        
    }
    
    private class InfoEvent implements EventHandler<MouseEvent>
    {
        private TileInfoStage infoStage;

        @Override
        public void handle(MouseEvent event)
        {
            if(infoStage == null)
                infoStage = new TileInfoStage(readable);
            
            infoStage.show();
        }
    }
    
    private class MouseEnterHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            ((Tile)event.getSource()).setStyle("-fx-background-color: -fx-theme-selectable-hover;");
        }
    }
    
    private class MouseExitHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            ((Tile)event.getSource()).setStyle("-fx-background-color: -fx-theme-background-alt;");
        }
    }
}