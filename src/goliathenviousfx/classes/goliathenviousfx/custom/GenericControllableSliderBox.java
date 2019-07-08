package goliathenviousfx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathEnviousFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GenericControllableSliderBox extends VBox
{
    private final LabeledSlider slider;
    private final Button apply;
    private final ReadOnlyNvReadable<Integer> readable;
    private Button reset;
    
    public GenericControllableSliderBox(ReadOnlyNvReadable<Integer> rdbl)
    {
        super();
        super.setStyle("-fx-background-color: -fx-theme-header;");
        super.setSpacing(8);
        super.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        
        readable = rdbl;
        readable.valueProperty().addListener(new ValueListener());
        
        slider = new LabeledSlider(readable, false);
        
        apply = new Button("Apply");
        apply.setPrefWidth(Integer.MAX_VALUE);
        apply.setOnMouseClicked(new ApplyHandler());
        
        reset = new Button("Reset");
        reset.setPrefWidth(Integer.MAX_VALUE);
        reset.setOnMouseClicked(new ResetHandler());

        if(!rdbl.getOperationalStatus().equals(OperationalStatus.READABLE_AND_CONTROLLABLE))
        {
            slider.getSlider().setDisable(true);
            slider.getTextBox().setDisable(true);
            apply.setDisable(true);
            reset.setDisable(true);
        }
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);
        buttonBox.setMinWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.setMaxWidth(200*GoliathEnviousFX.SCALE);
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        Label title = new Label(rdbl.displayNameProperty().get() + "(" + rdbl.getUnit() + ")");
        title.setAlignment(Pos.CENTER_LEFT);
        
        super.getChildren().add(title);
        super.getChildren().add(slider);
        super.getChildren().add(buttonBox);
    }

    public int getCurrentValue()
    {
        return (int)slider.getSlider().getValue();
    }
    
    private class ValueListener implements ChangeListener<Integer>
    {
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
        {
            if(slider.getSlider().isValueChanging())
                return;
            
            slider.getSlider().setValue(newValue);
            slider.getTextBox().setText(newValue.toString());
        }
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                readable.getController().get().setValue((int)slider.getSlider().getValue());
                AppStatusBar.setActionText(readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + ": " + ex.getLocalizedMessage() + ".");
            }
        }
    }
    
    private class ResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            try
            {
                readable.getController().get().reset();
                AppStatusBar.setActionText(readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ControllerResetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.nvTargetProperty().get() + " " + readable.displayNameProperty().get() + ": " + ex.getLocalizedMessage() + ".");
            }

            slider.getSlider().setValue(readable.getValue());
            slider.getTextBox().setText(String.valueOf(readable.getValue()));
        }
    }
}
