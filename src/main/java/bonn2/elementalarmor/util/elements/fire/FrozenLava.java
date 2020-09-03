package bonn2.elementalarmor.util.elements.fire;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("FrozenLava")
public class FrozenLava implements ConfigurationSerializable {

    private static final Material[] materials = { Material.CRIMSON_HYPHAE, Material.NETHERRACK };
    private final Location location;
    private int state, age;

    public FrozenLava(Location location, boolean place) {
        this.location = location;
        Block block = location.getBlock();
        state = materials.length - 1;
        if (place)
            block.setType(materials[state]);
    }

    private FrozenLava(Location location, int state, int age, boolean place) {
        this.location = location;
        Block block = location.getBlock();
        this.state = state;
        this.age = age;
        if (place)
            block.setType(materials[state]);
    }

    /**
     * Lowers the state int by one and sets the appropriate block. If value is invalid it sets to lava
     */
    public void decrementState() {
        state--;
        age = 0;
        if (state >= 0 && state < materials.length)
            location.getBlock().setType(materials[state]);
        else
            location.getBlock().setType(Material.LAVA);
    }

    public static Material[] getMaterials() {
        return materials;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getState() {
        return state;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("location", location);
        result.put("state", state);
        result.put("age", age);
        return result;
    }

    public static FrozenLava deserialize(Map<String, Object> args) {
        Location location = null;
        int state = 0, age = 0;
        if (args.containsKey("location"))
            location = (Location) args.get("location");
        if (args.containsKey("state"))
            state = (int) args.get("state");
        if (args.containsKey("age"))
            age = (int) args.get("age");
        return new FrozenLava(location, state, age, false);
    }
}
