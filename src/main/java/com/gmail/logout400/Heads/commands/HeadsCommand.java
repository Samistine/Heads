/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.logout400.Heads.commands;

import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.Messages;
import com.gmail.logout400.Heads.util.SimpleSkull;
import java.util.Collections;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Samuel
 */
public class HeadsCommand implements CommandExecutor {

    private final Heads plugin;
    private final Messages msgs;

    public HeadsCommand(Heads plugin, Messages msgs) {
        this.plugin = plugin;
        this.msgs = msgs;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("head")) {
            return headCommand(sender, command, label, args);
        }
        if (command.getName().equalsIgnoreCase("heads")) {
            return headsCommand(sender, command, label, args);
        }
        return false;
    }

    public void noperm(CommandSender sender) {
        sendMessage(sender, msgs.permissions);
    }

    public boolean headCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, msgs.consoleSender);
            helpMessage(sender);
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            helpMessage(sender);
            if (player.hasPermission("heads.addown")) {
                player.getInventory().addItem(SimpleSkull.getNamedSkull(player.getName()));
                sendMessage(sender, msgs.headAdded.replaceAll("%head%", "&7" + player.getName() + "&a"));
            } else {
                noperm(sender);
            }
            return true;
        }

        if (args.length == 1) {
            if (player.hasPermission("heads.addnamed")) {
                player.getInventory().addItem(SimpleSkull.getNamedSkull(args[0]));
                sendMessage(sender, msgs.headAdded.replaceAll("%head%", "&7" + args[0] + "&a"));
            } else {
                noperm(sender);
            }
            return true;
        }

        if (args.length == 2) {
            if (sender.hasPermission("heads.give")) {
                Player get = plugin.getServer().getPlayer(args[1]);
                if ((get == null) || (!get.isOnline())) {
                    sendMessage(sender, msgs.playerOffline.replaceAll("%player%", "&e" + args[1] + "&c"));
                    return true;
                }
                get.getInventory().addItem(SimpleSkull.getNamedSkull(args[0]));
                sendMessage(get, msgs.headReceived.replaceAll("%owner%", "&7" + args[0] + "&a"));
                sendMessage(sender, msgs.headGiven.replaceAll("%player%", "&7" + get.getName() + "&a"));
            } else {
                noperm(sender);
            }
            return true;
        }

        sendMessage(sender, msgs.commandUse);
        return true;
    }

    public boolean headsCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            //open heads gui
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            helpMessage(sender);
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("version")) {
            sendMessage(sender, msgs.pluginVersion.replaceAll("%version%", "&e" + plugin.getVersion() + "&a"));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("heads.reload")) {
                plugin.reload();
                sendMessage(sender, msgs.reloaded);
            } else {
                noperm(sender);
            }
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("nick")) {
            if (!sender.hasPermission("heads.nick")) {
                sendMessage(sender, msgs.permissions);
                return true;
            }
            if (sender instanceof Player) {
                Block block = ((Player) sender).getTargetBlock(materials, 200);
                String owner = SimpleSkull.getSkullBlockOwner(block);
                if (owner.equals("[HEADS_NULL]")) {
                    sendMessage(sender, msgs.notSkull);
                } else {
                    sendMessage(sender, msgs.skullInfo.replaceAll("%owner%", "&7" + owner + "&a"));
                }
            } else {
                sendMessage(sender, msgs.consoleSender);
            }
            return true;
        }

        sendMessage(sender, msgs.unknownCommand);
        return true;
    }

    private final static Set<Material> materials = Collections.singleton(Material.AIR);

    public void helpMessage(CommandSender sender) {
        customMessage(sender, "&e--------------------[ &b&l Heads v" + plugin.getVersion() + "&r&e ]-------------------");
        if (sender instanceof Player) {
            sendMessage(sender, "&7/head &e- &agives you your head.");
        }
        sendMessage(sender, "&7/head [nick] &e- &aadd head to your inventory.");
        //sendMessage(sender, "&7/head [nick] [player] &e- &agive head to someone.");
        if (sender instanceof Player) {
            sendMessage(sender, "&7/heads &e- &aopens a menu of heads.");
            sendMessage(sender, "&7/heads nick &e- &askin owner of skull you are looking at.");
        }
        sendMessage(sender, "&7/heads help &e- &ashows you this page.");
        sendMessage(sender, "&7/heads version &e- &ashows you plugin version.");
        if (sender.hasPermission("heads.reload")) {
            sendMessage(sender, "&7/heads reload &e- &areloads plugin files.");
        }
        customMessage(sender, "&e-------------------[ &b&lSamistine Network&r&e ]------------------");
    }

    public static void sendMessage(CommandSender sender, String message) {
        String msg = "&e[Heads]&r " + message;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void customMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
