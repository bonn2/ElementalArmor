package bonn2.elementalarmor.commands;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.CustomArmor;
import bonn2.elementalarmor.util.emums.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveArmor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        switch (args.length) {
            case 2: {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) return false;
                ArmorType type;
                try {
                    type = ArmorType.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    return false;
                }
                for (CustomArmor item : ArmorManager.getFullSet(type))
                    target.getInventory().addItem(item);
                sender.sendMessage("Successfully gave " + target.getDisplayName() + " the " + type.getFormattedName() + ChatColor.WHITE + " armor set.");
                return true;
            }
            case 3: {
                break;
            }
            default: {
                return false;
            }
        }
        return false;
    }
}
