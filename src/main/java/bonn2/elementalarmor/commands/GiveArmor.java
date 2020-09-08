package bonn2.elementalarmor.commands;

import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.CustomArmor;
import bonn2.elementalarmor.util.emums.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveArmor implements CommandExecutor, TabCompleter {
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> output = new ArrayList<>();
        switch (args.length) {
            case 0: {
                for (Player player : Bukkit.getOnlinePlayers())
                    output.add(player.getName());
                break;
            }
            case 1: {
                for (Player player : Bukkit.getOnlinePlayers())
                    if (player.getName().startsWith(args[0]))
                        output.add(player.getName());
                break;
            }
            case 2: {
                for (ArmorType type : ArmorType.values())
                    if (!type.equals(ArmorType.NONE))
                        output.add(type.name().toLowerCase());
                break;
            }
        }
        return output;
    }
}
