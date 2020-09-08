package bonn2.elementalarmor.commands;

import bonn2.elementalarmor.util.CustomArmor;
import bonn2.elementalarmor.util.emums.Charm;
import bonn2.elementalarmor.util.exceptions.InvalidCharmException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetCharm implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) return false;
            if (target.getInventory().getItemInMainHand().equals(new ItemStack(Material.AIR))) return false;
            Charm charm;
            try {
                charm = Charm.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
            CustomArmor armor = new CustomArmor(target.getInventory().getItemInMainHand());
            try {
                armor.setCharm(charm);
            } catch (InvalidCharmException e) {
                sender.sendMessage(e.getMessage());
                return false;
            }
            target.getInventory().setItemInMainHand(armor);
            return true;
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
                for (Charm charm : Charm.values())
                    if (!charm.equals(Charm.NONE))
                        output.add(charm.name().toLowerCase());
                break;
            }
        }
        return output;
    }
}
