package com.github.syr0ws.crafter.message.placeholder.parser;

import com.github.syr0ws.crafter.message.placeholder.Placeholder;

import java.util.Map;

/**
 * Represents a parser that processes placeholders in a given input and replaces them with corresponding values.
 */
public interface PlaceholderParser<T> {

    /**
     * Parses the given input and replaces placeholders with their corresponding values.
     *
     * @param input        The input containing placeholders to be replaced. Must not be null.
     * @param placeholders A map of placeholders and their replacement values. Must not be null.
     * @return The input with placeholders replaced by their corresponding values.
     * @throws IllegalArgumentException If {@code input} or {@code placeholders} is null.
     */
    T parsePlaceholders(T input, Map<Placeholder, String> placeholders);
}
