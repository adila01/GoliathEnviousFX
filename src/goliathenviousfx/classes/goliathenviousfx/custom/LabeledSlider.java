package goliathenviousfx.custom;

import goliath.envious.enums.Unit;
import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.ReadOnlyNvControllable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.settings.AppSettings;
import goliathenviousfx.GoliathEnviousFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;

public class LabeledSlider extends HBox
{
    private final Slider slider;
    private final TextField textBox;
    private final ReadOnlyNvReadable<Integer> attr;
    private final boolean setValue;
    
    public LabeledSlider(ReadOnlyNvReadable<Integer> attribute, boolean setVal)
    {
        super();
        super.setSpacing(10*GoliathEnviousFX.SCALE);
        
        attr = attribute;
        setValue = setVal;

        slider = new Slider();
        slider.setPrefWidth(Integer.MAX_VALUE);
        slider.getStyleClass().add("slider");
        
        if(!attribute.getController().isEmpty())
        {
            slider.setMin(attr.getController().get().getMinValue());
            slider.setMax(attr.getController().get().getMaxValue());
            slider.setValue(attr.getValue());
        }
        else
        {
            slider.setMin(attr.getValue());
            slider.setMax(attr.getValue());
            slider.setValue(attr.getValue());
        }
        
        if(!AppSettings.getAllowUnderclocking().getValue() && !attr.unitProperty().get().equals(Unit.WATTS))
            slider.setMin(0);
        
        slider.valueProperty().addListener(new DefaultContSliderHandler());
        
        textBox = new TextField();
        textBox.setMinWidth(75*GoliathEnviousFX.SCALE);
        textBox.setMaxWidth(75*GoliathEnviousFX.SCALE);
        textBox.focusedProperty().addListener(new ValueValidatorListener());
        textBox.setPrefWidth(Integer.MAX_VALUE);
        textBox.setEditable(true);
        textBox.setText(String.valueOf(attr.getValue()));
        
        super.getChildren().add(slider);
        super.getChildren().add(textBox);
    }
    
    public Slider getSlider()
    {
        return slider;
    }
    
    public TextField getTextBox()
    {
        return textBox;
    }
    
    public ReadOnlyNvControllable<Integer> getController()
    {
        return attr.getController().get();
    }
    
    private class DefaultContSliderHandler implements ChangeListener<Number>
    {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
        {
            textBox.setText(String.valueOf(newValue.intValue()));
            
            if(!setValue)
                return;
            
            try
            {
                attr.getController().get().setValue(newValue.intValue());
            }
            catch (ValueSetFailedException ex)
            {
                
            }
        }

    }
    
    private class ValueValidatorListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            if(newValue)
                return;
            
            for(int i = 0; i < textBox.getText().length(); i++)
            {
                if(!Character.isDigit(textBox.getText().toCharArray()[i]))
                {
                    textBox.setText(attr.getValue().toString());
                    break;
                }
            }
            
            if(Integer.parseInt(textBox.getText()) > attr.getController().get().getValueRange().getMax())
                textBox.setText(attr.getController().get().getValueRange().getMax().toString());
            
            if(Integer.parseInt(textBox.getText()) < attr.getController().get().getValueRange().getMin())
                textBox.setText(attr.getController().get().getValueRange().getMin().toString());
            
            if(!attr.getController().get().getValueRange().isWithinRange(Integer.parseInt(textBox.getText())))
                textBox.setText(attr.getValue().toString());
            
            slider.setValue(Integer.parseInt(textBox.getText()));
        }
    }
    
    private class DefaultTextBoxHandler implements EventHandler<Event>
    {
        @Override
        public void handle(Event event)
        {
            if(textBox.getText().equals(""))
                return;
            
            for(int i = 0; i < textBox.getText().toCharArray().length; i++)
            {
                if(!Character.isDigit(textBox.getText().toCharArray()[i]))
                {
                    textBox.setText(String.valueOf((int)slider.getValue()));
                    return;
                }
            }
            
            if(this.isPartOf(Integer.parseInt(textBox.getText())))
                return;
            
            if(!attr.getController().get().getValueRange().isWithinRange(Integer.parseInt(textBox.getText())))
            {
                textBox.setText(String.valueOf((int)slider.getValue()));
                return;
            }
            
            
            slider.setValue(Integer.valueOf(textBox.getText()));
            
            if(!setValue)
                return;
            
            try
            {
                attr.getController().get().setValue((int)slider.getValue());
            }
            catch (ValueSetFailedException ex)
            {
                ex.printStackTrace();
            }
        }
        
        private boolean isPartOf(int num)
        {
            boolean is = false;
            
            for(int i = 0; i < attr.getController().get().getValueRange().getAllInRange().size(); i++)
            {
                String str = String.valueOf(attr.getController().get().getValueRange().getAllInRange().get(i));
                str = str.substring(0, str.length()-1);
                int partVal = Integer.parseInt(str);
                if(partVal == num)
                    is = true;
            }
            return is;
        }
    }
}
