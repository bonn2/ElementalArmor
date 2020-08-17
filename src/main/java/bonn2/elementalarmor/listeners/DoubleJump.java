package bonn2.elementalarmor.listeners;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DoubleJump implements Listener {

    Main plugin = Main.plugin;
    List<Player> canJump = new ArrayList<>();

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if ((player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
        && ArmorManager.isWearingCharm(player, Charm.JUMPING)) {
            event.setCancelled(true);
            if (!player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0)).isPassable()
            || !player.getWorld().getBlockAt(player.getLocation().subtract(0, 2, 0)).isPassable()) {
                Vector vector = player.getLocation().getDirection().multiply(1).setY(1);
                player.setVelocity(vector);
            }
        }
    }
}
