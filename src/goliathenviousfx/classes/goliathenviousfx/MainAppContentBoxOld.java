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

import goliathenviousfx.buttontabnav.NavButton;
import goliathenviousfx.buttontabnav.about.AboutContentPane;
import goliathenviousfx.buttontabnav.fan.FanContentPane;
import goliathenviousfx.buttontabnav.gpu.GPUContentPane;
import goliathenviousfx.buttontabnav.nvxconfig.NvXConfigPane;
import goliathenviousfx.buttontabnav.osd.OSDContentPane;
import goliathenviousfx.buttontabnav.overview.OverviewContentPane;
import goliathenviousfx.buttontabnav.reactions.ReactionContentPane;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MainAppContentBoxOld extends GridPane
{
    private final List<NavButton> navButtons;
    private final ScrollPane contentScroller;
    private NavButton selected;
    
    public MainAppContentBoxOld()
    {
        super();
        
        GridPane buttonBox = new GridPane();

        ColumnConstraints colwidthButtons = new ColumnConstraints();
        colwidthButtons.setPercentWidth(100);
        
        RowConstraints rowHeightButtons = new RowConstraints();
        rowHeightButtons.setPercentHeight(7);
        
        buttonBox.getColumnConstraints().add(colwidthButtons);
        buttonBox.setStyle("-fx-background-color: -fx-theme-background-alt;");
        
        contentScroller = new ScrollPane();
        contentScroller.setFitToHeight(true);
        contentScroller.setFitToWidth(true);
        contentScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //contentScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        contentScroller.setStyle("-fx-padding: 0; " +
        "-fx-background-insets: 0;");
        
        navButtons = new ArrayList<>();
        navButtons.add(new NavButton("Overview", new OverviewContentPane()));
        navButtons.add(new NavButton("NvGPU", new GPUContentPane()));
        navButtons.add(new NavButton("NvFan", new FanContentPane()));
        //navButtons.add(new NavButton("NvDisplay", new ContentPane()));
        navButtons.add(new NavButton("NvXConfig", new NvXConfigPane()));
        navButtons.add(new NavButton("NvReactions", new ReactionContentPane()));
        navButtons.add(new NavButton("OSD", new OSDContentPane()));
        navButtons.add(new NavButton("About", new AboutContentPane()));
       
        for(int i = 0; i < navButtons.size(); i++)
        {
            buttonBox.addRow(buttonBox.getRowCount(), navButtons.get(i));
            buttonBox.getRowConstraints().add(rowHeightButtons);
            navButtons.get(i).setPrefWidth(Integer.MAX_VALUE);
            navButtons.get(i).setPrefHeight(Integer.MAX_VALUE);
            navButtons.get(i).setOnMouseClicked(new ViewSwitchHandler());
            navButtons.get(i).setOnMouseEntered(new MouseEnterHandler());
            navButtons.get(i).setOnMouseExited(new MouseExitHandler());
        }
        
        contentScroller.setContent(navButtons.get(0).getContentPane());
        selected = navButtons.get(0);
        selected.setStyle("-fx-background-color: -fx-theme-selected;");
        
        ColumnConstraints conWidthButtons = new ColumnConstraints();
        conWidthButtons.setPercentWidth(15);
        
        ColumnConstraints conWidthContent = new ColumnConstraints();
        conWidthContent.setPercentWidth(85);
        
        RowConstraints conHeightButtons = new RowConstraints();
        conHeightButtons.setPercentHeight(100);
        
        RowConstraints conHeightContent = new RowConstraints();
        conHeightButtons.setPercentHeight(100);
        
        super.getColumnConstraints().add(conWidthButtons);
        super.getColumnConstraints().add(conWidthContent);
        super.getRowConstraints().add(conHeightButtons);
        super.getRowConstraints().add(conHeightContent);
        
        super.addColumn(0, buttonBox);
        super.addColumn(1, contentScroller);
    }
    
    private class ViewSwitchHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(((NavButton)event.getSource()).equals(contentScroller.getContent()))
            {
                event.consume();
                return;
            }
            
            selected.setStyle("-fx-background-color: -fx-theme-background-alt");
            selected = (NavButton)event.getSource();
            selected.setStyle("-fx-background-color: -fx-theme-selected");
            
            contentScroller.setContent(((NavButton)event.getSource()).getContentPane());
        }
    }
    
    private class MouseEnterHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(((NavButton)event.getSource()).equals(selected))
            {
                event.consume();
                return;
            }
            
            ((NavButton)event.getSource()).setStyle("-fx-background-color: -fx-theme-selectable-hover;");
        }
    }
    
    private class MouseExitHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            if(((NavButton)event.getSource()).equals(selected))
            {
                event.consume();
                return;
            }
            
            ((NavButton)event.getSource()).setStyle("-fx-background-color: -fx-theme-background-alt;");
        }
    }
}
