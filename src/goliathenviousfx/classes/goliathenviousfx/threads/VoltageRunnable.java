package goliathenviousfx.threads;

import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;

public class VoltageRunnable implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            try
            {
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageCurrent().update();
                NvSettings.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getVoltageOffset().update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }  
}
