package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.main.NvSMI;

public class PerformanceLimitRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getIdlePerformanceLimit().update();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getHardwareThermalSlowdownPerformanceLimit();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getHardwarePowerBrakeSlowdownPerformanceLimit();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getSoftwarePowerCapPerformanceLimit();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getSyncBoostPerformanceLimit();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
