package bonn2.elementalarmor.listeners.air;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.emums.ArmorType;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static bonn2.elementalarmor.Main.plugin;

public class DoubleJump implements Listener {

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if ((!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR))
        && ArmorManager.isWearingFullSet(player, ArmorType.AIR)) {
            event.setCancelled(true);
            if (player.isGliding()) {
                player.setGliding(true);
                return;
            }
            if (!player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0)).isPassable()
            || !player.getWorld().getBlockAt(player.getLocation().subtract(0, 2, 0)).isPassable()) {
                Vector vector = player.getLocation().getDirection().multiply(1).setY(1);
                player.setVelocity(vector);
            }
        }
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
        boolean oldIsAir = ArmorManager.getType(event.getOldArmorPiece()).equals(ArmorType.AIR);
        Player player = event.getPlayer();
        new BukkitRunnable() {

            @Override
            public void run() {
                if (ArmorManager.isWearingFullSet(player, ArmorType.AIR)) {
                    player.setAllowFlight(true);
                } else if (oldIsAir && (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR))) {
                    player.setAllowFlight(false);
                }
            }

        }.runTaskLater(plugin, 1);
    }

    @EventHandler
    public void gamemodeChangeEvent(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingFullSet(player, ArmorType.AIR)
        && (event.getNewGameMode().equals(GameMode.SURVIVAL) || event.getNewGameMode().equals(GameMode.ADVENTURE))) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setAllowFlight(true);
                }
            }.runTaskLater(plugin, 1);
        }
    }
}
