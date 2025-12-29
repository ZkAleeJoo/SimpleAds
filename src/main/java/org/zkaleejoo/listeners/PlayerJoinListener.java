package org.zkaleejoo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zkaleejoo.SimpleAds;
import org.zkaleejoo.utils.MessageUtils;
import org.zkaleejoo.config.MainConfigManager;

public class PlayerJoinListener implements Listener {
    private final SimpleAds plugin;

    public PlayerJoinListener(SimpleAds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("simpleads.use")) return;

        String latest = plugin.getLatestVersion();
        MainConfigManager config = plugin.getMainConfigManager();

        if (latest != null && !plugin.getDescription().getVersion().equalsIgnoreCase(latest)) {
            player.sendMessage(MessageUtils.getColoredMessage(config.getPrefix() + config.getMsgUpdateAvailable().replace("{version}", latest)));
            player.sendMessage(MessageUtils.getColoredMessage(config.getMsgUpdateDownload()));
        }
    }
}