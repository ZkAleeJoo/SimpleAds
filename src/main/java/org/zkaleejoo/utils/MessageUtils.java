package org.zkaleejoo.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String getColoredMessage(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
}
