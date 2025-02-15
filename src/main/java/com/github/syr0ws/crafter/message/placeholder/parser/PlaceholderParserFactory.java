package com.github.syr0ws.crafter.message.placeholder.parser;

import com.github.syr0ws.crafter.component.EasyTextComponent;
import com.github.syr0ws.crafter.util.Validate;

public class PlaceholderParserFactory {

    @SuppressWarnings("unchecked")
    public static <T> PlaceholderParser<T> getParser(Class<T> type) {
        Validate.notNull(type, "type cannot be null");

        if(type == String.class) {
            return (PlaceholderParser<T>) new StringPlaceholderParser();
        } else if(type == EasyTextComponent.class) {
            return (PlaceholderParser<T>) new EasyTextComponentPlaceholderParser();
        }

        throw new IllegalArgumentException(String.format("No placeholder parser found for type %s", type.getName()));
    }
}
