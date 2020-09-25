package ca.minecore.elementalarmor.listeners.earth;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.emums.Charm;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Functionality of the TURN_TO_STONE charm
 * Gives damage-resistance to players standing still and removes knockback
 * @author yoschi */
public class TurnToStone implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        System.out.println("damage");
        if(event.getEntity() instanceof Player){
            System.out.println("isPlayer");
            Player player = (Player) event.getEntity();
            if(ArmorManager.isWearingCharm(player, Charm.TURN_TO_STONE)){
                System.out.println("isWearing");
                Vector v = player.getVelocity();
                System.out.println(v.toString());
                if(v.distance(new Vector(0, -0.0784000015258789, 0)) == 0){
                    System.out.println("isStill");
                    double reductionFactor = 2;         //can be adjusted later when progression is added. A 2 equals 1/2 dmg is taken.
                    double dmg = event.getFinalDamage();
                    event.setCancelled(true);
                    player.setHealth(player.getHealth() - (dmg/reductionFactor));  //simulate damage without damage-events
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1); //make dmg feel real by adding the sound
                }
            }
        }
    }
}
