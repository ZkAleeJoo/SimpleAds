package org.zkaleejoo.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String getColoredMessage(String message) {
        return getColoredMessage(message, null);
    }

    // Nuevo método que soporta PAPI
    public static String getColoredMessage(String message, Player player) {
        if (message == null) return "";

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && player != null) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String color = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + color).toString());
        }
        matcher.appendTail(buffer);

        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}