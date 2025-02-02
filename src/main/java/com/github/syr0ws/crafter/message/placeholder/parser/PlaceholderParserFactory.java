package com.github.syr0ws.crafter.message.placeholder.parser;

import com.github.syr0ws.crafter.component.EasyTextComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlaceholderParserFactory {

    @SuppressWarnings("unchecked")
    public static <T> PlaceholderParser<T> getParser(Class<T> type) {

        if(type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        if(type == String.class) {
            return (PlaceholderParser<T>) new StringPlaceholderParser();
        } else if(type == EasyTextComponent.class) {
            return (PlaceholderParser<T>) new EasyTextComponentPlaceholderParser();
        }

        throw new IllegalArgumentException(String.format("No placeholder parser found for type %s", type.getName()));
    }
}
