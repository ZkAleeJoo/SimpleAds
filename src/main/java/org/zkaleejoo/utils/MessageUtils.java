package org.zkaleejoo.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)&[0-9A-FK-ORX]|&#[A-F0-9]{6}|§[0-9A-FK-ORX]");
    private final static int CENTER_PX = 154; 

    public static String getColoredMessage(String message) {
        return getColoredMessage(message, null);
    }

    public static String getColoredMessage(String message, Player player) {
        if (message == null || message.isEmpty()) return "";

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && player != null) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        if (message.contains("[center]")) {
            message = centerText(message);
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

    private static String centerText(String text) {
        String cleanText = text.replace("[center]", "");
        
        int messagePxSize = 0;
        boolean isBold = false;
        
        for (int i = 0; i < cleanText.length(); i++) {
            char c = cleanText.charAt(i);
            
            if (c == '&' || c == '§') {
                if (i + 1 < cleanText.length()) {
                    char next = cleanText.charAt(i + 1);
                    if (next == '#') { 
                        i += 7; 
                        continue;
                    } else if ("lL".indexOf(next) != -1) {
                        isBold = true;
                        i++;
                        continue;
                    } else if ("rR0123456789aAbBcCdDeEfFkKmMnoO".indexOf(next) != -1) {
                        isBold = false;
                        i++;
                        continue;
                    }
                }
            }
            
            DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
            messagePxSize++; 
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString() + cleanText;
    }
}