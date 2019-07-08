package goliathenviousfx.menu.items;

import goliathenviousfx.GoliathEnviousFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;

public class ViewMenu extends Menu
{
    private final CheckMenuItem fullscreen;
    private final CheckMenuItem resize;
    private final CheckMenuItem opacity;
    
    public ViewMenu()
    {
        super("View");
        
        fullscreen = new CheckMenuItem("Fullscreen");
        fullscreen.setDisable(true);
        fullscreen.setOnAction(new FullscreenHandler());
        
        resize = new CheckMenuItem("Resizable");
        resize.setOnAction(new ResizeHandler());
        
        opacity = new CheckMenuItem("Transparent");
        opacity.setSelected(true);
        opacity.setOnAction(new OpacityHandler());
        
        super.getItems().add(fullscreen);
        super.getItems().add(resize);
        super.getItems().add(opacity);
        
        GoliathEnviousFX.STAGE.fullScreenProperty().addListener(new FullscreenListener());
    }
    
    private class FullscreenHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            if(!GoliathEnviousFX.STAGE.isFullScreen())
            {
                resize.setDisable(true);
                opacity.setDisable(true);
                GoliathEnviousFX.STAGE.setFullScreen(true);
            }
            else
            {
                resize.setDisable(false);
                opacity.setDisable(false);
                GoliathEnviousFX.STAGE.setFullScreen(false);
            }
        }
    }
    
    private class FullscreenListener implements ChangeListener<Boolean>
    {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            fullscreen.setSelected(newValue);
        }
    }
    
    private class ResizeHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            if(!GoliathEnviousFX.STAGE.isResizable())
            {
                fullscreen.setDisable(false);
                GoliathEnviousFX.STAGE.setResizable(true);
            }
            else
            {
                fullscreen.setDisable(true);
                GoliathEnviousFX.STAGE.setResizable(false);
            }
        }
     }
    
    private class OpacityHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            if(GoliathEnviousFX.STAGE.getOpacity() != 1)
                GoliathEnviousFX.STAGE.setOpacity(1);
            else
                GoliathEnviousFX.STAGE.setOpacity(.99);
        }
        
    }
}
