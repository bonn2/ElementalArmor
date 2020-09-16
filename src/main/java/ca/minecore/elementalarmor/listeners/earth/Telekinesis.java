package ca.minecore.elementalarmor.listeners.earth;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.emums.Charm;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Telekinesis implements Listener {

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {

        if (ArmorManager.isWearingCharm(event.getPlayer(), Charm.TELEKINESIS)) {
            // dont drop items
            event.setDropItems(false);
            // add the items to the players inventory then drop the ones that didn't fit
            event.getPlayer().getInventory().addItem(event.getBlock().getDrops().toArray(new ItemStack[0]))
                    .forEach((integer, itemStack) -> event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), itemStack));
        }

    }

}
