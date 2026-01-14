package org.zkaleejoo;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.zkaleejoo.commands.MainCommand;
import org.zkaleejoo.config.MainConfigManager;
import org.zkaleejoo.listeners.PlayerJoinListener;
import org.zkaleejoo.managers.AutoAdManager;
import org.zkaleejoo.utils.MessageUtils;
import org.zkaleejoo.utils.UpdateChecker;
import org.bukkit.ChatColor;

public class SimpleAds extends JavaPlugin {

    public static String prefix = "&8[&9SimpleAds&8] ";
    private MainConfigManager mainConfigManager;
    private String version = getDescription().getVersion();
    private String latestVersion;
    private AutoAdManager autoAdManager;

    @Override
    public void onEnable() {
        int pluginId = 28650; 
        Metrics metrics = new Metrics(this, pluginId);
        mainConfigManager = new MainConfigManager(this);
        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        checkUpdates();
        autoAdManager = new AutoAdManager(this);
        autoAdManager.start();

        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "  _________.__               .__            _____       .___      ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + " /   _____/|__| _____ ______ |  |   ____   /  _  \\    __| _/______");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + " \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\ /  /_\\  \\  / __ |/  ___/");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + " /        \\|  |  Y Y  \\  |_> >  |_\\  ___//    |    \\/ /_/ |\\___ \\ ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "/_______  /|__|__|_|  /   __/|____/\\___  >____|__  /\\____ /____  >");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "        \\/          \\/|__|             \\/        \\/      \\/    \\/ ");


        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&9It was activated correctly in the version "+version));

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&9It was disabled correctly in the version "+version));
    }


    public void registerCommands() {
        MainCommand mainCommand = new MainCommand(this);
        this.getCommand("ads").setExecutor(new MainCommand(this));
        this.getCommand("ads").setTabCompleter(mainCommand);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    private void checkUpdates() {
    if (!mainConfigManager.isUpdateCheckEnabled()) return;
    new UpdateChecker(this, 131350).getVersion(version -> {
        if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
            getLogger().info("You are using the latest version!");
        } else {
            this.latestVersion = version;
            getLogger().warning("A new version is available: " + version);
        }
    });
    }  

    public String getLatestVersion() { return latestVersion; }

    public AutoAdManager getAutoAdManager() {
        return autoAdManager;
    }

}
