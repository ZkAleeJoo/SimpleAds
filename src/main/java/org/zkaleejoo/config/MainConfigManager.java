package org.zkaleejoo.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.zkaleejoo.SimpleAds;

import java.util.List;

public class MainConfigManager {

    private CustomConfig configFile;
    private SimpleAds plugin;

    private String nopermissionMessage;
    private String prefix;
    private String pluginreload;
    private Integer titlefadeIn;
    private Integer titlestay;
    private Integer titlefadeOut;
    private Boolean tittlesoundenabled;
    private String tittlesound;

    private List<String> messages;
    private Boolean chatsoundenabled;
    private String chatsound;


    public MainConfigManager(SimpleAds plugin) {
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml", null, plugin);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = configFile.getConfig();
        nopermissionMessage = config.getString("no-permission-message");
        prefix = config.getString("prefix");
        pluginreload = config.getString("plugin-reload");
        titlefadeIn = config.getInt("title.fadein");
        titlestay = config.getInt("title.stay");
        titlefadeOut = config.getInt("title.fadeout");
        tittlesoundenabled = config.getBoolean("title.sound.enabled");
        tittlesound = config.getString("title.sound.sound");
        messages = config.getStringList("anunce.decoration");
        chatsoundenabled = config.getBoolean("anunce.sound.enabled");
        chatsound = config.getString("anunce.sound.sound");

    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }


    public String getNopermissionMessage() {
        return nopermissionMessage;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPluginreload() {
        return pluginreload;
    }

    public String getTittlesound() {
        return tittlesound;
    }

    public Boolean getTittlesoundenabled() {
        return tittlesoundenabled;
    }

    public Integer getTitlefadeOut() {
        return titlefadeOut;
    }

    public Integer getTitlestay() {
        return titlestay;
    }

    public Integer getTitlefadeIn() {
        return titlefadeIn;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getChatsound() {
        return chatsound;
    }

    public Boolean getChatsoundenabled() {
        return chatsoundenabled;
    }
}

