package com.gmail.logout400.Heads.util;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SimpleSkull {

    public static ItemStack getNamedSkull(String nick) {
        ItemStack skull = getSkull(SkullType.PLAYER);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        meta.setOwner(nick);
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack getSkull(SkullType type) {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) type.ordinal());
    }

    public static String getSkullBlockOwner(Block block) {
        if (block.getType() == Material.SKULL) {
            final Skull skull = (Skull) block.getState();
            final SkullType type = skull.getSkullType();

            switch (type) {
                case PLAYER:
                    String owner = skull.getOwner();
                    if (skull.hasOwner()) {
                        return owner;
                    } else {
                        return "Steve";
                    }
                default:
                    return type.toString();
            }
        }
        return "[HEADS_NULL]";
    }

}
