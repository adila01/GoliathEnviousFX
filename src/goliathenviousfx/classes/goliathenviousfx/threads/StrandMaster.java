package goliathenviousfx.threads;

import goliath.envious.enums.UpdateFrequency;
import goliath.envious.exceptions.UpdateFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliath.nvsmi.main.NvSMI;
import java.util.ArrayList;
import java.util.List;

public class StrandMaster 
{
    List<Runnable> runs = new ArrayList<>();
    
    public StrandMaster()
    {
        for(int i = 0; i < NvGPU.getNvGPUList().size(); i++)
        {
            NvReadableRunnable runnable = new NvReadableRunnable();
            
            runnable.addReadOnlyNvReadable(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getCoreUtilization());
            runnable.addReadOnlyNvReadable(NvSMI.getNvGPUInstance(NvGPU.getNvGPUList().get(i)).getMemoryUtilization());
        }
    }
    
    private class NvReadableRunnable implements Runnable
    {
        private List<ReadOnlyNvReadable> readables;
        
        public NvReadableRunnable()
        {
            readables = new ArrayList<>();
        }
        
        public NvReadableRunnable(ReadOnlyNvReadable... rdbls)
        {
            readables = new ArrayList<>();
            readables = List.of(rdbls);
        }
        
        public List<ReadOnlyNvReadable> getReadables()
        {
            return readables;
        }
        
        public UpdateFrequency getUpdateFrequency()
        {
            return readables.get(0).getUpdateFrequency();
        }
        
        public void addReadOnlyNvReadable(ReadOnlyNvReadable rdbl)
        {
            readables.add(rdbl);
        }
        
        @Override
        public void run()
        {
            try
            {
                for(int i = 0; i < readables.size(); i++)
                    readables.get(i).update();
            }
            catch (UpdateFailedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
