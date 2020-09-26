package ca.minecore.elementalarmor.listeners.air;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.Timeout;
import ca.minecore.elementalarmor.util.enums.Charm;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SlowFall implements Listener {

    Map<UUID, Timeout> timeouts = new HashMap<>();

    // Allow player to activate slowfall in midair, manually
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) {
            if (ArmorManager.isWearingCharm(player, Charm.SLOWFALL) && !player.isGliding()) {
                if (!player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0)).isPassable()) return;
                if (timeouts.getOrDefault(player.getUniqueId(), new Timeout()).isTimedOut()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0, true));
                    Vector newVelocity = player.getVelocity();
                    newVelocity.setY(newVelocity.getY() * .25);
                    player.setVelocity(newVelocity);
                    timeouts.put(player.getUniqueId(), new Timeout(5, Calendar.SECOND));
                } else {
                    player.sendMessage(ChatColor.RED + "You have to wait " + timeouts.get(player.getUniqueId()).getRemainingTime() + " seconds to do that again.");
                }
            }
        }
    }

    // Automatically activate slowfall to avoid fall damage
    @EventHandler
    public void aboutToLand(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingCharm(player, Charm.SLOWFALL)) {
            World world = player.getWorld();
            Location currentLoc = event.getTo();
            if (currentLoc == null) return;
            double yVec = currentLoc.getY() - event.getFrom().getY();
            if (yVec >= 0) return;
            if ((world.getBlockAt(currentLoc.clone().subtract(0, 1.5, 0)).isPassable()
                    && !world.getBlockAt(currentLoc.clone().subtract(0, 2.5, 0)).isPassable())
            || (world.getBlockAt(currentLoc.clone().subtract(0, 2.5, 0)).isPassable()
                    && !world.getBlockAt(currentLoc.clone().subtract(0, 3.5, 0)).isPassable())) {
                if (timeouts.getOrDefault(player.getUniqueId(), new Timeout()).isTimedOut()) {
                    if (!player.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
                        Vector newVelocity = player.getVelocity();
                        newVelocity.setY(newVelocity.getY() * .25);
                        player.setVelocity(newVelocity);
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0, true));
                    timeouts.put(player.getUniqueId(), new Timeout(1, Calendar.SECOND));
                }
            }
        }
    }

}
