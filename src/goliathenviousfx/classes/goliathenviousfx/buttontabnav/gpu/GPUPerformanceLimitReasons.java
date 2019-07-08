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
import goliath.nvsmi.enums.PerformanceLimitState;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
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
        super(g.getNvTarget() + " Performance Limiters");
        
        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(16*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile idleLimit = new Tile();
        idleLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getIdlePerformanceLimit());
        NvSMI.getNvGPUInstance(g).getIdlePerformanceLimit().valueProperty().addListener(new ValueListener(idleLimit));
        
        Tile appClockLimit = new Tile();
        appClockLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getAppClockSettingsPerformanceLimit());
        NvSMI.getNvGPUInstance(g).getAppClockSettingsPerformanceLimit().valueProperty().addListener(new ValueListener(appClockLimit));
        
        Tile softwarePowerCapLimit = new Tile();
        softwarePowerCapLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getSoftwarePowerCapPerformanceLimit());
        NvSMI.getNvGPUInstance(g).getSoftwarePowerCapPerformanceLimit().valueProperty().addListener(new ValueListener(softwarePowerCapLimit));
        
        Tile hardwareThermalLimit = new Tile();
        hardwareThermalLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getHardwareThermalSlowdownPerformanceLimit());
        NvSMI.getNvGPUInstance(g).getHardwareThermalSlowdownPerformanceLimit().valueProperty().addListener(new ValueListener(hardwareThermalLimit));
        
        Tile hardwarePowerBrakeLimit = new Tile();
        hardwarePowerBrakeLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getHardwarePowerBrakeSlowdownPerformanceLimit());
        NvSMI.getNvGPUInstance(g).getHardwarePowerBrakeSlowdownPerformanceLimit().valueProperty().addListener(new ValueListener(hardwarePowerBrakeLimit));
        
        Tile syncBoostLimit = new Tile();
        syncBoostLimit.setNvReadable(NvSMI.getNvGPUInstance(g).getSyncBoostPerformanceLimit());
        NvSMI.getNvGPUInstance(g).getSyncBoostPerformanceLimit().valueProperty().addListener(new ValueListener(syncBoostLimit));
        
        tilePane.getChildren().add(idleLimit);
        tilePane.getChildren().add(appClockLimit);
        tilePane.getChildren().add(softwarePowerCapLimit); 
        tilePane.getChildren().add(hardwareThermalLimit);
        tilePane.getChildren().add(hardwarePowerBrakeLimit);
        tilePane.getChildren().add(syncBoostLimit);
        
        super.addTo(tilePane);
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
            /*
            if(newValue.equals(PerformanceLimitState.ACTIVE))
                tile.setStyle("-fx-background-color: -fx-theme-selected;");
            else
                tile.setStyle("-fx-background-color: -fx-theme-background-alt;");
            */
        }
    }
}
