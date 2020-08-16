package bonn2.elementalarmor.util;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.emums.ArmorPiece;
import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class ArmorManager {

    private static final NamespacedKey key = new NamespacedKey(Main.plugin, "TYPE");

    public static boolean isWearingFullSet(Player player, ArmorType type) {
        boolean output = true;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            CustomArmor armor = new CustomArmor(item);
                if (armor.getArmorType().equals(type))
                    continue;
            output = false;
        }
        return output;
    }

    public static boolean isWearingCharm(Player player, Charm charm) {
        boolean output = false;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            CustomArmor armor = new CustomArmor(item);
            if (armor.getCharm().equals(charm)) {
                output = true;
                break;
            }
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
