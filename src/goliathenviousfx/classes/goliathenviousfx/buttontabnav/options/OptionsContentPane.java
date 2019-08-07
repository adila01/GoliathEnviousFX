package goliathenviousfx.buttontabnav.options;

import goliathenviousfx.buttontabnav.ContentPane;

public class OptionsContentPane extends ContentPane
{
    public OptionsContentPane()
    {
        super();
        
        super.addTo(new AppOptionsSectionContentPane());
        super.addTo(new APIOptionsSectionContentPane());
    }
}
