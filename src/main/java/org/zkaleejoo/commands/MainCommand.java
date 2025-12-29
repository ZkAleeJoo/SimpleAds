package org.zkaleejoo.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.zkaleejoo.SimpleAds;
import org.zkaleejoo.config.MainConfigManager;
import org.zkaleejoo.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Registry;
import org.bukkit.NamespacedKey;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final SimpleAds plugin;

    public MainCommand(SimpleAds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        MainConfigManager config = plugin.getMainConfigManager();
        String prefix = config.getPrefix();

        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("simpleads.use")) {
                sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getNoPermission()));
                return true;
            }
            config.reloadConfig();
            sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getPluginReload()));
            return true;
        }

        if (args.length >= 1) {
            String sub = args[0].toLowerCase();

            if (sub.equals("title")) {
                if (!sender.hasPermission("simpleads.use")) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getNoPermission()));
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getUsageTitle()));
                    return true;
                }

                String title = args[1];
                StringBuilder subtitleBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    subtitleBuilder.append(args[i]).append(" ");
                }
                String subtitle = subtitleBuilder.toString().trim();

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendTitle(
                        MessageUtils.getColoredMessage(title),
                        MessageUtils.getColoredMessage(subtitle),
                        config.getTitleFadeIn(),
                        config.getTitleStay(),
                        config.getTitleFadeOut()
                    );

                    if (config.isTitleSoundEnabled()) {
                        playConfigSound(p, config.getTitleSound());
                    }
                }
                
                if (!(sender instanceof Player)) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + "&aTitle sent to all players."));
                }
                return true;
            }

            else if (sub.equals("anunce")) {
                if (!sender.hasPermission("simpleads.use")) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getNoPermission()));
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + config.getUsageAnunce()));
                    return true;
                }

                StringBuilder msgBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    msgBuilder.append(args[i]).append(" ");
                }
                String advertisement = msgBuilder.toString().trim();

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    for (String line : config.getAdDecoration()) {
                        p.sendMessage(MessageUtils.getColoredMessage(line.replace("{message}", advertisement)));
                    }

                    if (config.isChatSoundEnabled()) {
                        playConfigSound(p, config.getChatSound());
                    }
                }

                if (!(sender instanceof Player)) {
                    sender.sendMessage(MessageUtils.getColoredMessage(prefix + "&aAnnouncement sent to all players."));
                }
                return true;
            }
        }

        help(sender);
        return true;
    }

    private void playConfigSound(Player player, String soundName) {
        try {
            NamespacedKey key = NamespacedKey.minecraft(soundName.toLowerCase());
            Sound sound = Registry.SOUNDS.get(key);
            
            if (sound != null) {
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            } else {
                plugin.getLogger().warning("Sound not found in Minecraft registry: " + soundName);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid sound format in config: " + soundName);
        }
    }

        public void help(CommandSender sender) {
        MainConfigManager config = plugin.getMainConfigManager();
        String version = plugin.getDescription().getVersion();
        
        sender.sendMessage(MessageUtils.getColoredMessage(
            config.getHelpHeader().replace("{version}", version)
        ));
        
        for (String line : config.getHelpLines()) {
            sender.sendMessage(MessageUtils.getColoredMessage(line));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!sender.hasPermission("simpleads.use")) return completions;

        if (args.length == 1) {
            String input = args[0].toLowerCase();
            if ("title".startsWith(input)) completions.add("title");
            if ("anunce".startsWith(input)) completions.add("anunce");
            if ("reload".startsWith(input)) completions.add("reload");
            if ("help".startsWith(input)) completions.add("help");
        }
        return completions;
    }
}