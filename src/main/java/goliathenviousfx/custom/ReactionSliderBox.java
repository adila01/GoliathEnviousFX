package goliathenviousfx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.NvControllable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ReactionSliderBox extends BorderPane
{
    private final ToggleButton enable;
    private final ReadOnlyNvReadable<Integer> readable;
    private final NvControllable<Integer> controller;
    private final ToggleButton disable;
    private final Spinner<Integer> spinner;
    private final ChangeListener<Integer> binding;
    
    public ReactionSliderBox(String text, ReadOnlyNvReadable<Integer> rdbl, NvControllable<Integer> cont)
    {
        super();
        super.setStyle("-fx-background-color: -fx-theme-header;");
        super.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        
        readable = rdbl;
        readable.valueProperty().addListener(new ValueListener());

        controller = cont;
        binding = new ValueListener();
        
        enable = new ToggleButton("Enable");
        enable.setOnMouseClicked(new EnableHandler());
        enable.setPrefWidth(Integer.MAX_VALUE);
        
        disable = new ToggleButton("Disable");
        disable.setOnMouseClicked(new DisableHandler());
        disable.setPrefWidth(Integer.MAX_VALUE);

        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(enable);
        group.getToggles().add(disable);
        group.selectToggle(disable);
        disable.setStyle("-fx-background-color: -fx-theme-selected;");
        
        if(!cont.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            enable.setDisable(true);
            disable.setDisable(true);
        }

        ColumnConstraints conWidthButtons = new ColumnConstraints();
        conWidthButtons.setPercentWidth(10);
        
        GridPane gridBox = new GridPane();
        gridBox.setHgap(10*GoliathENVIOUSFX.SCALE);
        
        gridBox.getColumnConstraints().addAll(conWidthButtons, conWidthButtons);
        
        gridBox.add(enable, gridBox.getColumnCount()-2, 0);
        gridBox.add(disable, gridBox.getColumnCount()-1, 0);
        
        Label title = new Label(text);
        title.setAlignment(Pos.CENTER_LEFT);
        
        spinner = new Spinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100, 0));
        spinner.prefWidthProperty().bind(super.widthProperty().multiply(.1));
        
        super.setTop(title);
        super.setRight(spinner);
        super.setBottom(gridBox);
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
            readable.valueProperty().addListener(binding);
            enable.setStyle("-fx-background-color: -fx-theme-selected;");
            disable.setStyle("-fx-background-color: -fx-theme-background-alt;");
            AppStatusBar.setActionText("NvReaction for " + readable.displayNameProperty().get() + " has been enabled.");
        }
    }
    
    private class DisableHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            readable.valueProperty().removeListener(binding);
            enable.setStyle("-fx-background-color: -fx-theme-background-alt;");
            disable.setStyle("-fx-background-color: -fx-theme-selected;");
            AppStatusBar.setActionText("NvReaction for " + readable.displayNameProperty().get() + " has been disabled.");
        }
    }
}
