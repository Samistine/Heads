package com.gmail.logout400.Heads.commands;

import com.gmail.logout400.Heads.Config;
import com.gmail.logout400.Heads.SimpleSkull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Config.Messages msgs = Config.getMessages();
        if (!(sender instanceof Player)) {
            SimpleSkull.sendMessage(sender, msgs.consoleSender);
            return true;
        }
        if (args.length >= 3) {
            SimpleSkull.sendMessage(sender, msgs.commandUse);
            return true;
        }
        Player player = (Player) sender;
        String nick = player.getName();
        if (args.length == 0) {
            if (!player.hasPermission("heads.addown")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            player.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(nick)});
            SimpleSkull.sendMessage(sender, msgs.headAdded.replaceAll("%head%", "&7" + player.getName() + "&a"));
        } else if (args.length == 1) {
            if (!player.hasPermission("heads.addnamed")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            player.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(args[0])});
            SimpleSkull.sendMessage(sender, msgs.headAdded.replaceAll("%head%", "&7" + args[0] + "&a"));
        } else if (args.length == 2) {
            if (!player.hasPermission("heads.give")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            Player get = Bukkit.getPlayer(args[1]);
            if ((get == null) || (!get.isOnline())) {
                SimpleSkull.sendMessage(sender, msgs.playerOffline.replaceAll("%player%", "&e" + args[1] + "&c"));
                return true;
            }
            get.getInventory().addItem(new ItemStack[]{SimpleSkull.getNamedSkull(args[0])});
            SimpleSkull.sendMessage(get, msgs.headReceived.replaceAll("%owner%", "&7" + args[0] + "&a"));
            SimpleSkull.sendMessage(sender, msgs.headGiven.replaceAll("%player%", "&7" + get.getName() + "&a"));
        }
        return true;
    }
}
