package ca.minecore.elementalarmor.commands;

import ca.minecore.elementalarmor.Main;
import ca.minecore.elementalarmor.util.ArmorManager;
import ca.minecore.elementalarmor.util.CustomArmor;
import ca.minecore.elementalarmor.util.emums.ArmorPiece;
import ca.minecore.elementalarmor.util.emums.ArmorType;
import ca.minecore.elementalarmor.util.emums.Charm;
import ca.minecore.elementalarmor.util.exceptions.InvalidCharmException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveArmor implements CommandExecutor, TabCompleter {

    private Main plugin;

    public GiveArmor(Main plugin) {
        this.plugin = plugin;
        PluginCommand cmd = plugin.getCommand("givearmor");
        if (cmd != null) {
            cmd.setTabCompleter(this);
            cmd.setExecutor(this);
        } else plugin.getLogger().severe("Give Armor Command was null!");
    }

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
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) return false;
                ArmorType type;
                ArmorPiece piece;
                try {
                    type = ArmorType.valueOf(args[1].toUpperCase());
                    piece = ArmorPiece.valueOf(args[2].toUpperCase());
                } catch (IllegalArgumentException e) {
                    return false;
                }
                target.getInventory().addItem(ArmorManager.getFullSet(type).get(piece.getIndex()));
                return true;
            }
            case 4: {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) return false;
                ArmorType type;
                ArmorPiece piece;
                Charm charm;
                try {
                    type = ArmorType.valueOf(args[1].toUpperCase());
                    piece = ArmorPiece.valueOf(args[2].toUpperCase());
                    charm = Charm.valueOf(args[3].toUpperCase());
                } catch (IllegalArgumentException e) {
                    return false;
                }
                CustomArmor armor = ArmorManager.getFullSet(type).get(piece.getIndex());
                try {
                    armor.setCharm(charm);
                } catch (InvalidCharmException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Charm Combination!");
                    return false;
                }
                target.getInventory().addItem(armor);
                return true;
            }
            default: {
                return false;
            }
        }
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
                    if (!type.equals(ArmorType.NONE) && type.name().toLowerCase().startsWith(args[1]))
                        output.add(type.name().toLowerCase());
                break;
            }
            case 3: {
                for (ArmorPiece piece : ArmorPiece.values())
                    if (piece.name().toLowerCase().startsWith(args[2].toLowerCase()))
                        output.add(piece.name().toLowerCase());
                break;
            }
            case 4: {
                ArmorType type;
                try {
                    type = ArmorType.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    break;
                }
                for (Charm charm : Charm.values())
                    if (charm.getType().equals(type) && charm.name().toLowerCase().startsWith(args[3].toLowerCase()))
                        output.add(charm.name().toLowerCase());
                break;
            }
        }
        return output;
    }
}
