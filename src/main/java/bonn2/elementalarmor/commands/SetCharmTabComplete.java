package bonn2.elementalarmor.commands;

import bonn2.elementalarmor.util.emums.ArmorType;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetCharmTabComplete implements TabCompleter {
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
