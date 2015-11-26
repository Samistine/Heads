package com.gmail.logout400.Heads;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SimpleSkull {

    public static ItemStack getNamedSkull(String nick) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        meta.setOwner(nick);
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack getSkull(String type) {
        if (type.equalsIgnoreCase("PLAYER")) {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        }
        if (type.equalsIgnoreCase("ZOMBIE")) {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
        }
        if (type.equalsIgnoreCase("WITHER")) {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        }
        if (type.equalsIgnoreCase("CREEPER")) {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
        }
        if (type.equalsIgnoreCase("SKELETON")) {
            return new ItemStack(Material.SKULL_ITEM, 1);
        }
        return null;
    }

    public static String getSkullBlockOwner(Block block) {
        if (block.getType() == Material.SKULL) {
            Skull skull = (Skull) block.getState();
            SkullType type = skull.getSkullType();
            if (type == SkullType.PLAYER) {
                String owner = skull.getOwner();
                if (skull.hasOwner()) {
                    return owner;
                } else {
                    return "Steve";
                }
            }

            if (type == SkullType.WITHER) {
                return "Wither";
            }
            if (type == SkullType.ZOMBIE) {
                return "Zombie";
            }
            if (type == SkullType.CREEPER) {
                return "Creeper";
            }
            if (type == SkullType.SKELETON) {
                return "Skeleton";
            } else {
                return "[HEADS_NULL]";
            }
        }
        return "[HEADS_NULL]";
    }

    public static void sendMessage(CommandSender sender, String message) {
        String msg = "&e[Heads]&r " + message;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void customMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
