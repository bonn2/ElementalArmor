package bonn2.elementalarmor.listeners.util;

import bonn2.elementalarmor.util.CustomArmor;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SoulBinding implements Listener {
    @EventHandler
    public void onEquipArmor(ArmorEquipEvent event) {
        if (event.getNewArmorPiece() != null) {
            CustomArmor armor;
            try {
                armor = new CustomArmor(event.getNewArmorPiece());
            } catch (NullPointerException e) {
                return;
            }
            if (armor.getSoul() != null && !event.getPlayer().getUniqueId().equals(armor.getSoul())) {
                event.setCancelled(true);
            }
        }
    }
}
