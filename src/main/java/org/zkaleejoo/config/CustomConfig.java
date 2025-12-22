package org.zkaleejoo.config;

import org.zkaleejoo.SimpleAds;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomConfig {
    private SimpleAds plugin;
    private String fileName;
    private FileConfiguration fileConfiguration = null;
    private File file = null;
    private String folderName;
    private boolean newFile;

    public CustomConfig(String fileName, String folderName, SimpleAds plugin){
        this.fileName = fileName;
        this.folderName = folderName;
        this.plugin = plugin;
        this.newFile = newFile;
    }

    public String getPath(){
        return this.fileName;
    }

    public void updateConfig() {
        InputStream defaultConfigStream = plugin.getResource(fileName);
        if (defaultConfigStream == null) return;

        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
        FileConfiguration currentConfig = getConfig();

        for (String key : defaultConfig.getKeys(true)) {
            if (!currentConfig.contains(key)) {
                currentConfig.set(key, defaultConfig.get(key));
            }
        }
        saveConfig();
    }

    public void registerConfig(){
        if(folderName != null){
            file = new File(plugin.getDataFolder() +File.separator + folderName,fileName);
        }else{
            file = new File(plugin.getDataFolder(), fileName);
        }

        if(!file.exists()){
            if(newFile){
                try{
                    file.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                if(folderName != null){
                    plugin.saveResource(folderName+File.separator+fileName, false);
                }else{
                    plugin.saveResource(fileName, false);
                }
            }
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);

            updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    public boolean reloadConfig() {
        if (fileConfiguration == null) {
            if(folderName != null){
                file = new File(plugin.getDataFolder() +File.separator + folderName, fileName);
            }else{
                file = new File(plugin.getDataFolder(), fileName);
            }

        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        if(file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.setDefaults(defConfig);
        }
        return true;
    }
}