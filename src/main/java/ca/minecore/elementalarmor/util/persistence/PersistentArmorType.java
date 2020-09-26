package ca.minecore.elementalarmor.util.persistence;

import ca.minecore.elementalarmor.util.enums.ArmorType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PersistentArmorType implements PersistentDataType<String, ArmorType> {
    /**
     * Returns the primitive data type of this tag.
     *
     * @return the class
     */
    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    /**
     * Returns the complex object type the primitive value resembles.
     *
     * @return the class type
     */
    @Override
    public @NotNull Class<ArmorType> getComplexType() {
        return ArmorType.class;
    }

    /**
     * Returns the primitive data that resembles the complex object passed to
     * this method.
     *
     * @param complex the complex object instance
     * @param context the context this operation is running in
     * @return the primitive value
     */
    @Override
    public @NotNull String toPrimitive(@NotNull ArmorType complex, @NotNull PersistentDataAdapterContext context) {
        return complex.name();
    }

    /**
     * Creates a complex object based of the passed primitive value
     *
     * @param primitive the primitive value
     * @param context   the context this operation is running in
     * @return the complex object instance
     */
    @Override
    public @NotNull ArmorType fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            return ArmorType.valueOf(primitive);
        } catch (IllegalArgumentException e) {
            return ArmorType.NONE;
        }

    }
}
