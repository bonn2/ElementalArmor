package bonn2.elementalarmor.util;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.emums.ArmorPiece;
import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import bonn2.elementalarmor.util.exceptions.InvalidCharmException;
import bonn2.elementalarmor.util.persistence.PersistentArmorType;
import bonn2.elementalarmor.util.persistence.PersistentCharmType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomArmor extends ItemStack {

    private static final NamespacedKey TYPE = new NamespacedKey(Main.plugin, "TYPE");
    private static final NamespacedKey CHARM = new NamespacedKey(Main.plugin, "CHARM");
    private static final NamespacedKey NAME = new NamespacedKey(Main.plugin, "NAME");
    private final ArmorType type;
    private final String name;
    private Charm charm;

    public CustomArmor(ArmorType type, ArmorPiece piece) {
        super(type.getMaterials()[piece.getIndex()]);
        this.type = type;
        this.name = type.getFormattedName() + " " + piece.getFormattedName();
        ItemMeta temp = getItemMeta();
        assert temp != null;
        temp.getPersistentDataContainer().set(TYPE, new PersistentArmorType(), type);
        temp.getPersistentDataContainer().set(NAME, PersistentDataType.STRING, name);
        temp.setDisplayName(name);
        setItemMeta(temp);
    }

    public CustomArmor(ItemStack item) {
        super(item);
        ItemMeta temp = getItemMeta();
        assert temp != null;
        this.type = temp.getPersistentDataContainer().getOrDefault(TYPE, new PersistentArmorType(), ArmorType.NONE);
        this.charm = temp.getPersistentDataContainer().getOrDefault(CHARM, new PersistentCharmType(), Charm.NONE);
        this.name = temp.getPersistentDataContainer().getOrDefault(NAME, PersistentDataType.STRING, temp.getDisplayName());
    }

    /**
     * Sets / Replaces charm on item. Will also change item name to format "%type% %piece% of %charm%"
     * @param charm The charm to add to the item
     * @throws InvalidCharmException Thrown when charm is not allowed to be added to this type of armor
     */
    public void setCharm(Charm charm) throws InvalidCharmException {
        if (charm.getType().equals(type)) {
            this.charm = charm;
            ItemMeta temp = getItemMeta();
            assert temp != null;
            temp.getPersistentDataContainer().set(CHARM, new PersistentCharmType(), charm);
            temp.setDisplayName(name + " of " + charm.getFormattedName());
            setItemMeta(temp);
        } else {
            throw new InvalidCharmException(ChatColor.RED + charm.getType().name() + " charm cannot be put on " + type.name() + " armor!");
        }
    }

    /**
     * Get charm that is currently on item
     * @return The current charm
     */
    public Charm getCharm() {
        return charm;
    }

    /**
     * Gets the ArmorType of the item
     * @return The ArmorType
     */
    public ArmorType getArmorType() {
        return type;
    }
}
