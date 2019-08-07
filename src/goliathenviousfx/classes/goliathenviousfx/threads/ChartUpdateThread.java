package goliathenviousfx.threads;

import goliathenviousfx.buttontabnav.monitoring.NvMonitorPane;

public class ChartUpdateThread extends Thread
{
    public ChartUpdateThread()
    {
        super();
        super.setDaemon(true);
        super.setName("Goliath Chart Update Thread");
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            
            for(int i = 0; i < NvMonitorPane.getCharts().size(); i++)
                NvMonitorPane.getCharts().get(i).update();
        }
    }
}
