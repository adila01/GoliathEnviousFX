package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;

public class PowerMizerRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPerfModes().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPowerMizerMode().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCurrentPerformanceLevel().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
