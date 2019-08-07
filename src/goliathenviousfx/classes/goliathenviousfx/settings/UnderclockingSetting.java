package goliathenviousfx.settings;

import goliath.envious.abstracts.SettingProperty;
import java.util.Properties;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class UnderclockingSetting extends SettingProperty<Boolean>
{
    private final ObjectProperty<Properties> propGroup;
    private final ObjectProperty<Boolean> value;
    
    public UnderclockingSetting()
    {
        super("Allow Underclocking", "goliath.envious.fx.underclocking.allow", true);
        
        propGroup = new SimpleObjectProperty<>(SettingProperty.getSettingProperties("goliath.envious.fx"));
        value = new SimpleObjectProperty<>(Boolean.parseBoolean((String)propGroup.get().getOrDefault(super.getSetting(), super.getDefaultValue().toString())));
    }
    
    @Override
    public ObjectProperty<Properties> propertiesGroupProperty()
    {
        return propGroup;
    }
    
    @Override
    public ObjectProperty<Boolean> valueProperty()
    {
        return value; 
    }

    @Override
    public void setValue(Boolean val)
    {
        propGroup.get().setProperty(this.getSetting(), val.toString());
        value.set(val);
    }
}
