package com.gmail.logout400.Heads.commands;

import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.SimpleSkull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeadCommand implements CommandExecutor {

    String headAdded;
    String headGiven;
    String commandUse;
    String permissions;
    String headReceived;
    String consoleSender;
    String playerOffline;

    public HeadCommand() {
        Heads plugin = Heads.INSTANCE;
        headAdded = "&a" + plugin.messages.getString("head-added");
        headGiven = "&a" + plugin.messages.getString("head-given");
        commandUse = "&c" + plugin.messages.getString("command-usage");
        permissions = "&c" + plugin.messages.getString("permissions");
        headReceived = "&a" + plugin.messages.getString("head-received");
        consoleSender = "&c" + plugin.messages.getString("console-msg");
        playerOffline = "&c" + plugin.messages.getString("player-offline");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            SimpleSkull.sendMessage(sender, consoleSender);
            return true;
        }
        if (args.length >= 3) {
            SimpleSkull.sendMessage(sender, commandUse);
            return true;
        }
        Player player = (Player) sender;
        String nick = player.getName();
        if (args.length == 0) {
            if (!player.hasPermission("heads.addown")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            player.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(nick)});
            SimpleSkull.sendMessage(sender, headAdded.replaceAll("%head%", "&7" + player.getName() + "&a"));
        } else if (args.length == 1) {
            if (!player.hasPermission("heads.addnamed")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            player.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(args[0])});
            SimpleSkull.sendMessage(sender, headAdded.replaceAll("%head%", "&7" + args[0] + "&a"));
        } else if (args.length == 2) {
            if (!player.hasPermission("heads.give")) {
                SimpleSkull.sendMessage(sender, permissions);
                return true;
            }
            Player get = Bukkit.getPlayer(args[1]);
            if ((get == null) || (!get.isOnline())) {
                SimpleSkull.sendMessage(sender, playerOffline.replaceAll("%player%", "&e" + args[1] + "&c"));
                return true;
            }
            get.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(args[0])});
            SimpleSkull.sendMessage(get, headReceived.replaceAll("%owner%", "&7" + args[0] + "&a"));
            SimpleSkull.sendMessage(sender, headGiven.replaceAll("%player%", "&7" + get.getName() + "&a"));
        }
        return true;
    }
}
