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

import goliath.envious.enums.OperationalStatus;
import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.settings.AppSettings;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.Space;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

public class OSDSectionContentPane extends SectionContentPane
{
    private static ListView<ReadOnlyNvReadable> activeReadableList;
    
    private final ListView<ReadOnlyNvReadable> fullReadableList;
    private final Button fullToOnUnderButton;
    private final Button fullToOnAboveButton;
    private final Button onToOffButton;
    
    private final Spinner<Integer> xLoc;
    private final Spinner<Integer> yLoc;
    
    private final ToggleGroup group;
    private final ToggleButton enable;
    private final ToggleButton disable;
    
    private OSDStage osd;
    
    private int x = 0;
    private int y = 0;
    
    public static ObservableList<ReadOnlyNvReadable> rdbls;

    public OSDSectionContentPane()
    {
        super("GPU-0 On Screen Display");
        
        List<ReadOnlyNvReadable> allReadables = new ArrayList<>();
        allReadables.addAll(NvGPU.getPrimaryNvGPU().getNvReadables());
        allReadables.add(NvSettings.getPrimaryNvGPUInstance().getUuid());
        allReadables.addAll(NvSMI.getPrimaryNvGPUInstance().getNvReadablesExceptOperationalStatus(OperationalStatus.NOT_SUPPORTED));  
        
        if(!AppSettings.getNvSMIOnly().getValue())
        {
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCUDACores());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCurrentPerformanceLevel());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoEngineUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoEncoderUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVideoDecoderUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getPCIeUtilization());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getPCIeCurrentSpeed());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getCoreOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getMemoryOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVoltageOffset());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getVoltageCurrent());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getPowerMizerMode());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getNvGPULogoBrightness());
            allReadables.add(NvSettings.getPrimaryNvGPUInstance().getFanMode());  
            allReadables.addAll(NvSettings.getPrimaryNvGPUInstance().getNvFan().getNvReadables());
        }
        
        List<ReadOnlyNvReadable> defaultReadables = new ArrayList<>();
        defaultReadables.add(NvGPU.getPrimaryNvGPU().getNameNvReadable());
        defaultReadables.add(NvSettings.getDriverVersion());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getCoreUtilization());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getCoreClock());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getCoreTemp());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getMemoryUtilization());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getMemoryUsed());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getMemoryClock());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getPowerDraw());
        defaultReadables.add(NvSMI.getPrimaryNvGPUInstance().getFanSpeed());
       
        
        fullReadableList = new ListView<>(FXCollections.observableList(allReadables));
        fullReadableList.setMinHeight(300*GoliathEnviousFX.SCALE);
        fullReadableList.setMaxHeight(300*GoliathEnviousFX.SCALE);
        fullReadableList.getSelectionModel().selectFirst();
        fullReadableList.setPrefWidth(Integer.MAX_VALUE);
        
        activeReadableList = new ListView<>(FXCollections.observableList(defaultReadables));
        activeReadableList.setMinHeight(300*GoliathEnviousFX.SCALE);
        activeReadableList.setMaxHeight(300*GoliathEnviousFX.SCALE);
        activeReadableList.getSelectionModel().selectFirst();
        activeReadableList.setPrefWidth(Integer.MAX_VALUE);
        
        rdbls = activeReadableList.getItems();
        
        fullToOnAboveButton = new Button("++>");
        fullToOnAboveButton.setPrefWidth(100*GoliathEnviousFX.SCALE);
        fullToOnAboveButton.setAlignment(Pos.CENTER);
        fullToOnAboveButton.setOnMouseClicked(new OnAboveMouseEvent());
        
        fullToOnUnderButton = new Button("-->");
        fullToOnUnderButton.setPrefWidth(100*GoliathEnviousFX.SCALE);
        fullToOnUnderButton.setAlignment(Pos.CENTER);
        fullToOnUnderButton.setOnMouseClicked(new OnUnderMouseEvent());
        
        onToOffButton = new Button("<--");
        onToOffButton.setPrefWidth(100*GoliathEnviousFX.SCALE);
        onToOffButton.setAlignment(Pos.CENTER);
        onToOffButton.setOnMouseClicked(new OffMouseEvent());
        
        VBox buttonBox = new VBox();
        buttonBox.setStyle("-fx-background-color: -fx-theme-header;");
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setAlignment(Pos.CENTER);
        
        buttonBox.getChildren().add(fullToOnAboveButton);
        buttonBox.getChildren().add(fullToOnUnderButton);
        buttonBox.getChildren().add(onToOffButton);
        
        GridPane readableSelectionBox = new GridPane();
        readableSelectionBox.setAlignment(Pos.CENTER);
        readableSelectionBox.setStyle("-fx-background-color: -fx-theme-header;");
        readableSelectionBox.addColumn(0, fullReadableList);
        readableSelectionBox.addColumn(1, buttonBox);
        readableSelectionBox.addColumn(2, activeReadableList);
        
        ColumnConstraints listWidthConstraint = new ColumnConstraints();
        listWidthConstraint.setPercentWidth(35);
        
        ColumnConstraints buttonsWidthConstraint = new ColumnConstraints();
        buttonsWidthConstraint.setPercentWidth(30);
        
        readableSelectionBox.getColumnConstraints().add(listWidthConstraint);
        readableSelectionBox.getColumnConstraints().add(buttonsWidthConstraint);
        readableSelectionBox.getColumnConstraints().add(listWidthConstraint);
        
        List<Space> spaces = new ArrayList<>();
        
        for(int i = 0; i < 3; i++)
        {
            spaces.add(new Space(true));
            spaces.get(i).setMinHeight(1*GoliathEnviousFX.SCALE);
            spaces.get(i).setMaxHeight(1*GoliathEnviousFX.SCALE);
        }
        
        xLoc = new Spinner<>();
        xLoc.setPrefWidth(100*GoliathEnviousFX.SCALE);
        xLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (int)Screen.getPrimary().getBounds().getMaxX()));
        
        yLoc = new Spinner<>();
        yLoc.setPrefWidth(100*GoliathEnviousFX.SCALE);
        yLoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (int)Screen.getPrimary().getBounds().getMaxY()));
        
        BorderPane xPane = new BorderPane();
        xPane.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        xPane.setTop(new Label("X Location"));
        xPane.setRight(xLoc);
        
        Button xApply = new Button("Apply");
        xApply.setOnMouseClicked(new XApplyHandler());
        xApply.setMinWidth(100*GoliathEnviousFX.SCALE);
        xApply.setMaxWidth(100*GoliathEnviousFX.SCALE);
        xPane.setBottom(xApply);
        
        BorderPane yPane = new BorderPane();
        yPane.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        yPane.setTop(new Label("Y Location"));
        yPane.setRight(yLoc);
        
        Button yApply = new Button("Apply");
        yApply.setOnMouseClicked(new YApplyHandler());
        yApply.setMinWidth(100*GoliathEnviousFX.SCALE);
        yApply.setMaxWidth(100*GoliathEnviousFX.SCALE);
        yPane.setBottom(yApply);

        enable = new ToggleButton("Enabled");
        enable.setOnMouseClicked(new EnableHandler());
        enable.setPrefWidth(Integer.MAX_VALUE);
        
        if(System.getProperty("jdk.gtk.version").equals("3"))
            enable.setDisable(true);
        
        disable = new ToggleButton("Disabled");
        disable.setOnMouseClicked(new DisableHandler());
        disable.setPrefWidth(Integer.MAX_VALUE);
        
        group = new ToggleGroup();
        group.getToggles().add(enable);
        group.getToggles().add(disable);
        group.selectToggle(disable);

        HBox toggleBox = new HBox();
        toggleBox.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        toggleBox.setSpacing(10*GoliathEnviousFX.SCALE);
        toggleBox.setMinWidth(220*GoliathEnviousFX.SCALE);
        toggleBox.setMaxWidth(220*GoliathEnviousFX.SCALE);
        toggleBox.getChildren().add(enable);
        toggleBox.getChildren().add(disable);
        
        super.addTo(readableSelectionBox);
        super.addTo(spaces.get(0));
        super.addTo(xPane);
        super.addTo(spaces.get(1));
        super.addTo(yPane);
        super.addTo(spaces.get(2));
        super.addTo(toggleBox);
    }
    
    private class EnableHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(osd == null)
            {
                osd = new OSDStage();
                osd.setOnCloseRequest(new CloseHandler());
            }
            
            osd.show();
            
            osd.setX(x);
            osd.setY(y);
        }
        
        private class CloseHandler implements EventHandler<WindowEvent>
        {

            @Override
            public void handle(WindowEvent event)
            {
                osd.close();
                osd = null;
                group.selectToggle(disable);
            }
        }
    }
    
    private class DisableHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            osd.close();
            osd = null;
        }
    }
    
    private class XApplyHandler implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent event)
        {
            if(osd == null)
                x = xLoc.getValue();
            else
                osd.setX(xLoc.getValue());
        }
    }
    
    private class YApplyHandler implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent event)
        {
            if(osd == null)
                y = yLoc.getValue();
            else
                osd.setY(yLoc.getValue());
        }
    }
    
    private class OnAboveMouseEvent implements EventHandler<MouseEvent>
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
                activeReadableList.getItems().add(activeReadableList.getSelectionModel().getSelectedIndex(), fullReadableList.getSelectionModel().getSelectedItem());
                activeReadableList.getSelectionModel().select(fullReadableList.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    private class OnUnderMouseEvent implements EventHandler<MouseEvent>
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
