package bonn2.elementalarmor.util.emums;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Charm {
    JUMPING(ArmorType.AIR, ChatColor.AQUA + "Jumping", Material.FEATHER),
    NONE(ArmorType.NONE, "", Material.AIR);

    private final ArmorType type;
    private final String formattedName;
    private final Material material;

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
        meta.setDisplayName(formattedName);
        item.setItemMeta(meta);
        return item;
    }
}
