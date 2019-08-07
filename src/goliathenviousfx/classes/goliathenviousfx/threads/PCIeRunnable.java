package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;

public class PCIeRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getPCIeCurrentSpeed().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
