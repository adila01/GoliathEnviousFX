package goliathenviousfx.custom;

import goliath.envious.exceptions.ValueSetFailedException;
import goliath.envious.interfaces.NvControllable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class LabeledSlider extends HBox
{
    private final Slider slider;
    private final TextField textBox;
    private final ReadOnlyNvReadable<Integer> attr;
    private final boolean setValue;
    
    public LabeledSlider(ReadOnlyNvReadable<Integer> attribute, boolean setVal)
    {
        super();
        super.setSpacing(10*GoliathENVIOUSFX.SCALE);
        
        attr = attribute;
        setValue = setVal;

        slider = new Slider();
        slider.prefWidthProperty().bind(super.widthProperty().multiply(.92));
        slider.getStyleClass().add("slider");
        
        if(!attribute.getController().isEmpty())
        {
            slider.setMin(attr.getController().get().getMinValue());
            slider.setMax(attr.getController().get().getMaxValue());
            slider.setValue(attr.getCurrentValue());
        }
        else
        {
            slider.setMin(attr.getCurrentValue());
            slider.setMax(attr.getCurrentValue());
            slider.setValue(attr.getCurrentValue());
        }
        
        slider.setOnMouseReleased(new DefaultContSliderHandler());
        
        textBox = new TextField();
        textBox.focusedProperty().addListener(new ValueValidatorListener());
        textBox.prefWidthProperty().bind(super.widthProperty().multiply(.08));
        textBox.setEditable(true);
        
        textBox.setText(String.valueOf(attr.getCurrentValue()));
        
        //textBox.setOnKeyTyped(new DefaultTextBoxHandler());
        
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
    
    public NvControllable<Integer> getController()
    {
        return attr.getController().get();
    }
    
    private class DefaultContSliderHandler implements EventHandler<Event>
    {
        @Override
        public void handle(Event event)
        {
            textBox.setText(String.valueOf((int)slider.getValue()));
            
            if(!setValue)
                return;
            
            try
            {
                attr.getController().get().setValue((int)slider.getValue());
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
            
            if(Integer.parseInt(textBox.getText()) > attr.getController().get().getAllValues().getMax())
                textBox.setText(attr.getController().get().getAllValues().getMax().toString());
            
            if(Integer.parseInt(textBox.getText()) < attr.getController().get().getAllValues().getMin())
                textBox.setText(attr.getController().get().getAllValues().getMin().toString());
            
            if(!attr.getController().get().getAllValues().isWithinRange(Integer.parseInt(textBox.getText())))
                textBox.setText(attr.getCurrentValue().toString());
            
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
            
            if(!attr.getController().get().getAllValues().isWithinRange(Integer.parseInt(textBox.getText())))
            {
                System.out.print(Integer.parseInt(textBox.getText()));
                System.out.print(!attr.getController().get().getAllValues().isWithinRange(Integer.parseInt(textBox.getText())));
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
            
            for(int i = 0; i < attr.getController().get().getAllValues().getAllInRange().size(); i++)
            {
                String str = String.valueOf(attr.getController().get().getAllValues().getAllInRange().get(i));
                str = str.substring(0, str.length()-1);
                int partVal = Integer.parseInt(str);
                if(partVal == num)
                    is = true;
            }
            return is;
        }
    }
}
