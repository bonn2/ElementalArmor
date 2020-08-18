package bonn2.elementalarmor.listeners.air;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.emums.ArmorType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamage implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (ArmorManager.isWearingFullSet(player, ArmorType.AIR)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
