package bonn2.elementalarmor.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Counter {

    int max, current, displaySize;
    String name;

    /**
     * Creates a new counter with starting value 0
     * @param max The maximum value the counter can hold
     */
    public Counter(int max) {
        this.max = max;
        this.current = 0;
        name = "";
        displaySize = max / 4;
    }

    /**
     * Creates a new counter with a preset value
     * @param max The maximum value the counter can hold
     * @param starting The starting value of the counter
     */
    public Counter(int max, int starting) {
        this.max = max;
        this.current = starting;
        if (current < 0) current = 0;
        this.name = "";
        this.displaySize = max / 4;
    }

    /**
     * Creates a new counter with a name and preset value
     * @param max The maximum value the counter can hold
     * @param starting The starting value of the counter
     * @param name The display name of the counter
     */
    public Counter(int max, int starting, String name) {
        this.max = max;
        this.current = starting;
        if (current < 0) current = 0;
        this.name = name;
        this.displaySize = max / 4;
    }

    /**
     * Creates a new counter with a preset display size
     * @param max The maximum value the counter can hold
     * @param starting The starting value of the counter
     * @param displaySize The number of '|' the counter displays
     */
    public Counter(int max, int starting, int displaySize) {
        this.max = max;
        this.current = starting;
        if (current < 0) current = 0;
        this.name = "";
        this.displaySize = displaySize;
    }

    /**
     * Creates a new counter with a starting value name and display size
     * @param max The maximum value the counter can hold
     * @param starting The starting value of the counter
     * @param name The display name of the counter
     * @param displaySize The number of '|' the counter displays
     */
    public Counter(int max, int starting, String name, int displaySize) {
        this.max = max;
        this.current = starting;
        if (current < 0) current = 0;
        this.name = name;
        this.displaySize = displaySize;
    }

    /**
     * Safely increment the counter with bounds-checking
     * @param num The amount to increment the counter
     */
    public void increment(int num) {
        current += num;
        if (current < 0) current = 0;
        if (current > max) current = max;
    }

    /**
     * @return If the counter is full
     */
    public boolean isFull() {
        return current == max;
    }

    /**
     * @return If the counter is empty
     */
    public boolean isEmpty() {
        return current < 1;
    }

    /**
     * @return The current value of the counter
     */
    public int getCurrent() {
        return current;
    }

    /**
     * @return The max value of the counter
     */
    public int getMax() {
        return max;
    }

    /**
     * Draw the counter to a player action bar
     * @param player The player to draw the action bar to
     */
    public void draw(Player player) {
        double percentFull = ((double) current) / ((double) max);
        StringBuilder message = new StringBuilder();
        if (!name.equals("")) message.append(name).append("&7 A\u00BB ");
        message.append("&8[ ");
        for (int i = 0; i < displaySize; i++) {
            if (((double) i / (double) displaySize * 100) < (int) (percentFull * 100)) message.append("&a");
            else message.append("&c");
            message.append('|');
        }
        message.append(" &8]");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.colorize(message.toString())));
    }
}
