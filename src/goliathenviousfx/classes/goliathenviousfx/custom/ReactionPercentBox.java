package goliathenviousfx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.ReadOnlyNvControllable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.envious.utility.EnviousPlatform;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathEnviousFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ReactionPercentBox extends BorderPane
{
    private final ToggleButton enable;
    private final ReadOnlyNvReadable<Integer> readable;
    private final ReadOnlyNvControllable<Integer> controller;
    private final ToggleButton disable;
    private final Spinner<Integer> spinner;
    private final ChangeListener<Integer> binding;
    
    public ReactionPercentBox(String text, ReadOnlyNvReadable<Integer> rdbl, ReadOnlyNvControllable<Integer> cont)
    {
        super();
        super.setStyle("-fx-background-color: -fx-theme-header;");
        super.setPadding(new Insets(10*GoliathEnviousFX.SCALE,5*GoliathEnviousFX.SCALE,10*GoliathEnviousFX.SCALE,5*GoliathEnviousFX.SCALE));
        
        readable = rdbl;
        readable.valueProperty().addListener(new ValueListener());

        controller = cont;
        binding = new ValueListener();
        
        enable = new ToggleButton("Enabled");
        enable.setOnMouseClicked(new EnableHandler());
        enable.setPrefWidth(Integer.MAX_VALUE);
        
        disable = new ToggleButton("Disabled");
        disable.setOnMouseClicked(new DisableHandler());
        disable.setPrefWidth(Integer.MAX_VALUE);

        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(enable);
        group.getToggles().add(disable);
        group.selectToggle(disable);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setMinWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.setMaxWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.getChildren().add(enable);
        buttonBox.getChildren().add(disable);
        
        Label title = new Label(text);
        title.setAlignment(Pos.CENTER_LEFT);
        
        spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100, 0));
        spinner.setPrefWidth(100*GoliathEnviousFX.SCALE);
        
        if(!cont.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            spinner.setDisable(true);
            enable.setDisable(true);
            disable.setDisable(true);
        }
        
        EnviousPlatform.CURRENT.dirtyPlatformProperty().addListener(new DirtyPlatformListener());
        
        super.setTop(title);
        super.setRight(spinner);
        super.setBottom(buttonBox);
    }

    private class DirtyPlatformListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            readable.valueProperty().removeListener(binding);
        }
    }
    
    private class ValueListener implements ChangeListener<Integer>
    {
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
        {
            try
            {
                if(enable.isSelected())
                {
                    if(Integer.sum(newValue, spinner.getValue()) > controller.getMaxValue())
                        controller.setValue(controller.getMaxValue());
                    else if(Integer.sum(newValue, spinner.getValue()) < controller.getMinValue())
                        controller.setValue(controller.getMinValue());
                    else
                        controller.setValue(Integer.sum(newValue, spinner.getValue()));
                }
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set controller value. Reason: " + ex.getLocalizedMessage() + ". Value: " + Integer.sum(newValue, spinner.getValue()) + ".");
            }
        }
    }
    
    private class EnableHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {   
            if(EnviousPlatform.CURRENT.dirtyPlatformProperty().get() == true)
            {
                disable.setSelected(true);
                return;
            }
            
            readable.valueProperty().addListener(binding);
            AppStatusBar.setActionText("NvReaction for " + readable.getNvTarget() + " " + readable.displayNameProperty().get() + " has been enabled.");
        }
    }
    
    private class DisableHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            readable.valueProperty().removeListener(binding);
            AppStatusBar.setActionText("NvReaction for "+ readable.getNvTarget() + " " + readable.displayNameProperty().get() + " has been disabled.");
        }
    }
}
