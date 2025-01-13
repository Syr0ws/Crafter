package com.github.syr0ws.plugincrafter.message;

import com.github.syr0ws.plugincrafter.component.EasyTextComponent;
import com.github.syr0ws.plugincrafter.message.placeholder.Placeholder;
import com.github.syr0ws.plugincrafter.message.placeholder.parser.PlaceholderParserFactory;
import com.github.syr0ws.plugincrafter.message.placeholder.parser.PlaceholderParser;
import com.github.syr0ws.plugincrafter.text.TextUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static void sendMessage(Player player, String message) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        player.sendMessage(TextUtil.parseColors(message));
    }

    public static void sendMessage(Player player, String message, Map<Placeholder, String> placeholders) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        if(placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

        PlaceholderParser<String> parser = PlaceholderParserFactory.getParser(String.class);
        String parsed = parser.parsePlaceholders(message, placeholders);

        player.sendMessage(TextUtil.parseColors(parsed));
    }

    public static void sendMessages(Player player, List<String> messages) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(messages == null) {
            throw new IllegalArgumentException("messages cannot be null");
        }

        String[] array = messages.stream()
                .map(TextUtil::parseColors)
                .toArray(String[]::new);

        player.sendMessage(array);
    }

    public static void sendMessages(Player player, List<String> messages, Map<Placeholder, String> placeholders) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(messages == null) {
            throw new IllegalArgumentException("messages cannot be null");
        }

        if(placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

        PlaceholderParser<String> parser = PlaceholderParserFactory.getParser(String.class);

        String[] array = messages.stream()
                .map(message -> parser.parsePlaceholders(message, placeholders))
                .map(TextUtil::parseColors)
                .toArray(String[]::new);

        player.sendMessage(array);
    }

    public static void sendMessage(Player player, ConfigurationSection section, String key) {

        if(section == null) {
            throw new IllegalArgumentException("section cannot be null");
        }

        if(key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be null or empty");
        }

        String message = section.getString(key, "");
        MessageUtil.sendMessage(player, message);
    }

    public static void sendMessage(Player player, ConfigurationSection section, String key, Map<Placeholder, String> placeholders) {

        if(section == null) {
            throw new IllegalArgumentException("section cannot be null");
        }

        if(key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be null or empty");
        }

        String message = section.getString(key, "");
        MessageUtil.sendMessage(player, message, placeholders);
    }

    public static void sendMessage(Player player, EasyTextComponent component) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(component == null) {
            throw new IllegalArgumentException("component cannot be null");
        }

        TextComponent tc = component.toTextComponent();
        player.spigot().sendMessage(tc);
    }

    public static void sendMessage(Player player, EasyTextComponent component, Map<Placeholder, String> placeholders) {

        if(player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if(component == null) {
            throw new IllegalArgumentException("component cannot be null");
        }

        if(placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

        PlaceholderParser<EasyTextComponent> parser = PlaceholderParserFactory.getParser(EasyTextComponent.class);
        EasyTextComponent parsed = parser.parsePlaceholders(component, placeholders);

        TextComponent tc = parsed.toTextComponent();
        player.spigot().sendMessage(tc);
    }
}
