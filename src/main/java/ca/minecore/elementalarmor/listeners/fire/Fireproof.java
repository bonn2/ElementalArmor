package ca.minecore.elementalarmor.listeners.fire;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.enums.ArmorType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Fireproof implements Listener {

    @EventHandler
    public void onFireDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)
                    || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)
                    || event.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR)
                    || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                if (ArmorManager.isWearingFullSet(player, ArmorType.FIRE)) {
                    event.setCancelled(true);
                    player.setFireTicks(0);
                }
            }
        }
    }

}
