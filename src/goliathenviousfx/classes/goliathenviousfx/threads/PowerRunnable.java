package goliathenviousfx.threads;

import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.main.NvSMI;

public class PowerRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPowerDraw();
            NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getEnforcedPowerLimit();
            NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPowerLimit();            
        }
    }
}
