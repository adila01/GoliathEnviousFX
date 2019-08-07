package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsmi.main.NvSMI;

public class ClockRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreClock().update();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryClock().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
