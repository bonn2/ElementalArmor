package ca.minecore.elementalarmor.util.emums;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.ChatUtil;
import ca.minecore.elementalarmor.util.persistence.PersistentCharmType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Charm {
    // Air charms
    FIRE_THORNS(ArmorType.FIRE, ChatColor.GOLD + "Fire Thorns", Material.BLAZE_POWDER),
    LAVA_WALKING(ArmorType.FIRE, ChatColor.GOLD + "Lava Walking", Material.MAGMA_CREAM),
    EXPLOSION(ArmorType.FIRE, ChatColor.GOLD + "Explosion", Material.TNT),

    // Air charms
    PUSH_NEARBY(ArmorType.AIR, ChatColor.AQUA + "Gust", Material.FEATHER),
    ELYTRA_BOOST(ArmorType.AIR, ChatColor.GOLD + "Elytra Boost", Material.GUNPOWDER),
    SLOWFALL(ArmorType.AIR, ChatColor.AQUA + "Slow Falling", Material.FEATHER),

    // Earth charms
    TELEKINESIS(ArmorType.EARTH, ChatColor.GREEN + "Telekinesis", Material.GHAST_TEAR),

    // Water charms
    FAST_SWIM(ArmorType.WATER, ChatColor.BLUE + "Fast Swim", Material.LAPIS_LAZULI),

    // No Armor
    NONE(ArmorType.NONE, "", Material.AIR);

    private final ArmorType type;
    private final String formattedName;
    private final Material material;
    private static final NamespacedKey CHARM = new NamespacedKey(Main.plugin, "CHARM");

    Charm(ArmorType type, String formattedName, Material material) {
        this.type = type;
        this.formattedName = formattedName;
        this.material = material;
    }

    public ArmorType getType() {
        return type;
    }

    public String getFormattedName() {
        return ChatUtil.colorize(formattedName);
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "Charm of " + formattedName);
        meta.getPersistentDataContainer().set(CHARM, new PersistentCharmType(), this);
        item.setItemMeta(meta);
        return item;
    }
}
