package ca.minecore.elementalarmor;

import ca.minecore.elementalarmor.commands.Bind;
import ca.minecore.elementalarmor.commands.GiveArmor;
import ca.minecore.elementalarmor.commands.GiveCharm;
import ca.minecore.elementalarmor.commands.SetCharm;
import ca.minecore.elementalarmor.listeners.air.DoubleJump;
import ca.minecore.elementalarmor.listeners.air.ElytraBoost;
import ca.minecore.elementalarmor.listeners.air.PushNearby;
import ca.minecore.elementalarmor.listeners.air.SlowFall;
import ca.minecore.elementalarmor.listeners.earth.Telekinesis;
import ca.minecore.elementalarmor.listeners.fire.Explosion;
import ca.minecore.elementalarmor.listeners.fire.FireThorns;
import ca.minecore.elementalarmor.listeners.fire.Fireproof;
import ca.minecore.elementalarmor.listeners.fire.LavaWalking;
import ca.minecore.elementalarmor.listeners.util.AddCharm;
import ca.minecore.elementalarmor.listeners.util.SoulBinding;
import ca.minecore.elementalarmor.listeners.water.FastSwim;
import ca.minecore.elementalarmor.util.elements.fire.FrozenLava;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static YamlConfiguration data;

    static {
        ConfigurationSerialization.registerClass(FrozenLava.class, "FrozenLava");
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveConfig(false);
        loadSavedFrozenLava();

        // register commands & liseners
        registerCommands();
        registerListeners();

        startRepeatingTasks();
    }

    @Override
    public void onDisable() {
        // Save all FrozenLava locations to file
        List<FrozenLava> toSave = new ArrayList<>();
        for (Location key : LavaWalking.frozenLavaMap.keySet()) {
            if (LavaWalking.frozenLavaMap.get(key).getState() > 0)
                toSave.add(LavaWalking.frozenLavaMap.get(key));
        }
        data.set("frozenLava", toSave);
        File frozenLavaFile = new File(getDataFolder() + File.separator + "data.yml");
        try {
            data.save(frozenLavaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(boolean replace) {
        File configYML = new File(getDataFolder() + File.separator + "config.yml");
        if (!configYML.exists()) {
            getLogger().warning("No config.yml found, making a new one!");
            saveResource("config.yml", replace);
        }
    }

    public YamlConfiguration getOrCreateConfig(String name) {
        File file = new File(getDataFolder() + File.separator + name + ".yml");
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void loadSavedFrozenLava() {
        data = getOrCreateConfig("data");
        List<FrozenLava> loaded = (List<FrozenLava>) data.get("frozenLava");
        if (loaded == null) return;
        for (FrozenLava lava : loaded)
            LavaWalking.frozenLavaMap.put(lava.getLocation(), lava);
    }

    private void startRepeatingTasks() {
        LavaWalking.startRepeatingTask();
    }

    // register all the plugins commands
    // each constructor of the following classes registers
    private void registerCommands() {
        new GiveArmor(this);
        new SetCharm(this);
        new GiveCharm(this);
        new Bind(this);
    }

    // regiter the listeners of the plugin
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new AddCharm(), this);
        getServer().getPluginManager().registerEvents(new SoulBinding(), this);

        // air
        getServer().getPluginManager().registerEvents(new SlowFall(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
        getServer().getPluginManager().registerEvents(new ElytraBoost(), this);
        getServer().getPluginManager().registerEvents(new PushNearby(), this);
        // fire
        getServer().getPluginManager().registerEvents(new Fireproof(), this);
        getServer().getPluginManager().registerEvents(new LavaWalking(), this);
        getServer().getPluginManager().registerEvents(new FireThorns(), this);
        getServer().getPluginManager().registerEvents(new Explosion(this), this);
        // earth
        getServer().getPluginManager().registerEvents(new Telekinesis(), this);
        // water
        getServer().getPluginManager().registerEvents(new FastSwim(), this);

    }
}
