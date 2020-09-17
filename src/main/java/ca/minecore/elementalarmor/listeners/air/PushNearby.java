package ca.minecore.elementalarmor.listeners.air;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.Counter;
import ca.minecore.elementalarmor.util.Timeout;
import ca.minecore.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PushNearby implements Listener {

    Map<UUID, Timeout> timeouts = new HashMap<>();
    Map<UUID, Counter> counters = new HashMap<>();

    @EventHandler
    public void holdCrouch(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            if (ArmorManager.isWearingCharm(event.getPlayer(), Charm.PUSH_NEARBY)) {
                counters.put(event.getPlayer().getUniqueId(), new Counter(60, 0, Charm.PUSH_NEARBY.getFormattedName(), 10));
                counters.get(event.getPlayer().getUniqueId()).draw(event.getPlayer());
                Bukkit.getServer().getScheduler().runTaskTimer(Main.plugin, t -> {
                    if (!event.getPlayer().isSneaking()) {
                        counters.get(event.getPlayer().getUniqueId()).set(0);
                        counters.get(event.getPlayer().getUniqueId()).draw(event.getPlayer());
                        counters.remove(event.getPlayer().getUniqueId());
                        t.cancel();
                        return;
                    }
                    counters.get(event.getPlayer().getUniqueId()).increment(1);
                    counters.get(event.getPlayer().getUniqueId()).draw(event.getPlayer());
                }, 0, 1);
            }
        }
    }

    @EventHandler
    public void crouchJump(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        Player player = event.getPlayer();
        Vector velocity = player.getVelocity();
        if (player.isSneaking()
        && velocity.getY() < -0.1
        && player.getWorld().getBlockAt(event.getFrom()).isPassable()
        && !player.getWorld().getBlockAt(event.getTo().clone().subtract(0, 1, 0)).isPassable()
        && ArmorManager.isWearingCharm(player, Charm.PUSH_NEARBY)
        && timeouts.getOrDefault(player.getUniqueId(), new Timeout()).isTimedOut()
        && !counters.getOrDefault(player.getUniqueId(), new Counter(1)).isEmpty()) {
            Location playerLoc = player.getLocation().clone();
            for (Entity entity : player.getNearbyEntities(4, 2, 4)) {
                Vector direction = entity.getLocation().toVector().clone().subtract(playerLoc.toVector()).normalize();
                direction.setY(.5);
                direction.multiply(counters.get(player.getUniqueId()).getCurrent() / 20);
                entity.setVelocity(direction);
            }
            counters.get(player.getUniqueId()).set(0);
            timeouts.put(player.getUniqueId(), new Timeout(500, Calendar.MILLISECOND));
        }
    }

}
