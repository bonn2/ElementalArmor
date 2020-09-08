package bonn2.elementalarmor.listeners.util;

import bonn2.elementalarmor.util.CustomArmor;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SoulBinding implements Listener {
    @EventHandler
    public void onEquipArmor(ArmorEquipEvent event) {
        if (event.getNewArmorPiece() != null) {
            CustomArmor armor = new CustomArmor(event.getNewArmorPiece());
            if (armor.getSoul() != null && event.getPlayer().getUniqueId() != armor.getSoul()) {
                event.setCancelled(true);
            }
        }
    }
}
