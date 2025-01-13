package com.github.syr0ws.plugincrafter.message.placeholder.parser;

import com.github.syr0ws.plugincrafter.message.placeholder.Placeholder;

import java.util.Map;

public interface PlaceholderParser<T> {

    T parsePlaceholders(T input, Map<Placeholder, String> placeholders);
}
