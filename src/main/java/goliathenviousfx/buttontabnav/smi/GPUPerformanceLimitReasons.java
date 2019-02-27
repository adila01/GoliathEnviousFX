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
package goliathenviousfx.buttontabnav.smi;

import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.enums.PerformanceLimitState;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Tile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class GPUPerformanceLimitReasons extends SectionContentPane
{
    private final TilePane tilePane;
    
    public GPUPerformanceLimitReasons(NvGPU g)
    {
        super(g.getTargetString() + " Performance Limiters");
        
        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(16*GoliathENVIOUSFX.SCALE,16*GoliathENVIOUSFX.SCALE,16*GoliathENVIOUSFX.SCALE,16*GoliathENVIOUSFX.SCALE));
        tilePane.setVgap(8*GoliathENVIOUSFX.SCALE);
        tilePane.setHgap(8*GoliathENVIOUSFX.SCALE);
        
        Tile idleLimit = new Tile();
        idleLimit.setNvReadable(NvSMI.getInstance(g).getIdlePerformanceLimit());
        NvSMI.getInstance(g).getIdlePerformanceLimit().valueProperty().addListener(new ValueListener(idleLimit));
        
        Tile appClockLimit = new Tile();
        appClockLimit.setNvReadable(NvSMI.getInstance(g).getAppClockSettingsPerformanceLimit());
        NvSMI.getInstance(g).getAppClockSettingsPerformanceLimit().valueProperty().addListener(new ValueListener(appClockLimit));
        
        Tile softwarePowerCapLimit = new Tile();
        softwarePowerCapLimit.setNvReadable(NvSMI.getInstance(g).getSoftwarePowerCapPerformanceLimit());
        NvSMI.getInstance(g).getSoftwarePowerCapPerformanceLimit().valueProperty().addListener(new ValueListener(softwarePowerCapLimit));
        
        Tile hardwareThermalLimit = new Tile();
        hardwareThermalLimit.setNvReadable(NvSMI.getInstance(g).getHardwareThermalSlowdownPerformanceLimit());
        NvSMI.getInstance(g).getHardwareThermalSlowdownPerformanceLimit().valueProperty().addListener(new ValueListener(hardwareThermalLimit));
        
        Tile hardwarePowerBrakeLimit = new Tile();
        hardwarePowerBrakeLimit.setNvReadable(NvSMI.getInstance(g).getHardwarePowerBrakeSlowdownPerformanceLimit());
        NvSMI.getInstance(g).getHardwarePowerBrakeSlowdownPerformanceLimit().valueProperty().addListener(new ValueListener(hardwarePowerBrakeLimit));
        
        Tile syncBoostLimit = new Tile();
        syncBoostLimit.setNvReadable(NvSMI.getInstance(g).getSyncBoostPerformanceLimit());
        NvSMI.getInstance(g).getSyncBoostPerformanceLimit().valueProperty().addListener(new ValueListener(syncBoostLimit));
        
        tilePane.getChildren().add(idleLimit);
        tilePane.getChildren().add(appClockLimit);
        tilePane.getChildren().add(softwarePowerCapLimit); 
        tilePane.getChildren().add(hardwareThermalLimit);
        tilePane.getChildren().add(hardwarePowerBrakeLimit);
        tilePane.getChildren().add(syncBoostLimit);
        
        super.add(tilePane, 0, super.getRowCount());
    }
    
    private class ValueListener implements ChangeListener<PerformanceLimitState>
    {
        private final Tile tile;
        
        public ValueListener(Tile tl)
        {
            tile = tl;
        }
        
        @Override
        public void changed(ObservableValue<? extends PerformanceLimitState> observable, PerformanceLimitState oldValue, PerformanceLimitState newValue)
        {
            if(newValue.equals(PerformanceLimitState.ACTIVE))
                tile.setStyle("-fx-background-color: -fx-theme-selected;");
            else
                tile.setStyle("-fx-background-color: -fx-theme-background-alt;");
        }
    }
}
