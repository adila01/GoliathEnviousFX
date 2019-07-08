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
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class GPUUtilizationSectionPane extends SectionContentPane
{
    private final NvGPU gpu;
    
    public GPUUtilizationSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " Utilization");
        
        gpu = g;
        
        TilePane tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(15*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        Tile coreUtil = new Tile();
        coreUtil.setNvReadable(NvSMI.getNvGPUInstance(g).getCoreUtilization());
        
        Tile memoryBandwidthUtilization = new Tile();
        memoryBandwidthUtilization.setNvReadable(NvSMI.getNvGPUInstance(g).getMemoryUtilization());
        
        Tile pcieUtilizationBandwidth = new Tile();
        pcieUtilizationBandwidth.setNvReadable(NvSettings.getNvGPUInstance(g).getPCIeUtilization());
        
        Tile videoEngineUtilization = new Tile();
        videoEngineUtilization.setNvReadable(NvSettings.getNvGPUInstance(g).getVideoEngineUtilization());
        
        Tile videoEncoderUtilization = new Tile();
        videoEncoderUtilization.setNvReadable(NvSettings.getNvGPUInstance(g).getVideoEncoderUtilization());
        
        Tile videoDecoderUtilization = new Tile();
        videoDecoderUtilization.setNvReadable(NvSettings.getNvGPUInstance(g).getVideoDecoderUtilization());
        
        tilePane.getChildren().add(coreUtil);
        tilePane.getChildren().add(memoryBandwidthUtilization); 
        tilePane.getChildren().add(pcieUtilizationBandwidth);
        tilePane.getChildren().add(videoEngineUtilization);
        tilePane.getChildren().add(videoEncoderUtilization);
        tilePane.getChildren().add(videoDecoderUtilization);
        
        super.addTo(tilePane);
    }
}
