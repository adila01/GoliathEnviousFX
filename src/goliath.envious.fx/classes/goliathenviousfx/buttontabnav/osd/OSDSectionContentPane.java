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
package goliathenviousfx.buttontabnav.osd;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OSDSectionContentPane extends SectionContentPane
{
    private static ListView<ReadOnlyNvReadable> activeReadableList;
    
    private final ListView<ReadOnlyNvReadable> fullReadableList;
    private final Button fullToOnButton;
    private final Button onToOffButton;
    private final Spinner<Integer> xLoc;
    private final Spinner<Integer> yLoc;
    private OSDStage osd;
    
    public static List<ReadOnlyNvReadable> getOSDReadableList()
    {
        return activeReadableList.getItems();
    }
    
    public OSDSectionContentPane()
    {
        super("GPU-0 On Screen Display");
        
        List<ReadOnlyNvReadable> allReadables = new ArrayList<>();
        allReadables.add(NvSettings.getDriverVersion());
        allReadables.addAll(NvSettings.getPrimaryInstance().getNvReadables());
        allReadables.addAll(NvSettings.getPrimaryInstance().getNvFan().getNvReadables());
        allReadables.addAll(NvSMI.READABLES);
        
        List<ReadOnlyNvReadable> defaultReadables = new ArrayList<>();
        defaultReadables.add(NvSettings.getDriverVersion());
        defaultReadables.add(NvSettings.getPrimaryInstance().getCoreUsage());
        defaultReadables.add(NvSettings.getPrimaryInstance().getCoreClock());
        defaultReadables.add(NvSettings.getPrimaryInstance().getCoreTemp());
        defaultReadables.add(NvSettings.getPrimaryInstance().getMemoryUsage());
        defaultReadables.add(NvSettings.getPrimaryInstance().getMemoryClock());
        defaultReadables.add(NvSMI.getPowerDraw());
        defaultReadables.add(NvSMI.getPerformanceLimit());
        defaultReadables.add(NvSettings.getPrimaryInstance().getNvFan().getFanCurrentSpeed());
        
        DoubleBinding totalWidth = super.widthProperty().multiply(.85);
        DoubleBinding listWidth = super.widthProperty().multiply(.30).subtract(8*GoliathENVIOUSFX.SCALE);
        DoubleBinding buttonWidth = super.widthProperty().multiply(.25);
        
        fullReadableList = new ListView<>(FXCollections.observableList(allReadables));
        fullReadableList.getSelectionModel().selectFirst();
        fullReadableList.minWidthProperty().bind(listWidth);
        fullReadableList.maxWidthProperty().bind(listWidth);
        
        activeReadableList = new ListView<>(FXCollections.observableList(defaultReadables));
        activeReadableList.getSelectionModel().selectFirst();
        activeReadableList.minWidthProperty().bind(listWidth);
        activeReadableList.maxWidthProperty().bind(listWidth);
        
        fullToOnButton = new Button("-->");
        fullToOnButton.setOnMouseClicked(new OnMouseEvent());
        
        onToOffButton = new Button("<--");
        onToOffButton.setOnMouseClicked(new OffMouseEvent());
        
        VBox buttonBox = new VBox();
        buttonBox.setStyle("-fx-background-color: -fx-theme-header;");
        buttonBox.setSpacing(8*GoliathENVIOUSFX.SCALE);
        buttonBox.minWidthProperty().bind(buttonWidth);
        buttonBox.maxWidthProperty().bind(buttonWidth);
        buttonBox.setPadding(new Insets(8*GoliathENVIOUSFX.SCALE));
        buttonBox.setAlignment(Pos.CENTER);
        
        DoubleBinding buttonWidthBinding = buttonBox.widthProperty().multiply(.50);
   
        fullToOnButton.minWidthProperty().bind(buttonWidthBinding);
        fullToOnButton.maxWidthProperty().bind(buttonWidthBinding);
        
        onToOffButton.minWidthProperty().bind(buttonWidthBinding);
        onToOffButton.maxWidthProperty().bind(buttonWidthBinding);
        
        buttonBox.getChildren().add(fullToOnButton);
        buttonBox.getChildren().add(onToOffButton);
        
        HBox readableSelectionBox = new HBox();
        readableSelectionBox.setPadding(new Insets(8*GoliathENVIOUSFX.SCALE));
        readableSelectionBox.setStyle("-fx-background-color: -fx-theme-header;");
        readableSelectionBox.minWidthProperty().bind(totalWidth);
        readableSelectionBox.maxWidthProperty().bind(totalWidth);
        readableSelectionBox.setAlignment(Pos.CENTER);
        readableSelectionBox.getChildren().add(fullReadableList);
        readableSelectionBox.getChildren().add(buttonBox);
        readableSelectionBox.getChildren().add(activeReadableList);
        
        DoubleBinding spaceHeightBind = super.heightProperty().multiply(.001);
        DoubleBinding spaceWidthBind = super.widthProperty().multiply(.85);
        
        Space firstSpace = new Space(false);
        firstSpace.minHeightProperty().bind(spaceHeightBind);
        firstSpace.maxHeightProperty().bind(spaceHeightBind);
        firstSpace.minWidthProperty().bind(spaceWidthBind);
        firstSpace.maxWidthProperty().bind(spaceWidthBind);
        
        xLoc = new Spinner();
        xLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1920));
        
        yLoc = new Spinner();
        yLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1080));
        
        super.getChildren().add(readableSelectionBox);
        super.getChildren().add(firstSpace);
    }
    
            
    private class LaunchButtonHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if (osd == null)
                osd = new OSDStage();

            osd.setX(xLoc.getValue());
            osd.setY(yLoc.getValue());
            osd.show();
        }
    }
    
    
    private class OnMouseEvent implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent event)
        {
            if(activeReadableList.getItems().size() == 15)
                return;

            if(activeReadableList.getItems().contains(fullReadableList.getSelectionModel().getSelectedItem()))
                return;
                
            if(activeReadableList.getItems().isEmpty())
            {
                activeReadableList.getItems().add(fullReadableList.getSelectionModel().getSelectedItem());
                activeReadableList.getSelectionModel().selectFirst();
            }
            else
            {
                activeReadableList.getItems().add(activeReadableList.getSelectionModel().getSelectedIndex()+1, fullReadableList.getSelectionModel().getSelectedItem());
                activeReadableList.getSelectionModel().select(fullReadableList.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    private class OffMouseEvent implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(activeReadableList.getSelectionModel().isEmpty())
                return;
            
            int index = activeReadableList.getSelectionModel().getSelectedIndex();
            activeReadableList.getItems().remove(index);
            
            if(activeReadableList.getSelectionModel().getSelectedIndex() < index)
                activeReadableList.getSelectionModel().select(index);
            else
                activeReadableList.getSelectionModel().select(index);
        }
    }
}
