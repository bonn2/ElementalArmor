package bonn2.elementalarmor.listeners.fire;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.ChatUtil;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class Explosion implements Listener {

    Map<UUID, Long> quickCrouch = new HashMap<>();
    Map<UUID, Long> timeouts = new HashMap<>();

    List<UUID> noDamage = new ArrayList<>();

    private Main plugin;

    public Explosion(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        // only the crouch not the uncrouch
        if (!event.isSneaking()) return;

        // easier variable names
        Long execTime = System.currentTimeMillis();
        Player player = event.getPlayer();
        UUID id = event.getPlayer().getUniqueId();

        // do everthing here cause they have the charm on!
        if (ArmorManager.isWearingCharm(player, Charm.EXPLOSION)) {
            // player not in the one crouch list, so add them and stop
            if (!quickCrouch.containsKey(player.getUniqueId())) {
                quickCrouch.put(id, execTime);
                return;
            }
            // they waited more than a second, so they can't use the quick jump
            if (execTime - quickCrouch.get(id) >= 1000) {
                quickCrouch.remove(id);
                // we also simulate it as if its their first time again :)
                quickCrouch.put(id, execTime);
                return;
            }
            // 5 second cooldown
            if (execTime - timeouts.getOrDefault(id, -1L) <= 5000) {
                player.sendMessage(ChatUtil.colorize("<#B22222>You can only explode every 5 seconds!"));
                quickCrouch.remove(id);
                return;
            } else {
                quickCrouch.remove(id);
                timeouts.remove(id);
            }

            // create the explosion :)
            noDamage.add(id);
            event.getPlayer().getWorld().createExplosion(event.getPlayer().getLocation(), 1, false, false);
            quickCrouch.remove(id);
            timeouts.put(id, execTime);
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> noDamage.remove(id), 40);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && noDamage.contains(event.getEntity().getUniqueId())) {
            double addHealth = event.getDamage() + player.getHealth();
            if (addHealth > 20.0) addHealth = 20.0;
            player.setHealth(addHealth);
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && noDamage.contains(event.getEntity().getUniqueId())) {
            double addHealth = event.getDamage() + player.getHealth();
            if (addHealth > 20.0) addHealth = 20.0;
            player.setHealth(addHealth);
        }

    }

}
