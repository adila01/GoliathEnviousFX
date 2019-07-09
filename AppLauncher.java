
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Goliath Modular Application Launcher

    Used to easily package & launch a pure precompiled modular Java 9+ application.

    Notes: 

    - LOCAL_PACKAGE should be set to false once ran once

    - Module directories CANNOT contain non valid module files(zip for example)

    


 */
public class AppLauncher
{
    // All setup goes here 
    public static void init()
    {
        // JVM args
        JVM_LIST_ARGS.add("-client");
        JVM_LIST_ARGS.add("-Djdk.gtk.version=2");
        JVM_LIST_ARGS.add("-Dprism.forceUploadingPainter=true");

        // Add module directories to be copied to the libs folder.
        EXT_MODULE_DIRS.add(new File("/home/ty/Downloads/javafx-sdk-12.0.1/lib"));

        // Add module files to the libs folder
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathTerminal/build/libs/goliath.io.jar"));
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathCSS/dist/goliath.css.jar"));
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathEnvious/dist/goliath.nvsettings.jar"));
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathEnvious/dist/goliath.envious.jar"));
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathEnvious/dist/goliath.nvsmi.jar"));
        EXT_MODULE_JARS.add(new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathEnvious/dist/goliath.nvxconfig.jar"));
    }
    
    
    // Standard logging
    private static final Logger LOGGER = Logger.getLogger("AppLauncher");

    // Module file list. A jar has to have module-info in them to be java 9 modules.
    private static final List<File> EXT_MODULE_JARS = new ArrayList<>();

    // Module directory list. All files within these directories will be added. Archive files and other non modular formats are not allowed.
    private static final List<File> EXT_MODULE_DIRS = new ArrayList<>();

    // Libs directory.
    private static final File LIBS = new File("application");

    // JVM directory for packaged JVMs
    private static final File JVMS = new File("jvm");

    // Launch jar containing the specified main class
    private static final File LAUNCH_JAR = new File("/run/media/ty/Windows_Linux_Shared/NetBeansProjects/GoliathENVIOUSFX/dist/goliathenviousfx.jar");

    // Main class in format <package>.<Class>
    private static final String MAIN = "goliathenviousfx.GoliathEnviousFX";

    // Arguments for the Java Virtual Machine. These need to be valid.
    private static final List<String> JVM_LIST_ARGS = new ArrayList<>();
    
    // Arguments for the application.
    private static final List<String> APP_LIST_ARGS = new ArrayList<>();

    // included JVMs to be used.
    private static final List<JVM> PACKAGED_JVMS = new ArrayList<>();

    // System Java bin - just "java" in Linux.
    private static final String JAVA_SYSTEM = "java";

    // Client Java bin. Reduced memory compared to system.
    private static final String JAVA_CLIENT = "/usr/lib/jvm/java-13-minimal-goliathjdk/bin/java";

    private static final JVM JAVA_TO_USE = JVM.SYSTEM;
    
    // Wait time to use before checking for and printing application errors printed to error stream
    private static final long PROC_WAIT_TIME = 2;
    
    // Time unit to use for checking for and printing application errors printed to error stream 
    private static final TimeUnit PROC_WAIT_UNIT = TimeUnit.SECONDS;

    // Repackage before running.
    private static boolean REPACKAGE = true;

    private static String JVM_STRING_ARGS;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        LOGGER.log(Level.INFO, "Launcher start");
        
        if(!Path.of(LIBS.getAbsolutePath(), LAUNCH_JAR.getName()).toFile().exists() && !LAUNCH_JAR.exists())
        {
            LOGGER.log(Level.SEVERE, "Jar to launch doesn't exist. Exiting.");
            System.exit(1);
        }

        init();

        PACKAGED_JVMS.add(JVM.CLIENT);

        if(REPACKAGE)
        {
            packageDirs();
            packageJars();
            //packageJVMS(); -- not fully added yet
        }

        JVM_STRING_ARGS = listToString(JVM_LIST_ARGS);

        LOGGER.log(Level.INFO, "Launching " + LAUNCH_JAR.getName());
        
        launch(listToString(APP_LIST_ARGS, List.of(args)));
        
        LOGGER.log(Level.INFO, "Launch finished. Exiting.");
    }

