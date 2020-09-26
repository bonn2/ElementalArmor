package ca.minecore.elementalarmor.util;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.enums.ArmorPiece;
import ca.minecore.elementalarmor.util.enums.ArmorType;
import ca.minecore.elementalarmor.util.enums.Charm;
import ca.minecore.elementalarmor.util.exceptions.InvalidCharmException;
import ca.minecore.elementalarmor.util.persistence.PersistentArmorType;
import ca.minecore.elementalarmor.util.persistence.PersistentCharmType;
import ca.minecore.elementalarmor.util.persistence.PersistentUUIDType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomArmor extends ItemStack {

    private static final NamespacedKey TYPE = new NamespacedKey(Main.plugin, "TYPE");
    private static final NamespacedKey CHARM = new NamespacedKey(Main.plugin, "CHARM");
    private static final NamespacedKey NAME = new NamespacedKey(Main.plugin, "NAME");
    private static final NamespacedKey SOUL = new NamespacedKey(Main.plugin, "SOUL");
    private final ArmorType type;
    private final String name;
    private Charm charm;
    private UUID soul;

    public CustomArmor(ArmorType type, ArmorPiece piece) {
        super(type.getMaterials()[piece.getIndex()]);
        this.type = type;
        this.name = type.getFormattedName() + " " + piece.getFormattedName();
        this.soul = null;
        ItemMeta temp = getItemMeta();
        assert temp != null;
        temp.getPersistentDataContainer().set(TYPE, new PersistentArmorType(), type);
        temp.getPersistentDataContainer().set(NAME, PersistentDataType.STRING, name);
        temp.setDisplayName(name);
        setItemMeta(temp);
    }

    public CustomArmor(@NotNull ItemStack item) {
        super(item);
        ItemMeta temp = getItemMeta();
        assert temp != null;
        this.type = temp.getPersistentDataContainer().getOrDefault(TYPE, new PersistentArmorType(), ArmorType.NONE);
        this.charm = temp.getPersistentDataContainer().getOrDefault(CHARM, new PersistentCharmType(), Charm.NONE);
        this.name = temp.getPersistentDataContainer().getOrDefault(NAME, PersistentDataType.STRING, temp.getDisplayName());
        try {
            this.soul = temp.getPersistentDataContainer().get(SOUL, new PersistentUUIDType());
        } catch (NullPointerException e) {
            this.soul = null;
        }
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
            throw new InvalidCharmException(charm.getType(), type);
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

    public void setSoul(Player player) {
        ItemMeta temp = getItemMeta();
        assert temp != null;
        temp.getPersistentDataContainer().set(SOUL, new PersistentUUIDType(), player.getUniqueId());
        List<String> lore = new ArrayList<>();
        lore.add(colorize("&bBound to &c" + player.getName()));
        temp.setLore(lore);
        setItemMeta(temp);
    }

    /**
     * Returns null if no owner is stored
     * @return The owners UUID
     */
    public UUID getSoul() {
        return soul;
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
