package ca.minecore.elementalarmor.listeners.fire;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.enums.Charm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class FireThorns implements Listener {

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (ArmorManager.isWearingCharm(player, Charm.FIRE_THORNS)) {
                Random random = new Random();
                if (random.nextBoolean())
                    event.getDamager().setFireTicks(event.getDamager().getFireTicks() + 300);
            }
        }
    }
}
