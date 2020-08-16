package bonn2.elementalarmor.util.persistence;

import bonn2.elementalarmor.util.emums.Charm;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PersistentCharmType implements PersistentDataType<String, Charm> {
    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Charm> getComplexType() {
        return Charm.class;
    }

    @NotNull
    @Override
    public String toPrimitive(@NotNull Charm complex, @NotNull PersistentDataAdapterContext context) {
        return complex.name();
    }

    @NotNull
    @Override
    public Charm fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return Charm.valueOf(primitive);
    }
}
