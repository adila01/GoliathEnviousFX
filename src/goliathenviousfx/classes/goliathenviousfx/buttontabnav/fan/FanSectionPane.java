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
package goliathenviousfx.buttontabnav.fan;

import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.GenericControllableSliderBox;
import goliathenviousfx.custom.GenericReadableTable;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import goliathenviousfx.custom.Tile;

public class FanSectionPane extends SectionContentPane
{
    //private final GenericReadableTable tablePane;
    private final GenericControllableSliderBox speedController;
    private final TilePane tilePane;
    
    public FanSectionPane(NvGPU g)
    {
        super(NvSettings.getNvGPUInstance(g).getNvFan().getNvTarget());
        
        List<ReadOnlyNvReadable> attrs = new ArrayList<>();
        attrs.add(NvSMI.getNvGPUInstance(g).getFanSpeed());
        attrs.add(NvSettings.getNvGPUInstance(g).getNvFan().getFanTargetSpeed());
        attrs.add(NvSettings.getNvGPUInstance(g).getNvFan().getFanRPMSpeed());
        
        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(15*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile fanSpeedCurrent = new Tile();
        fanSpeedCurrent.setNvReadable(attrs.get(0));
        
        Tile fanSpeedTarget = new Tile();
        fanSpeedTarget.setNvReadable(attrs.get(1));
        
        Tile fanSpeedRPM = new Tile();
        fanSpeedRPM.setNvReadable(attrs.get(2));
        
        tilePane.getChildren().add(fanSpeedCurrent);
        tilePane.getChildren().add(fanSpeedTarget);
        tilePane.getChildren().add(fanSpeedRPM);
        
        //tablePane = new GenericReadableTable(attrs);
        //tablePane.setMinHeight(130*GoliathEnviousFX.SCALE);
        //tablePane.setMaxHeight(130*GoliathEnviousFX.SCALE);
        
        speedController = new GenericControllableSliderBox(NvSettings.getNvGPUInstance(g).getNvFan().getFanTargetSpeed());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(tilePane);
        super.addTo(space);
        super.addTo(speedController);
    }
}
