package com.gmail.logout400.Heads.commands;

import com.gmail.logout400.Heads.Config;
import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.SimpleSkull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeadsCommand implements CommandExecutor {

    private final Heads plugin = Heads.INSTANCE;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Config.Messages msgs = Config.getMessages();
        if (args.length != 1) {
            SimpleSkull.sendMessage(sender, msgs.unknownCommand);
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
            SimpleSkull.customMessage(sender, "&e-------------------[ &b&lSamistine Network&r&e ]------------------");
        } else if (args[0].equalsIgnoreCase("version")) {
            if (!sender.hasPermission("heads.version")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            SimpleSkull.sendMessage(sender, msgs.pluginVersion.replaceAll("%version%", "&e" + this.plugin.getVersion() + "&a"));
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("heads.reload")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            plugin.reload();

            SimpleSkull.sendMessage(sender, msgs.reloaded);
        } else if (args[0].equalsIgnoreCase("nick")) {
            if (!sender.hasPermission("heads.nick")) {
                SimpleSkull.sendMessage(sender, msgs.permissions);
                return true;
            }
            if (!(sender instanceof Player)) {
                SimpleSkull.sendMessage(sender, msgs.consoleSender);
                return true;
            }
            Set materials = new HashSet<>(1);
            materials.add(Material.AIR);
            Block block = ((Player) sender).getTargetBlock(materials, 200);
            String owner = SimpleSkull.getSkullBlockOwner(block);
            if (owner.equals("[HEADS_NULL]")) {
                SimpleSkull.sendMessage(sender, msgs.notSkull);
            } else {
                SimpleSkull.sendMessage(sender, msgs.skullInfo.replaceAll("%owner%", "&7" + owner + "&a"));
            }
        } else {
            SimpleSkull.sendMessage(sender, msgs.unknownCommand);
            return true;
        }
        return true;
    }
}
