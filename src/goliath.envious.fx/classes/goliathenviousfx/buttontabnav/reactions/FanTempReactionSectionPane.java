/*
 * The MIT License
 *
 * Copyright 2018 ty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliathenviousfx.buttontabnav.reactions;

import goliath.nvsettings.targets.NvSettingsGPU;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.ReactionSliderBox;
import javafx.beans.binding.DoubleBinding;

public class FanTempReactionSectionPane extends SectionContentPane
{
    private final ReactionSliderBox tempReaction;
    
    public FanTempReactionSectionPane(NvSettingsGPU g)
    {
        super(g.getTargetString() + " Temperature Fan Speed Reaction");
        
        DoubleBinding bind = super.widthProperty().multiply(.85);
        tempReaction = new ReactionSliderBox("Set the GPU's Fan Speed to the GPU's Temperature using an optional offset.\n\nThe Fan Mode must be set to Manual for this Reaction.", g.getCoreTemp(), g.getFan().getFanTargetSpeed().getController().get());
        tempReaction.minWidthProperty().bind(bind);
        tempReaction.maxWidthProperty().bind(bind);
        
        super.getChildren().add(tempReaction);
    }
}
