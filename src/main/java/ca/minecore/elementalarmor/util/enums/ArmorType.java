package ca.minecore.elementalarmor.util.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum ArmorType {
    FIRE(ChatColor.RED + "Fire", new Material[] {
            Material.CHAINMAIL_HELMET,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS
    }),
    WATER(ChatColor.BLUE + "Water", new Material[] {
            Material.TURTLE_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS
    }),
    AIR(ChatColor.AQUA + "Air", new Material[] {
            Material.CHAINMAIL_HELMET,
            Material.ELYTRA,
            Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS
    }),
    EARTH(ChatColor.GOLD + "Earth", new Material[] {
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS
    }),
    MAGIC(ChatColor.LIGHT_PURPLE + "Magic", new Material[] {
            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS
    }),
    NONE("", new Material[0]);

    private final String formattedName;
    private final Material[] materials;

    ArmorType(String formattedName, Material[] materials) {
        this.formattedName = formattedName;
        this.materials = materials;
    }

    public Material[] getMaterials() {
        return materials;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
