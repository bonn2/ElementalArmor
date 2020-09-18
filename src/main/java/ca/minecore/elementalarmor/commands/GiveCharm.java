package ca.minecore.elementalarmor.commands;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.emums.Charm;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveCharm implements CommandExecutor, TabCompleter {

    private Main plugin;

    public GiveCharm(Main plugin) {
        this.plugin = plugin;
        PluginCommand cmd = plugin.getCommand("givecharm");
        if (cmd != null) {
            cmd.setTabCompleter(this);
            cmd.setExecutor(this);
        } else plugin.getLogger().severe("Give Charm Command was null!");
    }

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
