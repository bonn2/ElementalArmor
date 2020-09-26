package ca.minecore.elementalarmor.commands;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.CustomArmor;
import ca.minecore.elementalarmor.util.enums.ArmorType;
import ca.minecore.elementalarmor.util.enums.Charm;
import ca.minecore.elementalarmor.util.exceptions.InvalidCharmException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetCharm implements CommandExecutor, TabCompleter {

    private Main plugin;

    public SetCharm(Main plugin) {
        this.plugin = plugin;
        // command registering
        PluginCommand cmd = plugin.getCommand("givecharm");
        if (cmd != null) {
            cmd.setTabCompleter(this);
            cmd.setExecutor(this);
        } else plugin.getLogger().severe("Set Charm Command was null!");
    }

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
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    ArmorType type = ArmorManager.getType(item);
                    for (Charm charm : Charm.values())
                        if (!charm.equals(Charm.NONE) && charm.getType().equals(type))
                            output.add(charm.name().toLowerCase());
                }
                break;
            }
        }
        return output;
    }
}
