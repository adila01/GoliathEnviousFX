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
package goliathenviousfx;

import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliathenviousfx.buttontabnav.ContentPane;
import goliathenviousfx.buttontabnav.about.AboutContentPane;
import goliathenviousfx.buttontabnav.fan.FanContentPane;
import goliathenviousfx.buttontabnav.gpu.GPUContentPane;
import goliathenviousfx.buttontabnav.monitoring.MonitoringContentPane;
import goliathenviousfx.buttontabnav.nvxconfig.NvXConfigPane;
import goliathenviousfx.buttontabnav.options.OptionsContentPane;
import goliathenviousfx.buttontabnav.osd.OSDContentPane;
import goliathenviousfx.buttontabnav.overview.OverviewContentPane;
import goliathenviousfx.buttontabnav.reactions.ReactionContentPane;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class MainAppContentBox extends SplitPane
{
    private final ScrollPane contentScroller;
    private final TreeView<String> view;
    private final Map<TreeItem<String>, Double> positionMap;
    
    private TreeItem<String> currentItem; 
    
    public MainAppContentBox()
    {
        super();
        
        positionMap = new HashMap<>();
        
        contentScroller = new ScrollPane();
        contentScroller.vvalueProperty().addListener(new ViewPositionListener());
        contentScroller.setFitToHeight(true);
        contentScroller.setFitToWidth(true);
        contentScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        view = new TreeView<>();
        view.setMinWidth(0);
        view.setShowRoot(false);
        view.setRoot(new TreeItem<>());
        
        for(int i = 0; i < Screen.getScreens().size(); i++)
        {
            if(Screen.getScreens().get(i).getBounds().getHeight() > view.getPrefHeight())
                view.setPrefHeight(Screen.getScreens().get(i).getBounds().getHeight());
        }
        
        ContentItem overview = new ContentItem(new ContentPane(), "Overview");
        overview.setExpanded(true);
        view.getRoot().getChildren().add(overview);
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
            overview.getChildren().add(new ContentItem(new OverviewContentPane(NvGPU.getNvGPUList().get(i)), NvGPU.getNvGPUList().get(i).getNvTarget() + " - " + NvGPU.getNvGPUList().get(i).nameProperty().get()));
        
        ContentItem gpus = new ContentItem(new ContentPane(), "NvGPU");
        gpus.setExpanded(true);
        view.getRoot().getChildren().add(gpus);
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
            gpus.getChildren().add(new ContentItem(new GPUContentPane(NvGPU.getNvGPUList().get(i)), NvGPU.getNvGPUList().get(i).getNvTarget() + " - " + NvGPU.getNvGPUList().get(i).nameProperty().get()));
        
        ContentItem fans = new ContentItem(new ContentPane(), "NvFan");
        fans.setExpanded(true);
        view.getRoot().getChildren().add(fans);
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
            fans.getChildren().add(new ContentItem(new FanContentPane(NvGPU.getNvGPUList().get(i)), NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvFan().getNvTarget() + " - " + NvGPU.getNvGPUList().get(i).nameProperty().get()));
        
        ContentItem monitoring = new ContentItem(new ContentPane(), "NvMonitor");
        monitoring.setExpanded(true);
        view.getRoot().getChildren().add(monitoring);
        
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            monitoring.getChildren().add(new ContentItem(new MonitoringContentPane(NvGPU.getNvGPUList().get(i)), NvGPU.getNvGPUList().get(i).getNvTarget() + " - " + NvGPU.getNvGPUList().get(i).nameProperty().get()));
            monitoring.getChildren().add(new ContentItem(new MonitoringContentPane(NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvFan()), NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getNvFan().getNvTarget() + " - " + NvGPU.getNvGPUList().get(i).nameProperty().get()));
        }
        
        ContentItem xconfig = new ContentItem(new NvXConfigPane(), "NvXConfig");
        view.getRoot().getChildren().add(xconfig);
        
        ContentItem reactions = new ContentItem(new ReactionContentPane(), "NvReactions");
        view.getRoot().getChildren().add(reactions);
        
        ContentItem osd = new ContentItem(new OSDContentPane(), "NvOSD");
        view.getRoot().getChildren().add(osd);
        
        ContentItem options = new ContentItem(new OptionsContentPane(), "Options");
        view.getRoot().getChildren().add(options);
        
        ContentItem about = new ContentItem(new AboutContentPane(), "About");
        view.getRoot().getChildren().add(about);

        GoliathEnviousFX.STAGE.showingProperty().addListener(new ShowingListener());
        
        contentScroller.setContent(overview.getContent());
        currentItem = overview;
        
        positionMap.put(overview, 0.0);

        super.getItems().add(view);
        super.getItems().add(contentScroller);
        
        view.getSelectionModel().select(((ContentItem)overview.getChildren().get(0)));
        view.getSelectionModel().selectedItemProperty().addListener(new ContentSwitchHandler());
        
        super.getDividers().get(0).positionProperty().set(.15);
        super.getDividers().get(0).positionProperty().addListener(new DividerListener());
    }           
    
    private class ViewPositionListener implements ChangeListener<Number>
    {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
        {
            positionMap.put(currentItem, newValue.doubleValue());
        }
    }
    
    private class ShowingListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
        {
            contentScroller.setContent(((ContentItem)view.getRoot().getChildren().get(0).getChildren().get(0)).getContent());
        }
    }
    
    private class DividerListener implements ChangeListener<Number>
    {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
        {
            if(newValue.doubleValue() >= .15)
                MainAppContentBox.this.setDividerPositions(.15);
        }
        
    }
    
    private class ContentSwitchHandler implements ChangeListener<TreeItem<String>>
    {
        @Override
        public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue)
        {
            if(newValue.getValue().equals("Overview") || newValue.getValue().equals("NvGPU") || newValue.getValue().equals("NvFan") || newValue.getValue().equals("NvMonitor"))
                return;
            
            if(!positionMap.containsKey(newValue))
                positionMap.put(newValue, 0.0);
            
            contentScroller.setContent(((ContentItem)newValue).getContent());
            contentScroller.setContent(((ContentItem)oldValue).getContent());
            contentScroller.setContent(((ContentItem)newValue).getContent());
            
            currentItem = newValue;
            contentScroller.setVvalue(positionMap.get(newValue));
            
        }
    }
    
    private class ContentItem extends TreeItem<String>
    {
        private final Pane content;
        private final String label;
        
        public ContentItem(Pane cnt, String lab)
        {
            super(lab);
            
            content = cnt;
            label = lab;
        }
        
        public String getLabel()
        {
            return label;
        }
        
        public Pane getContent()
        {
            return content;
        }
    }
}
