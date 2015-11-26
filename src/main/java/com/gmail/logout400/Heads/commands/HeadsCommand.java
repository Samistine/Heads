package com.gmail.logout400.Heads.commands;

import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.SimpleSkull;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeadsCommand implements CommandExecutor {

    private final Heads plugin = Heads.INSTANCE;
    String permissions = "&c" + plugin.messages.getString("permissions");
    String unknownCommand = "&c" + plugin.messages.getString("unknown-cmd");
    String pluginVersion = "&7" + plugin.messages.getString("plugin-version");
    String reloaded = "&a" + plugin.messages.getString("reloaded");
    String consoleSender = "&c" + plugin.messages.getString("console-msg");
    String skullInfo = "&a" + plugin.messages.getString("skull-info");
    String notSkull = "&c" + plugin.messages.getString("not-skull");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            SimpleSkull.sendMessage(sender, unknownCommand);
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            SimpleSkull.customMessage(sender, "&e--------------------[ &b&l Heads v" + this.plugin.getVersion() + "&r&e ]-------------------");
            SimpleSkull.sendMessage(sender, "&7/head &e- &agives you your head.");
            SimpleSkull.sendMessage(sender, "&7/head [nick] &e- &aadd head to your inventory.");
            SimpleSkull.sendMessage(sender, "&7/head [nick] [player] &e- &agive head to someone.");
            SimpleSkull.sendMessage(sender, "&7/heads help &e- &ashows you this page.");
            SimpleSkull.sendMessage(sender, "&7/heads nick &e- &askin owner of skull you are looking at.");
            SimpleSkull.sendMessage(sender, "&7/heads version &e- &ashows you plugin version.");
            SimpleSkull.sendMessage(sender, "&7/heads reload &e- &areloads plugin files.");
            SimpleSkull.customMessage(sender, "&e-------------------[ &b&lby Logout400&r&e ]------------------");
        } else if (args[0].equalsIgnoreCase("version")) {
            if (!sender.hasPermission("heads.version")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            SimpleSkull.sendMessage(sender, pluginVersion.replaceAll("%version%", "&e" + plugin.getVersion() + "&a"));
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("heads.reload")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            plugin.config.reloadConfig();
            plugin.messages.reloadConfig();

            SimpleSkull.sendMessage(sender, reloaded);
        } else if (args[0].equalsIgnoreCase("nick")) {
            if (!sender.hasPermission("heads.nick")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            if (!(sender instanceof Player)) {
                SimpleSkull.sendMessage(sender, consoleSender);
                return true;
            }
            Block block = ((Player) sender).getTargetBlock(null, 200);
            String owner = SimpleSkull.getSkullBlockOwner(block);
            if (owner.equals("[HEADS_NULL]")) {
                SimpleSkull.sendMessage(sender, notSkull);
            } else {
                SimpleSkull.sendMessage(sender, skullInfo.replaceAll("%owner%", "&7" + owner + "&a"));
            }
        } else {
            SimpleSkull.sendMessage(sender, unknownCommand);
            return true;
        }
        return true;
    }
}
