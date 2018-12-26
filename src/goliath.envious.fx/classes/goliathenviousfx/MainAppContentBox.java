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

import goliathenviousfx.buttontabnav.ContentPane;
import goliathenviousfx.buttontabnav.NavButton;
import goliathenviousfx.buttontabnav.osd.AboutContentPane;
import goliathenviousfx.buttontabnav.fan.FanContentPane;
import goliathenviousfx.buttontabnav.gpu.GPUContentPane;
import goliathenviousfx.buttontabnav.nvxconfig.NvXConfigPane;
import goliathenviousfx.buttontabnav.overview.OverviewContentPane;
import goliathenviousfx.buttontabnav.reactions.ReactionContentPane;
import goliathenviousfx.buttontabnav.smi.SMIContentPane;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainAppContentBox extends HBox
{
    private final List<NavButton> navButtons;
    private final ScrollPane contentScroller;
    private NavButton selected;
    
    public MainAppContentBox()
    {
        super();
        super.prefHeightProperty().bind(AppRoot.getInstance().heightProperty());
        
        DoubleBinding buttonBind = super.widthProperty().multiply(.15);
        
        VBox buttonBox = new VBox();
        buttonBox.minWidthProperty().bind(buttonBind);
        buttonBox.maxWidthProperty().bind(buttonBind);
        buttonBox.setStyle("-fx-background-color: -fx-theme-background-alt;");
        
        contentScroller = new ScrollPane();
        contentScroller.prefWidthProperty().bind(super.widthProperty().multiply(.85));
        contentScroller.maxWidthProperty().bind(super.widthProperty().multiply(.85));
        contentScroller.setFitToHeight(true);
        contentScroller.setFitToWidth(true);
        contentScroller.setStyle("-fx-padding: 0; " +
        "-fx-background-insets: 0;");
        contentScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        contentScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        navButtons = new ArrayList<>();
        navButtons.add(new NavButton("Overview", new OverviewContentPane()));
        navButtons.add(new NavButton("GPU", new GPUContentPane()));
        navButtons.add(new NavButton("Fan", new FanContentPane()));
        navButtons.add(new NavButton("NvSMI", new SMIContentPane()));
        navButtons.add(new NavButton("NvXConfig", new NvXConfigPane()));
        navButtons.add(new NavButton("NvReactions", new ReactionContentPane()));
        navButtons.add(new NavButton("On Screen Display", new ContentPane()));
        //navButtons.add(new NavButton("NvReactions", new ContentPane()));
        navButtons.add(new NavButton("About", new AboutContentPane()));
        
        
        buttonBox.getChildren().addAll(navButtons);
        
        for(int i = 0; i < navButtons.size(); i++)
        {
            navButtons.get(i).minHeightProperty().bind(super.heightProperty().multiply(.06));
            navButtons.get(i).maxHeightProperty().bind(super.heightProperty().multiply(.06));
            navButtons.get(i).minWidthProperty().bind(buttonBind);
            navButtons.get(i).maxWidthProperty().bind(buttonBind);
            navButtons.get(i).setOnMouseClicked(new ViewSwitchHandler());
            navButtons.get(i).setOnMouseEntered(new MouseEnterHandler());
            navButtons.get(i).setOnMouseExited(new MouseExitHandler());
        }
        
        contentScroller.setContent(navButtons.get(0).getContentPane());
        selected = navButtons.get(0);
        selected.setStyle("-fx-background-color: -fx-theme-selected;");
        
        super.getChildren().add(buttonBox);
        super.getChildren().add(contentScroller);
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
