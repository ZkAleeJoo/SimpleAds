package org.zkaleejoo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.zkaleejoo.commands.MainCommand;
import org.zkaleejoo.config.MainConfigManager;
import org.zkaleejoo.utils.MessageUtils;

public class SimpleAds extends JavaPlugin {

    public static String prefix = "&8[&9SimpleAds&8] ";
    private MainConfigManager mainConfigManager;
    private String version = getDescription().getVersion();


    @Override
    public void onEnable() {
        registerCommands();
        mainConfigManager = new MainConfigManager(this);

        Bukkit.getConsoleSender().sendMessage(
                "  _________.__               .__            _____       .___      \n" +
                " /   _____/|__| _____ ______ |  |   ____   /  _  \\    __| _/______\n" +
                " \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\ /  /_\\  \\  / __ |/  ___/\n" +
                " /        \\|  |  Y Y  \\  |_> >  |_\\  ___//    |    \\/ /_/ |\\___ \\ \n" +
                "/_______  /|__|__|_|  /   __/|____/\\___  >____|__  /\\____ /____  >\n" +
                "        \\/          \\/|__|             \\/        \\/      \\/    \\/ \n");

        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&aIt was activated correctly in the version "+version));

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&aIt was disabled correctly in the version "+version));
    }


    public void registerCommands() {
        MainCommand mainCommand = new MainCommand(this);
        this.getCommand("ads").setExecutor(new MainCommand(this));
        this.getCommand("ads").setTabCompleter(mainCommand);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}
