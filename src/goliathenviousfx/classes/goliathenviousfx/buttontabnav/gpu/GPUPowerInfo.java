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
package goliathenviousfx.buttontabnav.gpu;

import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class GPUPowerInfo extends SectionContentPane
{
    private final TilePane tilePane;
    private final GenericControllableSliderBox powerControl;
    
    public GPUPowerInfo(NvGPU g)
    {
        super(g.getNvTarget() + " Power");
        
        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(16*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);

        Tile defaultPower = new Tile();
        defaultPower.setNvReadable(NvSMI.getNvGPUInstance(g).getDefaultPowerLimit());
        
        Tile enforcedPower = new Tile();
        enforcedPower.setNvReadable(NvSMI.getNvGPUInstance(g).getEnforcedPowerLimit());
        
        Tile minPower = new Tile();
        minPower.setNvReadable(NvSMI.getNvGPUInstance(g).getMinPowerLimit());
        
        Tile maxPower = new Tile();
        maxPower.setNvReadable(NvSMI.getNvGPUInstance(g).getMaxPowerLimit());
        
        Tile powerLimit = new Tile();
        powerLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getPowerLimit());
        
        Tile powerDraw = new Tile();
        powerDraw.setNvReadable(NvSMI.getNvGPUInstance(g).getPowerDraw());
        
        tilePane.getChildren().add(defaultPower);
        tilePane.getChildren().add(enforcedPower);
        tilePane.getChildren().add(minPower);
        tilePane.getChildren().add(maxPower);
        tilePane.getChildren().add(powerLimit);
        tilePane.getChildren().add(powerDraw);
        
        powerControl = new GenericControllableSliderBox(NvSMI.getNvGPUInstance(g).getPowerLimit());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(tilePane);
        super.addTo(space);
        super.addTo(powerControl);
    }
}
