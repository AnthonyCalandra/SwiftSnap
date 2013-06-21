
package swiftsnap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * This class handles the configuration file which holds the settings.
 * 
 * Wednesday, June 19, 2013
 * @author AnthonyCalandra
 */
public final class Configuration {
    
    private HashMap<String, String> configuration = new HashMap<String, String>();
    private static Configuration instance = null;
    
    /**
     * Sets up the configuration hashmap. Creates the config file, or reads from it.
     */
    private Configuration() {
        // Open the configuration directory... if it doesn't exist, make it and the file and use
        // the default settings.
        File configDir = new File("./config");
        File configFile = new File("./config/configuration.txt");
        if (!configDir.exists()) {
            try {
                setDefaultSettings();
                configDir.mkdir();
                configFile.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            // If it exists, open the file and read it.
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(configFile));
            } catch (FileNotFoundException ex) {
                setDefaultSettings();
                return;
            }

            try {
                String line = null;
                // Read each settings line by line.
                while((line = br.readLine()) != null) {
                    // key=value - extract the key and value from the file.
                    String[] keyval = line.split("=");
                    configuration.put(keyval[0], keyval[1]);
                }

                br.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                setDefaultSettings();
            }
        }
    }
    
    /**
     * If the config file doesn't exist or there are errors, this method initializes default
     * settings.
     */
    public void setDefaultSettings() {
        // Save in Pictures folder.
        configuration.put("savePath", System.getProperty("user.home") + "\\Pictures");
        configuration.put("enableAutoCropping", "true");
    }
    
    /**
     * Overwrite/set a setting.
     * 
     * @param key Setting name.
     * @param value Setting value.
     */
    public void setProperty(String key, String value) {
        configuration.put(key, value);
    }
    
    /**
     * Retrieve a property from the map of settings.
     * 
     * @param key Setting name.
     * @return Setting value.
     */
    public String getProperty(String key) {
        if (!configuration.containsKey(key))
            return null;
        
        return configuration.get(key);
    }
    
    /**
     * Save to the configuration file.
     */
    public void saveConfiguration() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("./config/configuration.txt"));
        } catch (IOException ex) {
            System.err.println("Unable to update settings!");
            System.err.println(ex.getMessage());
            return;
        }
        
        // Write each key=value pair.
        for (String key : configuration.keySet()) {
            pw.println(key + "=" + configuration.get(key));
        }
        
        pw.close();
    }
    
    /**
     * Returns the Configuration instance.
     * 
     * @return Configuration instance.
     */
    public static Configuration loadConfiguration() {
        if (instance == null)
            instance = new Configuration();
        
        return instance;
    }
}
