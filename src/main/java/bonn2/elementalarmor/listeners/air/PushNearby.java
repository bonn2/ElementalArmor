package bonn2.elementalarmor.listeners.air;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.Timeout;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PushNearby implements Listener {

    Map<UUID, Timeout> timeouts = new HashMap<>();

    @EventHandler
    public void crouchJump(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        Player player = event.getPlayer();
        Vector velocity = player.getVelocity();
        if (player.isSneaking() && velocity.getX() == 0 && velocity.getZ() == 0 && velocity.getY() < -0.1) {
            if (player.getWorld().getBlockAt(event.getFrom()).isPassable()
            && !player.getWorld().getBlockAt(event.getTo().clone().subtract(0, 1, 0)).isPassable()) {
                if (ArmorManager.isWearingCharm(player, Charm.PUSH_NEARBY)
                        && timeouts.getOrDefault(player.getUniqueId(), new Timeout()).isTimedOut()) {
                    Location playerLoc = player.getLocation().clone();
                    for (Entity entity : player.getNearbyEntities(4, 2, 4)) {
                        //TODO: Fix distance to push power relationship
                        Vector direction = entity.getLocation().toVector().clone().subtract(playerLoc.toVector()).normalize();
                        direction.add(new Vector(0, 1, 0));
                        Vector entityVelocity = new Vector(1, 1, 1);
                        entityVelocity.subtract(direction);
                        entity.setVelocity(direction);
                    }
                    timeouts.put(player.getUniqueId(), new Timeout(500, Calendar.MILLISECOND));
                }
            }
        }
    }

}
