package org.zkaleejoo.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.zkaleejoo.SimpleAds;
import java.util.List;

public class MainConfigManager {
    
    private CustomConfig configFile;
    private CustomConfig langFile;
    private SimpleAds plugin;
    private String prefix;
    private String selectedLanguage;
    private int titleFadeIn, titleStay, titleFadeOut;
    private boolean titleSoundEnabled, chatSoundEnabled;
    private String titleSound, chatSound;
    private String noPermission, pluginReload, usageTitle, usageAnunce, errorSound;
    private List<String> adDecoration;
    private boolean updateCheckEnabled;
    private String msgUpdateAvailable, msgUpdateCurrent, msgUpdateDownload;
    private String titleSent, anunceSent, helpHeader;
    private List<String> helpLines;
    private boolean autoAdsEnabled;
    private int autoAdsInterval;
    private boolean autoAdsRandom;
    private List<String> autoAdsMessages;

    public MainConfigManager(SimpleAds plugin) {
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml", null, plugin, false);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = configFile.getConfig();
        
        prefix = config.getString("general.prefix", "&8[&9SimpleAds&8] ");
        selectedLanguage = config.getString("general.language", "en");
        
        titleFadeIn = config.getInt("title.fadein", 10);
        titleStay = config.getInt("title.stay", 70);
        titleFadeOut = config.getInt("title.fadeout", 20);
        titleSoundEnabled = config.getBoolean("title.sound.enabled", true);
        titleSound = config.getString("title.sound.sound", "ENTITY_PLAYER_LEVELUP");
        chatSoundEnabled = config.getBoolean("anunce.sound.enabled", true);
        chatSound = config.getString("anunce.sound.sound", "ENTITY_PLAYER_LEVELUP");
        autoAdsEnabled = config.getBoolean("auto-ads.enabled", true);
        autoAdsInterval = config.getInt("auto-ads.interval", 300);
        autoAdsRandom = config.getBoolean("auto-ads.random", false);
        autoAdsMessages = config.getStringList("auto-ads.messages");

        String langPath = "messages_" + selectedLanguage + ".yml";
        langFile = new CustomConfig(langPath, "lang", plugin, false);
        langFile.registerConfig();
        FileConfiguration lang = langFile.getConfig();

        noPermission = lang.getString("messages.no-permission", "&cNo permission.");
        pluginReload = lang.getString("messages.plugin-reload", "&aReloaded!");
        usageTitle = lang.getString("messages.usage-title", "&cUsage: /ads title...");
        usageAnunce = lang.getString("messages.usage-anunce", "&cUsage: /ads anunce...");
        errorSound = lang.getString("messages.error-sound", "&cSound error.");
        adDecoration = lang.getStringList("anunce.decoration");
        updateCheckEnabled = config.getBoolean("general.update-check", true);
        msgUpdateAvailable = lang.getString("messages.update-available", "&eNew version: {version}");
        msgUpdateCurrent = lang.getString("messages.update-current", "&7Current: {version}");
        msgUpdateDownload = lang.getString("messages.update-download", "&eUpdate now!");
        titleSent = lang.getString("messages.title-sent", "&aTitle sent!");
        anunceSent = lang.getString("messages.anunce-sent", "&aAnnouncement sent!");
        helpHeader = lang.getString("messages.help-header", "&fCommands:");
        helpLines = lang.getStringList("messages.help-lines");
    }

    public void reloadConfig() {
        configFile.reloadConfig();
        if (langFile != null) langFile.reloadConfig();
        loadConfig();
    }

    public String getPrefix() { return prefix; }
    public String getNoPermission() { return noPermission; }
    public String getPluginReload() { return pluginReload; }
    public String getUsageTitle() { return usageTitle; }
    public String getUsageAnunce() { return usageAnunce; }
    public String getErrorSound() { return errorSound; }
    public List<String> getAdDecoration() { return adDecoration; }
    public int getTitleFadeIn() { return titleFadeIn; }
    public int getTitleStay() { return titleStay; }
    public int getTitleFadeOut() { return titleFadeOut; }
    public boolean isTitleSoundEnabled() { return titleSoundEnabled; }
    public String getTitleSound() { return titleSound; }
    public boolean isChatSoundEnabled() { return chatSoundEnabled; }
    public String getChatSound() { return chatSound; }
    public boolean isUpdateCheckEnabled() { return updateCheckEnabled; }
    public String getMsgUpdateAvailable() { return msgUpdateAvailable; }
    public String getMsgUpdateCurrent() { return msgUpdateCurrent; }
    public String getMsgUpdateDownload() { return msgUpdateDownload; }
    public String getTitleSent() { return titleSent; }
    public String getAnunceSent() { return anunceSent; }
    public String getHelpHeader() { return helpHeader; }
    public List<String> getHelpLines() { return helpLines; }
    public boolean isAutoAdsEnabled() { return autoAdsEnabled; }
    public int getAutoAdsInterval() { return autoAdsInterval; }
    public boolean isAutoAdsRandom() { return autoAdsRandom; }
    public List<String> getAutoAdsMessages() { return autoAdsMessages; }

}