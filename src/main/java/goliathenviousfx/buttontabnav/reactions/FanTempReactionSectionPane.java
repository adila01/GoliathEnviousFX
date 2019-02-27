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

import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliathenviousfx.GoliathENVIOUSFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.ReactionSliderBox;
import goliathenviousfx.custom.Space;

public class FanTempReactionSectionPane extends SectionContentPane
{
    private final ReactionSliderBox tempFanSpeedReaction;
    private final ReactionSliderBox tempLogoBrightnessReaction;
    
    public FanTempReactionSectionPane(NvGPU g)
    {
        super(g.getTargetString() + " Temperature Based NvReactions");
        
        tempFanSpeedReaction = new ReactionSliderBox("Fan Speed(" + NvSettings.getPrimaryInstance().getNvFan().getFanTargetSpeed().getMeasurement() + ")", NvSettings.getInstance(g).getCoreTemp(), NvSettings.getInstance(g).getNvFan().getFanTargetSpeed().getController().get());
        
        tempLogoBrightnessReaction = new ReactionSliderBox("Logo Brightness(" + NvSettings.getPrimaryInstance().getLogoBrightness().getMeasurement() + ")", NvSettings.getInstance(g).getCoreTemp(), NvSettings.getInstance(g).getLogoBrightness().getController().get());
        
        Space space = new Space(false);
        space.setMinHeight(1*GoliathENVIOUSFX.SCALE);
        space.setMaxHeight(1*GoliathENVIOUSFX.SCALE);
        
        super.add(tempFanSpeedReaction, 0, super.getRowCount());
        super.add(space, 0, super.getRowCount());
        super.add(tempLogoBrightnessReaction, 0, super.getRowCount());
    }
}
