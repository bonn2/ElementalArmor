package bonn2.elementalarmor;

import bonn2.elementalarmor.commands.*;
import bonn2.elementalarmor.listeners.air.*;
import bonn2.elementalarmor.listeners.util.AddCharm;
import bonn2.elementalarmor.listeners.fire.Fireproof;
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
        Objects.requireNonNull(getCommand("givecharm")).setExecutor(new GiveCharm());
        Objects.requireNonNull(getCommand("givecharm")).setTabCompleter(new GiveCharmTabComplete());
        getServer().getPluginManager().registerEvents(new SlowFall(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
        getServer().getPluginManager().registerEvents(new Fireproof(), this);
        getServer().getPluginManager().registerEvents(new ElytraBoost(), this);
        getServer().getPluginManager().registerEvents(new FallDamage(), this);
        getServer().getPluginManager().registerEvents(new AddCharm(), this);
        getServer().getPluginManager().registerEvents(new PushNearby(), this);
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
