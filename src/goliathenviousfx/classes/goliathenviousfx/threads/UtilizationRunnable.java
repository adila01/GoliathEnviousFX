package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliath.nvsmi.main.NvSMI;

public class UtilizationRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreUtilization().update();
                NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryUtilization().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPCIeUtilization().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEngineUtilization().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoEncoderUtilization().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVideoDecoderUtilization().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
