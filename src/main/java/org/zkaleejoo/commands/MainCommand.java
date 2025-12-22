package org.zkaleejoo.commands;

import org.bukkit.Particle;
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

public class MainCommand implements CommandExecutor, TabCompleter {

    private SimpleAds plugin;

    public MainCommand(SimpleAds plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        //COMANDOS EJECUTADOS DESDE LA CONSOLA
        if (!(sender instanceof Player)) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
                plugin.getMainConfigManager().reloadConfig();
                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+plugin.getMainConfigManager().getPluginreload()));
                return true;

            }else if (args.length >= 1 && args[0].equalsIgnoreCase("title")) {

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + "&cUsage: /ads title [title] <subtitle>"));
                    return true;
                }

                String title = args[1];
                String subtitle = "";

                if (args.length > 2) {
                    StringBuilder subtitleBuilder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        subtitleBuilder.append(args[i]).append(" ");
                    }
                    subtitle = subtitleBuilder.toString().trim();
                }

                int fadeIn = plugin.getMainConfigManager().getTitlefadeIn();
                int stay = plugin.getMainConfigManager().getTitlestay();
                int fadeOut = plugin.getMainConfigManager().getTitlefadeOut();

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendTitle(MessageUtils.getColoredMessage(title), MessageUtils.getColoredMessage(subtitle), fadeIn, stay, fadeOut);
                    if (mainConfigManager.getTittlesoundenabled()) {
                        try {
                            String soundName = plugin.getMainConfigManager().getTittlesound();
                            Sound sound = Sound.valueOf(soundName);
                            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Error playing sound: " + e.getMessage());
                        }
                    }
                }

                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+"&aYou have sent a title to all players"));

                return true;

            }else if(args.length >= 1 && args[0].equalsIgnoreCase("anunce")){

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + "&cUsage: /ads anunce [message]"));
                    return true;
                }

                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                String finalMessage = message.toString().trim();
                String anunceMessage = finalMessage;

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    for (String line : plugin.getMainConfigManager().getMessages()) {
                        String replacedLine = line.replace("{message}", anunceMessage);
                        p.sendMessage(MessageUtils.getColoredMessage(replacedLine));
                    }

                    if (mainConfigManager.getChatsoundenabled()) {
                        try {
                            String soundName = plugin.getMainConfigManager().getChatsound();
                            Sound sound = Sound.valueOf(soundName);
                            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Error playing sound: " + e.getMessage());
                        }
                    }

                }

                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+"&aYou have sent an anunce to all players"));
                return true;

            }
            help(sender);
            return true;
        }


        //COMANDOS EJECUTADOS POR EL JUGADOR
        Player player = (Player) sender;

        if (!player.hasPermission("simpleads.user")) {
            player.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+plugin.getMainConfigManager().getNopermissionMessage()));
            return true;
        }

        if (args.length >= 1) {
            //COMANDO /ads title [COSA A ANUNCIAR EN TITLE]
            if (args[0].equalsIgnoreCase("title")) {

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+"&cUsage: /ads title [message]"));
                    return true;
                }

                String title = args[1];
                String subtitle = "";

                if (args.length > 2) {
                    StringBuilder subtitleBuilder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        subtitleBuilder.append(args[i]).append(" ");
                    }
                    subtitle = subtitleBuilder.toString().trim();
                }

                int fadeIn = plugin.getMainConfigManager().getTitlefadeIn();
                int stay = plugin.getMainConfigManager().getTitlestay();
                int fadeOut = plugin.getMainConfigManager().getTitlefadeOut();

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendTitle(MessageUtils.getColoredMessage(title), MessageUtils.getColoredMessage(subtitle), fadeIn, stay, fadeOut);

                    if (mainConfigManager.getTittlesoundenabled()) {
                        try {
                            String soundName = plugin.getMainConfigManager().getTittlesound();
                            Sound sound = Sound.valueOf(soundName);
                            p.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Error playing sound: " + e.getMessage());
                        }
                    }
                }

                return true;

                //COMANDO /ads anunce [COSA A ANUNCIAR EN EL CHAT]
            }else if(args[0].equalsIgnoreCase("anunce")){

                if (args.length < 2) {
                    sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + "&cUsage: /ads anunce [message]"));
                    return true;
                }

                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                String finalMessage = message.toString().trim();
                String anunceMessage = finalMessage;

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    for (String line : plugin.getMainConfigManager().getMessages()) {
                        String replacedLine = line.replace("{message}", anunceMessage);
                        p.sendMessage(MessageUtils.getColoredMessage(replacedLine));
                    }

                    if (mainConfigManager.getChatsoundenabled()) {
                        try {
                            String soundName = plugin.getMainConfigManager().getChatsound();
                            Sound sound = Sound.valueOf(soundName);
                            p.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Error playing sound: " + e.getMessage());
                        }
                    }

                }

            }else if(args[0].equalsIgnoreCase("reload")){
                plugin.getMainConfigManager().reloadConfig();
                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix()+plugin.getMainConfigManager().getPluginreload()));
                return true;
            }
            else {
                help(sender);
            }

        }else {
            help(sender);
        }
        return true;

    }


    public void help(CommandSender sender){
        sender.sendMessage(MessageUtils.getColoredMessage("&8[&9SimpleAds&8] &fList of commands: "+ plugin.getDescription().getVersion()));
        sender.sendMessage(MessageUtils.getColoredMessage("&9> &a/ads title [title] <subtitle>"));
        sender.sendMessage(MessageUtils.getColoredMessage("&9> &a/ads anunce [message]"));
        sender.sendMessage(MessageUtils.getColoredMessage("&9> &a/ads reload"));
    }


    //FUNCION TAB COMPLETER
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();


        if (!sender.hasPermission("simpleads.use")) {
            return completions;
        }

        if (args.length == 1) {

            completions.add("title");
            completions.add("reload");
            completions.add("anunce");

            return filterCompletions(completions, args[0]);
        }


        return completions;
    }

    private List<String> filterCompletions(List<String> completions, String input) {
        List<String> filtered = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(input.toLowerCase())) {
                filtered.add(completion);
            }
        }
        return filtered;
    }

}
