package goliathenviousfx.settings;

import goliath.envious.abstracts.SettingProperty;

public class AppSettings
{
    private static final SettingProperty<Boolean> NVSMI_ONLY = new NvSMIOnlySetting();
    private static final SettingProperty<Boolean> COMPACT_MONITORS = new CompactMonitorsSetting();
    private static final SettingProperty<Boolean> UNDERCLOCKING = new UnderclockingSetting();
    private static final SettingProperty<Boolean> TRANSFER_RATE_POWERMIZER = new PreferTransferRatePowerMizerSetting();
    
    public static SettingProperty<Boolean> getNvSMIOnly()
    {
        return NVSMI_ONLY;
    }     
    
    public static SettingProperty<Boolean> getCompactMonitors()
    {
        return COMPACT_MONITORS;
    }
    
    public static SettingProperty<Boolean> getAllowUnderclocking()
    {
        return UNDERCLOCKING;
    }
    
    public static SettingProperty<Boolean> getTransferRatePowerMizer()
    {
        return TRANSFER_RATE_POWERMIZER;
    }
}
