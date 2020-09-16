package ca.minecore.elementalarmor.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    public static String colorize (String colorizeMe) {
        Pattern pattern = Pattern.compile(
                "<(#[a-f0-9]{6}|aqua|black|blue|dark_(aqua|blue|gray|green|purple|red)|gray|gold|green|light_purple|red|white|yellow)>",
                Pattern.CASE_INSENSITIVE
        );

        String endValue = colorizeMe;
        Matcher matcher = pattern.matcher(endValue);
        while (matcher.find()) {
            try {
                ChatColor color = ChatColor.of(matcher.group().replace("<", "").replace(">", ""));
                if (color != null) endValue = endValue.replace(matcher.group(), color.toString());
            } catch (IllegalArgumentException ignored) {
            }
        }
        return ChatColor.translateAlternateColorCodes('&', endValue);
    }

}
