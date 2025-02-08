package com.github.syr0ws.crafter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utility methods for validating elements.
 */
public class Validate {

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    /**
     * Checks if the given string is a valid UUID.
     *
     * @param str the string to be validated.
     * @return {@code true} if the string is a valid UUID, {@code false} otherwise.
     */
    public static boolean isUUID(String str) {

        if(str == null) {
            return false;
        }

        Matcher matcher = UUID_PATTERN.matcher(str);

        return matcher.matches();
    }
}
