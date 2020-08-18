package bonn2.elementalarmor.listeners.crafting;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.CustomArmor;
import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import bonn2.elementalarmor.util.exceptions.InvalidCharmException;
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
