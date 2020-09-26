package ca.minecore.elementalarmor.commands;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.CustomArmor;
import ca.minecore.elementalarmor.util.enums.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Bind implements CommandExecutor, TabCompleter {

    private Main plugin;
    public Bind(Main plugin) {
        this.plugin = plugin;
        PluginCommand cmd = plugin.getCommand("bind");
        if (cmd != null) {
            cmd.setTabCompleter(this);
            cmd.setExecutor(this);
        } else plugin.getLogger().severe("Bind Command was null!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) return false;
            ItemStack item = target.getInventory().getItemInMainHand();
            if (ArmorManager.getType(item) != ArmorType.NONE) {
                CustomArmor armor = new CustomArmor(item);
                armor.setSoul(target);
                target.getInventory().setItemInMainHand(armor);
                return true;
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
        }
        return output;
    }
}
