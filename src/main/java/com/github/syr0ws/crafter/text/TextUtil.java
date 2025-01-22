package com.github.syr0ws.crafter.text;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    private static final char COLOR_CHAR = '&';
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f\\d]{6})");

    /**
     * Parses color codes in a string and translates them into the corresponding Minecraft color codes.
     * Supports both alternate color codes (e.g. '&') and hexadecimal color codes.
     *
     * @param string The input string containing color codes. Must not be null.
     * @return The formatted string with color codes translated to Minecraft-compatible colors.
     */
    public static String parseColors(String string) {

        string = ChatColor.translateAlternateColorCodes(COLOR_CHAR, string);
        string = parseHexColors(string);

        return string;
    }

    /**
     * Parses color codes in a list of strings and translates them into the corresponding Minecraft color codes.
     * Supports both alternate color codes (e.g. '&') and hexadecimal color codes in each string.
     *
     * @param strings A list of strings containing color codes. Must not be null.
     * @return A list of formatted strings with color codes translated to Minecraft-compatible colors.
     */
    public static List<String> parseColors(List<String> strings) {
        return strings.stream().map(TextUtil::parseColors).toList();
    }

    private static String parseHexColors(String message) {

        char colorChar = ChatColor.COLOR_CHAR;

        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder builder = new StringBuilder(message.length() + 4 * 8);

        while (matcher.find()) {

            String group = matcher.group(1);

            matcher.appendReplacement(builder, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(builder).toString();
    }
}
