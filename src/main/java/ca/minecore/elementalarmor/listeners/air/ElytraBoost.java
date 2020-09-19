package ca.minecore.elementalarmor.listeners.air;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.Counter;
import ca.minecore.elementalarmor.util.emums.Charm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElytraBoost implements Listener {

    Map<UUID, Counter> counters = new HashMap<>();

    @EventHandler
    public void onSprint(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingCharm(player, Charm.ELYTRA_BOOST)) {
            if (player.isSprinting()) {
                if (player.isGliding()
                && !player.isSwimming()) {
                    if (counters.containsKey(player.getUniqueId()))
                        counters.get(player.getUniqueId()).increment(-1);
                    else
                        counters.put(player.getUniqueId(), new Counter(60, 60, Charm.ELYTRA_BOOST.getFormattedName()));
                    counters.get(player.getUniqueId()).draw(player);
                    if (!counters.get(player.getUniqueId()).isEmpty())
                        player.setVelocity(player.getLocation().getDirection().multiply(2));
                } else if (counters.containsKey(player.getUniqueId())
                && !counters.get(player.getUniqueId()).isFull()
                && !player.isSwimming()) {
                    counters.get(player.getUniqueId()).increment(1);
                    counters.get(player.getUniqueId()).draw(player);
                }
            } else {
                if (counters.containsKey(player.getUniqueId()) && !counters.get(player.getUniqueId()).isFull()) {
                    counters.get(player.getUniqueId()).increment(1);
                    counters.get(player.getUniqueId()).draw(player);
                }
            }
        }
    }
}
