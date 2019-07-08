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
import goliathenviousfx.GoliathEnviousFX;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class TileNvReadablePane extends TilePane
{
    public TileNvReadablePane(ReadOnlyNvReadable<?> rdbl)
    {
        super();
        
        super.setStyle("-fx-background-color: -fx-theme-header;");
        super.setAlignment(Pos.CENTER);
        super.setPadding(new Insets(16*GoliathEnviousFX.SCALE,16*GoliathEnviousFX.SCALE,16*GoliathEnviousFX.SCALE,16*GoliathEnviousFX.SCALE));
        super.setVgap(8*GoliathEnviousFX.SCALE);
        super.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile displayName = new Tile();
        displayName.addLabel("Display Name Property");
        displayName.setStringProperty(rdbl.displayNameProperty());
        
        Tile displayValue = new Tile();
        displayValue.addLabel("Display Value Property");
        displayValue.setStringProperty(rdbl.displayValueProperty());
        
        Tile cmdName = new Tile();
        cmdName.addLabel("CMD Name Property");
        cmdName.setStringProperty(rdbl.cmdNameProperty());
        
        Tile cmdValue = new Tile();
        cmdValue.addLabel("CMD Value Property");
        cmdValue.setStringProperty(rdbl.cmdValueProperty());
        
        Tile target = new Tile();
        target.addLabel("Target Property");
        target.setNvTargetProperty(rdbl.nvTargetProperty());
        
        Tile opStatus = new Tile();
        opStatus.addLabel("Operational Status Property");
        opStatus.setObjectProperty(rdbl.operationalStatusProperty());
        
        Tile update = new Tile();
        update.addLabel("Update Frequency Property");
        update.setObjectProperty(rdbl.updateFrequencyProperty());
        
        Tile measurement = new Tile();
        measurement.addLabel("Unit Property");
        measurement.setMeasurementProperty(rdbl.unitProperty());
        
        super.getChildren().add(displayName);
        super.getChildren().add(displayValue);
        super.getChildren().add(cmdName);
        super.getChildren().add(cmdValue);
        super.getChildren().add(target);
        super.getChildren().add(opStatus);
        super.getChildren().add(update);
        super.getChildren().add(measurement);
    }
}
