package com.github.syr0ws.plugincrafter.message.placeholder.parser;

import com.github.syr0ws.plugincrafter.message.placeholder.Placeholder;

import java.util.Map;

public class StringPlaceholderParser implements PlaceholderParser<String> {

    @Override
    public String parsePlaceholders(String text, Map<Placeholder, String> placeholders) {

        if(text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }

        if(placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

        String parsed = text;
        for(Map.Entry<Placeholder, String> entry : placeholders.entrySet()) {
            parsed = parsed.replace(entry.getKey().getName(), entry.getValue());
        }

        return parsed;
    }
}
