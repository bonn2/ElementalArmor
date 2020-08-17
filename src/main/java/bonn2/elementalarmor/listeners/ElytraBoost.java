package bonn2.elementalarmor.listeners;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ElytraBoost implements Listener {

    @EventHandler
    public void onSprint(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingCharm(player, Charm.ELYTRA_BOOST)) {
            if (player.isSprinting() && player.isGliding()) {
                player.setVelocity(player.getLocation().getDirection().multiply(2));
            }
        }
    }
}
