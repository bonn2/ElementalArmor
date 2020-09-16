package ca.minecore.elementalarmor.listeners.util;

import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.CustomArmor;
import ca.minecore.elementalarmor.util.emums.ArmorType;
import ca.minecore.elementalarmor.util.emums.Charm;
import ca.minecore.elementalarmor.util.exceptions.InvalidCharmException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import java.util.Objects;

public class AddCharm implements Listener {

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) throws InvalidCharmException {
        ArmorType firstItemType = ArmorManager.getType(event.getInventory().getItem(0));
        Charm secondItemCharm = ArmorManager.getCharm(event.getInventory().getItem(1));
        if (firstItemType != ArmorType.NONE
        && secondItemCharm != Charm.NONE) {
            if (secondItemCharm.getType().equals(firstItemType)) {
                CustomArmor armor = new CustomArmor(Objects.requireNonNull(event.getInventory().getItem(0)).clone());
                armor.setCharm(secondItemCharm);
                event.setResult(armor);
                event.getInventory().setRepairCost(15);
            }
        }
    }

}
