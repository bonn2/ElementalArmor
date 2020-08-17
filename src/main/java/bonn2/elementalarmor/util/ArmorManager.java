package bonn2.elementalarmor.util;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.emums.ArmorPiece;
import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import bonn2.elementalarmor.util.persistence.PersistentArmorType;
import bonn2.elementalarmor.util.persistence.PersistentCharmType;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class ArmorManager {

    private static final NamespacedKey TYPE = new NamespacedKey(Main.plugin, "TYPE");
    private static final NamespacedKey CHARM = new NamespacedKey(Main.plugin, "CHARM");

    public static boolean isWearingFullSet(Player player, ArmorType type) {
        boolean output = true;
        try {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                ArmorType itemType = item.getItemMeta().getPersistentDataContainer().getOrDefault(TYPE, new PersistentArmorType(), ArmorType.NONE);
                if (itemType.equals(type)) continue;
                output = false;
            }
            return output;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isWearingCharm(Player player, Charm charm) {
        boolean output = false;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            try {
                Charm itemCharm = item.getItemMeta().getPersistentDataContainer().getOrDefault(CHARM, new PersistentCharmType(), Charm.NONE);
                if (itemCharm.equals(charm)) {
                    output = true;
                    break;
                }
            } catch (NullPointerException ignored) {}
        }
        return output;
    }

    public static Collection<CustomArmor> getFullSet(ArmorType type) {
        Collection<CustomArmor> set = new ArrayList<>();
        set.add(new CustomArmor(type, ArmorPiece.HELMET));
        set.add(new CustomArmor(type, ArmorPiece.CHESTPLATE));
        set.add(new CustomArmor(type, ArmorPiece.LEGGINGS));
        set.add(new CustomArmor(type, ArmorPiece.BOOTS));
        return set;
    }
}
