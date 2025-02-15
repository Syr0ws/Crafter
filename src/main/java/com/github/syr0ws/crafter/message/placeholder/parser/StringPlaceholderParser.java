package com.github.syr0ws.crafter.message.placeholder.parser;

import com.github.syr0ws.crafter.message.placeholder.Placeholder;
import com.github.syr0ws.crafter.util.Validate;

import java.util.Map;

public class StringPlaceholderParser implements PlaceholderParser<String> {

    @Override
    public String parsePlaceholders(String text, Map<Placeholder, String> placeholders) {
        Validate.notNull(text, "text cannot be null");
        Validate.notNull(placeholders, "placeholders cannot be null");

        String parsed = text;
        for(Map.Entry<Placeholder, String> entry : placeholders.entrySet()) {
            parsed = parsed.replace(entry.getKey().getName(), entry.getValue());
        }

        return parsed;
    }
}
