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
import goliath.nvsmi.main.NvSMI;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.buttontabnav.SectionContentPane;
import goliathenviousfx.custom.ReactionSliderBox;
import goliathenviousfx.custom.Space;

public class FanTempReactionSectionPane extends SectionContentPane
{
    private final ReactionSliderBox tempFanSpeedReaction;
    private final ReactionSliderBox tempLogoBrightnessReaction;
    
    public FanTempReactionSectionPane(NvGPU g)
    {
        super(g.getNvTarget() + " Temperature Based NvReactions");
        
        tempFanSpeedReaction = new ReactionSliderBox("Fan Speed(" + NvSettings.getPrimaryNvGPUInstance().getNvFan().getFanTargetSpeed().getUnit() + ")", NvSMI.getPrimaryNvGPUInstance().getCoreTemp(), NvSettings.getNvGPUInstance(g).getNvFan().getFanTargetSpeed().getController().get());
        
        tempLogoBrightnessReaction = new ReactionSliderBox("Logo Brightness(" + NvSettings.getPrimaryNvGPUInstance().getNvGPULogoBrightness().getUnit() + ")", NvSMI.getPrimaryNvGPUInstance().getCoreTemp(), NvSettings.getNvGPUInstance(g).getNvGPULogoBrightness().getController().get());
        
        Space space = new Space(true);
        space.setMinHeight(1*GoliathEnviousFX.SCALE);
        space.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        super.addTo(tempFanSpeedReaction);
        super.addTo(space);
        super.addTo(tempLogoBrightnessReaction);
    }
}
