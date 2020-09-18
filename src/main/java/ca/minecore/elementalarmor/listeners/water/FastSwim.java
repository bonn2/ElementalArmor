package ca.minecore.elementalarmor.listeners.water;

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

public class FastSwim implements Listener {

    Map<UUID, Counter> counters = new HashMap<>();

    @EventHandler
    public void onSwim(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingCharm(player, Charm.FAST_SWIM)) {
            if (player.isSprinting()) {
                if (player.isSwimming()) {
                    if (counters.containsKey(player.getUniqueId()))
                        counters.get(player.getUniqueId()).increment(-1);
                    else
                        counters.put(player.getUniqueId(), new Counter(120, 120, Charm.FAST_SWIM.getFormattedName()));
                    counters.get(player.getUniqueId()).draw(player);
                    if (!counters.get(player.getUniqueId()).isEmpty())
                        player.setVelocity(player.getLocation().getDirection().multiply(2));
                } else if (counters.containsKey(player.getUniqueId())
                        && !counters.get(player.getUniqueId()).isFull()) {
                    counters.get(player.getUniqueId()).increment(2);
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
