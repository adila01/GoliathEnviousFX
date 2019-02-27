package goliathenviousfx.custom;

import goliath.envious.enums.OperationalStatus;
import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.exceptions.ValueSetFailedException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.AppStatusBar;
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

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
        super.setSpacing(10);
        super.setPadding(new Insets(10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE,10*GoliathENVIOUSFX.SCALE,5*GoliathENVIOUSFX.SCALE));
        
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
            apply.setDisable(true);
            reset.setDisable(true);
        }

        ColumnConstraints conWidthButtons = new ColumnConstraints();
        conWidthButtons.setPercentWidth(10);
        
        GridPane gridBox = new GridPane();
        gridBox.setHgap(10*GoliathENVIOUSFX.SCALE);
        
        gridBox.getColumnConstraints().addAll(conWidthButtons, conWidthButtons);
        
        gridBox.add(apply, gridBox.getColumnCount()-2, 0);
        gridBox.add(reset, gridBox.getColumnCount()-1, 0);
        
        Label title = new Label(rdbl.displayNameProperty().get() + "(" + rdbl.getMeasurement() + ")");
        title.setAlignment(Pos.CENTER_LEFT);
        
        super.getChildren().add(title);
        super.getChildren().add(slider);
        super.getChildren().add(gridBox);
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
                AppStatusBar.setActionText(readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ValueSetFailedException ex)
            {
                AppStatusBar.setActionText("Failed to set new value for " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
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
                AppStatusBar.setActionText(readable.displayNameProperty().get() + " has been set to " + readable.displayValueProperty().get() + ".");
            }
            catch (ControllerResetFailedException ex)
            {
                System.out.println("fail");
                AppStatusBar.setActionText("Failed to set new value for " + readable.displayNameProperty().get() + ". Reason: " + ex.getLocalizedMessage() + ".");
            }

            slider.getSlider().setValue(readable.getCurrentValue());
            slider.getTextBox().setText(String.valueOf(readable.getCurrentValue()));
        }
    }
}
