package org.zkaleejoo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.zkaleejoo.SimpleAds;
import org.zkaleejoo.config.MainConfigManager;
import org.zkaleejoo.utils.MessageUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AutoAdManager {

    private final SimpleAds plugin;
    private BukkitTask currentTask;
    private int currentIndex = 0;

    public AutoAdManager(SimpleAds plugin) {
        this.plugin = plugin;
    }

    public void start() {
        stop(); 
        
        MainConfigManager config = plugin.getMainConfigManager();
        if (!config.isAutoAdsEnabled() || config.getAutoAdsMessages().isEmpty()) return;

        long ticks = config.getAutoAdsInterval() * 20L; 

        currentTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;

            List<String> messages = config.getAutoAdsMessages();
            String messageToSend;

            if (config.isAutoAdsRandom()) {
                messageToSend = messages.get(new Random().nextInt(messages.size()));
            } else {
                messageToSend = messages.get(currentIndex);
                currentIndex = (currentIndex + 1) % messages.size();
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                for (String line : config.getAdDecoration()) {
                    String formattedLine = line.replace("{message}", messageToSend);
                    p.sendMessage(MessageUtils.getColoredMessage(formattedLine, p));
                }
                
                if (config.isChatSoundEnabled()) {
                    try {
                        p.playSound(p.getLocation(), org.bukkit.Sound.valueOf(config.getChatSound().toUpperCase()), 1.0f, 1.0f);
                    } catch (Exception ignored) {}
                }
            }
        }, ticks, ticks);
    }

    public void stop() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
    }
}