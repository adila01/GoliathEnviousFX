module goliathenviousfx
{
    requires javafx.base;
    requires javafx.controls;
    requires goliath.envious;
    requires goliath.nvsettings;
    requires goliath.nvsmi;
    requires goliath.nvxconfig;
    requires goliath.css;
    requires java.instrument;
    
    exports goliathenviousfx;
    
    opens goliathenviousfx.buttontabnav.osd;
}
