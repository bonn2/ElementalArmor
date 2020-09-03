package bonn2.elementalarmor.listeners.fire;

import bonn2.elementalarmor.Main;
import bonn2.elementalarmor.util.ArmorManager;
import bonn2.elementalarmor.util.elements.fire.FrozenLava;
import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class LavaWalking implements Listener {
    public static Map<Location, FrozenLava> frozenLavaMap = new HashMap<>();
    private static Map<Location, FrozenLava> toAdd = new HashMap<>();

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArmorManager.isWearingCharm(player, Charm.LAVA_WALKING)) {
            if (event.getFrom().toVector().equals(event.getTo().toVector())) return;
            if (!player.getWorld().getBlockAt(player.getLocation().clone().subtract(0, 1, 0)).isPassable()) {
                for (Block block : getBlocksIn2DRadius(player.getLocation().clone().subtract(0, 1, 0), 3)) {
                    if (block.getType().equals(Material.LAVA)
                    && ((Levelled) block.getBlockData()).getLevel() == 0) {
                            toAdd.put(block.getLocation(), new FrozenLava(block.getLocation(), true));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreakFrozenLava(BlockBreakEvent event) {
        boolean isValidMaterial = false;
        for (Material material : FrozenLava.getMaterials()) {
            if (event.getBlock().getType().equals(material)) {
                isValidMaterial = true;
                break;
            }
        }
        if (isValidMaterial) {
            List<Location> toRemove = new ArrayList<>();
            for (Location location : frozenLavaMap.keySet()) {
                if (location.equals(event.getBlock().getLocation())) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.LAVA);
                    toRemove.add(location);
                }
            }
            frozenLavaMap.keySet().removeAll(toRemove);
        }
    }

    public static void startRepeatingTask() {
        BukkitScheduler scheduler = Main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {
            List<Location> toRemove = new ArrayList<>();
            for (Location location : toAdd.keySet())
                frozenLavaMap.put(location, toAdd.get(location));
            toAdd = new HashMap<>();
            Random random = new Random();
            for (Location location : frozenLavaMap.keySet()) {
                if (frozenLavaMap.get(location) == null) {
                    toRemove.add(location);
                } else {
                    frozenLavaMap.get(location).setAge(frozenLavaMap.get(location).getAge() + 1);
                    if (frozenLavaMap.get(location).getAge() > 5) {
                        if (random.nextInt(3) == 0) {
                            frozenLavaMap.get(location).decrementState();
                            if (frozenLavaMap.get(location).getState() < 0)
                                toRemove.add(location);
                        }
                    } else if (frozenLavaMap.get(location).getAge() > 10) {
                        frozenLavaMap.get(location).decrementState();
                        if (frozenLavaMap.get(location).getState() < 0)
                            toRemove.add(location);
                    }
                }
            }
            frozenLavaMap.keySet().removeAll(toRemove);
        }, 0L, 5L);
    }

    public List<Block> getBlocksIn2DRadius(Location location, int radius) {
        Location adjustedLocation = location.clone().subtract(radius, 0, radius);
        List<Block> blocks = new ArrayList<>();
        for (int x = 0; x < radius * 2; x++) {
            for (int z = 0; z < radius * 2; z++) {
                blocks.add(adjustedLocation.getWorld().getBlockAt(
                        adjustedLocation.getBlockX() + x,
                        adjustedLocation.getBlockY(),
                        adjustedLocation.getBlockZ() + z));
            }
        }
        return blocks;
    }
}
