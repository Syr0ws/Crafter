package com.github.syr0ws.plugincrafter.message.placeholder.parser;

import com.github.syr0ws.plugincrafter.message.placeholder.Placeholder;

import java.util.Map;

public class StringPlaceholderParser implements PlaceholderParser<String> {

    @Override
    public String parsePlaceholders(String input, Map<Placeholder, String> placeholders) {

        String parsed = input;
        for(Map.Entry<Placeholder, String> entry : placeholders.entrySet()) {
            parsed = parsed.replace(entry.getKey().getName(), entry.getValue());
        }

        return parsed;
    }
}