    public static void packageDirs()
    {
        try
        {
            if (!LIBS.exists())
            {
                Files.createDirectory(LIBS.toPath());
                LOGGER.log(Level.INFO, "Created directory " + LIBS.getAbsolutePath());
            }
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to created LIBS folder. Exiting.");
            e.printStackTrace();
            System.exit(2);
        }

        for(int i = 0; i < EXT_MODULE_DIRS.size(); i++)
        {
            if(!EXT_MODULE_DIRS.get(i).exists())
            {
                LOGGER.log(Level.SEVERE, "Missing modules from directory: " + EXT_MODULE_DIRS.get(i).getAbsolutePath() + ". Exiting.");
                System.exit(2);
            }
        }

        for(int i = 0; i < EXT_MODULE_DIRS.size(); i++)
        {
            for(int k = 0; k < EXT_MODULE_DIRS.get(i).listFiles().length; k++)
            {
                try
                {
                    Files.copy(EXT_MODULE_DIRS.get(i).listFiles()[k].toPath(), Path.of(LIBS.toPath().toString(), EXT_MODULE_DIRS.get(i).listFiles()[k].getName()), StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.log(Level.INFO, "Copied " + EXT_MODULE_DIRS.get(i).listFiles()[k].getAbsolutePath() + " to " + LIBS.getAbsolutePath());
                }
                catch (IOException ex)
                {
                    LOGGER.log(Level.SEVERE, "Failed copy of " + EXT_MODULE_DIRS.get(i).listFiles()[k].getAbsolutePath() + " to " + LIBS.getAbsolutePath());
                    ex.printStackTrace();
                    System.exit(2);
                }
            }
        }
    }

    public static void packageJars()
    {
        try
        {
            if(!LIBS.exists())
            {
                Files.createDirectory(LIBS.toPath());
                LOGGER.log(Level.INFO, "Created directory " + LIBS.getAbsolutePath());
            }
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to created LIBS folder. Exiting.");
            e.printStackTrace();
            System.exit(3);
        }

        for(int i = 0; i < EXT_MODULE_JARS.size(); i++)
        {
            LOGGER.log(Level.INFO, "Copying " + EXT_MODULE_JARS.get(i).getAbsolutePath() + " to " + LIBS.getAbsolutePath());
            try
            {
                Files.copy(EXT_MODULE_JARS.get(i).toPath(), Path.of(LIBS.toPath().toString(), EXT_MODULE_JARS.get(i).getName()), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex)
            {
                LOGGER.log(Level.SEVERE, "Failed copy of " + EXT_MODULE_JARS.get(i).getAbsolutePath() + " to " + LIBS.getAbsolutePath());
                ex.printStackTrace();
                System.exit(3);
            }
        }

        try
        {
            Files.copy(LAUNCH_JAR.toPath(), Path.of(LIBS.toPath().toString(), LAUNCH_JAR.getName()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "Failed copy of " + LAUNCH_JAR.getAbsolutePath() + " to " + LIBS.getAbsolutePath());
            ex.printStackTrace();
            System.exit(3);
        }

        LOGGER.log(Level.INFO, "Finished local packaging");
    }

    public static void packageJVMS()
    {
        if(!JVMS.exists())
        {
            try
            {
                Files.createDirectory(JVMS.toPath());
            } catch (IOException ex)
            {
                LOGGER.log(Level.SEVERE, "Could not create JVM directory at " + JVMS.getAbsolutePath());
                ex.printStackTrace();
                System.exit(4);
            }
        }

        for(int i = 0; i < PACKAGED_JVMS.size(); i++)
        {
            String name = new File(PACKAGED_JVMS.get(i).getCMD()).getParentFile().getParentFile().getName();
            File root = Path.of(new File(PACKAGED_JVMS.get(i).getCMD()).getParentFile().getParentFile().getParentFile().getPath(), name).toFile();

            try
            {
                Files.copy(root.toPath(), Path.of(JVMS.getAbsolutePath(), root.getName()), StandardCopyOption.REPLACE_EXISTING);
            
            }
            catch (IOException ex)
            {
                LOGGER.log(Level.SEVERE, "Failed to copy " + root.getAbsolutePath() + " to " + JVMS.getAbsolutePath());
                ex.printStackTrace();
                System.exit(4);
            }
        }
    }

    public static String listToString(List<String> userArgs)
    {
        String str = "";

        for (int i = 0; i < userArgs.size(); i++)
        {
            
            if(i == 0)
                str = userArgs.get(i) + " ";
            else if(i != userArgs.size() - 1)
                str = str + userArgs.get(i);
            else
                str = str + " " + userArgs.get(i);
                
        }

        return str;
    }
    
    public static String listToString(List<String> appArgs, List<String> userArgs)
    {
        String str = "";

        for(int i = 0; i < appArgs.size(); i++)
        {
            if(i == 0)
                str = appArgs.get(i) + " ";
            else
                str = str + " " + appArgs.get(i);
        }
        
        for(int i = 0; i < userArgs.size(); i++)
        {
            if(i == 0)
                str = userArgs.get(i) + " ";
            else if(i != userArgs.size() - 1)
                str = str + userArgs.get(i);
            else
                str = str + " " + userArgs.get(i);
        }

        return str;
    }

    public static void launch(String args) throws IOException, InterruptedException
    {
        Process proc;

        if(args.length() != 0)
            proc = Runtime.getRuntime().exec(JAVA_TO_USE + " " + JVM_STRING_ARGS + " --module-path " + LIBS.getAbsolutePath() + " --module " + LAUNCH_JAR.getName().substring(0, LAUNCH_JAR.getName().length() - 4) + "/" + MAIN + " " + args);
        else
            proc = Runtime.getRuntime().exec(JAVA_TO_USE + " " + JVM_STRING_ARGS + " --module-path " + LIBS.getAbsolutePath() + " --module " + LAUNCH_JAR.getName().substring(0, LAUNCH_JAR.getName().length() - 4) + "/" + MAIN);

        LOGGER.log(Level.INFO, "Application started with PID " + proc.pid() + " under user " + System.getProperty("user.name"));
        
        proc.waitFor(PROC_WAIT_TIME, PROC_WAIT_UNIT);
        
        BufferedReader scan = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        
        if(scan.ready())
        {
            System.out.println("Application launch failed. Application output: ");
            
            String out = scan.readLine() + " ";
            
            while(scan.ready())
                out = out + "\n" + scan.readLine();
            
            LOGGER.log(Level.SEVERE, out);
            
            System.exit(5);
        }
    }

    public static enum JVM
    {
        SYSTEM(JAVA_SYSTEM), CLIENT(JAVA_CLIENT);

        private final String cmd;

        private JVM(String cmd)
        {
            this.cmd = cmd;
        }

        public String getCMD()
        {
            return cmd;
        }
        
        @Override
        public String toString()
        {
            return cmd;
        }
    }
}
