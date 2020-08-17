package bonn2.elementalarmor.util.emums;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.persistence.PersistentCharmType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Charm {
    SLOWFALL(ArmorType.AIR, ChatColor.AQUA + "Slow Falling", Material.FEATHER),
    JUMPING(ArmorType.AIR, ChatColor.AQUA + "Jumping", Material.FEATHER),
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
        return formattedName;
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
