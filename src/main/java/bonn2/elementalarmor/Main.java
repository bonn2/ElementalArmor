package bonn2.elementalarmor;

import bonn2.elementalarmor.commands.GiveArmor;
import bonn2.elementalarmor.commands.GiveArmorTabComplete;
import bonn2.elementalarmor.commands.SetCharm;
import bonn2.elementalarmor.commands.SetCharmTabComplete;
import bonn2.elementalarmor.listeners.DoubleJump;
import bonn2.elementalarmor.listeners.SlowFall;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveConfig(false);

        Objects.requireNonNull(getCommand("givearmor")).setExecutor(new GiveArmor());
        Objects.requireNonNull(getCommand("givearmor")).setTabCompleter(new GiveArmorTabComplete());
        Objects.requireNonNull(getCommand("setcharm")).setExecutor(new SetCharm());
        Objects.requireNonNull(getCommand("setcharm")).setTabCompleter(new SetCharmTabComplete());
        getServer().getPluginManager().registerEvents(new SlowFall(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void saveConfig(boolean replace) {
        File configYML = new File(getDataFolder() + File.separator + "config.yml");
        if (!configYML.exists()) {
            getLogger().warning("No config.yml found, making a new one!");
            saveResource("config.yml", replace);
        }
    }
}
