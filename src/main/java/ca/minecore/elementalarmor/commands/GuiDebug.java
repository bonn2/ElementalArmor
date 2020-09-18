package ca.minecore.elementalarmor.commands;

import ca.minecore.elementalarmor.Main;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuiDebug implements CommandExecutor, @Nullable TabCompleter {

    private final Main plugin;

    public GuiDebug(Main plugin) {
        this.plugin = plugin;
        PluginCommand cmd = plugin.getCommand("guidebug");
        if (cmd != null) {
            cmd.setExecutor(this);
            cmd.setTabCompleter(this);
        } else plugin.getLogger().severe("GUIDebug Command Was Null!");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("enigma.debug.gui") || !(sender instanceof Player)) return false;

        Player player = (Player) sender;
        plugin.getInventoryManager().getHomePage().getGui(player).open(player);

        return false;
    }

    // TODO
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
