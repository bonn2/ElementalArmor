package bonn2.elementalarmor.listeners.air;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.emums.Charm;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class DoubleJump implements Listener {

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if ((player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
        && ArmorManager.isWearingCharm(player, Charm.JUMPING)) {
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
        boolean oldHasJumping = ArmorManager.hasCharm(event.getOldArmorPiece(), Charm.JUMPING);
        boolean newHasJumping = ArmorManager.hasCharm(event.getNewArmorPiece(), Charm.JUMPING);
        Player player = event.getPlayer();
        if (oldHasJumping && newHasJumping) {
            player.setAllowFlight(true);
        } else if (oldHasJumping && !newHasJumping) {
            player.setAllowFlight(false);
        } else if (!oldHasJumping && newHasJumping) {
            player.setAllowFlight(true);
        }
    }
}
