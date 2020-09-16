package bonn2.elementalarmor.listeners.fire;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.ChatUtil;
import bonn2.elementalarmor.util.Counter;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Explosion implements Listener {

    Map<UUID, Long> quickCrouch = new HashMap<>();
    Map<UUID, Long> timeouts = new HashMap<>();

    Map<UUID, Counter> counters = new HashMap<>();

    List<UUID> noDamage = new ArrayList<>();

    private final Main plugin;

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
            long timeRemaining = execTime - timeouts.getOrDefault(id, -1L);
            if (timeRemaining <= 5000) {
                player.sendMessage(ChatUtil.colorize("&cYou can explode again in &e{time} &cseconds!".replace("{time}", String.valueOf(5 - (timeRemaining / 1000)))));
                quickCrouch.remove(id);
                return;
            } else {
                quickCrouch.remove(id);
                timeouts.remove(id);
            }

            Counter counter = new Counter(100, 0, Charm.EXPLOSION.getFormattedName(), 10);
            counter.draw(player);
            Bukkit.getServer().getScheduler().runTaskTimer(plugin, t -> {
                if (!player.isSneaking()) {
                    t.cancel();
                    counters.put(id, counter);
                    unSneak(event);
                }
                counter.increment(1);
                counter.draw(player);
            }, 0, 1);
        }
    }

    private void unSneak(PlayerToggleSneakEvent event) {
        Long execTime = System.currentTimeMillis();
        UUID id = event.getPlayer().getUniqueId();

        Counter c = counters.get(id);
        float level;
        if (c == null) {
            level = 1F;
        } else {
            level = (int) ((double) c.getCurrent() / 20);
        }

        // create the explosion :)
        noDamage.add(id);
        event.getPlayer().getWorld().createExplosion(event.getPlayer().getLocation(), level, false, false);
        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(level));
        quickCrouch.remove(id);
        timeouts.put(id, execTime);
        // give them two seconds to get rid of the damage, then add it
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> noDamage.remove(id), 100);


    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        // only players :P
        if (!(event.getEntity() instanceof Player)) return;

        // block explosion damage cancelled
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && noDamage.contains(event.getEntity().getUniqueId())) {
            event.setDamage(0);
        }

        // fall damage cancelled
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && noDamage.contains(event.getEntity().getUniqueId())) {
            event.setDamage(0);
        }

    }

}
