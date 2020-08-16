package bonn2.elementalarmor.commands;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.CustomArmor;
import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveCharm implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) return false;
            Charm charm;
            try {
                charm = Charm.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
            target.getInventory().addItem(charm.getItem());
            sender.sendMessage("Successfully gave " + target.getDisplayName() + " the Charm of " + charm.getFormattedName());
            return true;
        }
        return false;
    }
}
