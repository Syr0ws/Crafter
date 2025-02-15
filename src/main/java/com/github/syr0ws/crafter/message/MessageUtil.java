package com.github.syr0ws.crafter.message;

import com.github.syr0ws.crafter.component.EasyTextComponent;
import com.github.syr0ws.crafter.message.placeholder.Placeholder;
import com.github.syr0ws.crafter.message.placeholder.parser.PlaceholderParserFactory;
import com.github.syr0ws.crafter.message.placeholder.parser.PlaceholderParser;
import com.github.syr0ws.crafter.text.TextUtil;
import com.github.syr0ws.crafter.util.Validate;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class MessageUtil {

    /**
     * Sends a formatted message to a player. The message is parsed to translate color codes.
     *
     * @param player  The player to send the message to. Must not be null.
     * @param message The message to send. Must not be null.
     * @throws IllegalArgumentException If {@code player} or {@code message} is null.
     */
    public static void sendMessage(Player player, String message) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(message, "message cannot be null");

        player.sendMessage(TextUtil.parseColors(message));
    }

    /**
     * Sends a formatted message with placeholders to a player. The message is parsed to translate color codes and replace placeholders.
     *
     * @param player       The player to send the message to. Must not be null.
     * @param message      The message to send. Must not be null.
     * @param placeholders A map of placeholders and their replacement values. Must not be null.
     * @throws IllegalArgumentException If {@code player}, {@code message}, or {@code placeholders} is null.
     */
    public static void sendMessage(Player player, String message, Map<Placeholder, String> placeholders) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(message, "message cannot be null");
        Validate.notNull(placeholders, "placeholders cannot be null");

        PlaceholderParser<String> parser = PlaceholderParserFactory.getParser(String.class);
        String parsed = parser.parsePlaceholders(message, placeholders);

        player.sendMessage(TextUtil.parseColors(parsed));
    }

    /**
     * Sends a list of formatted messages to a player. Each message is parsed to translate color codes.
     *
     * @param player   The player to send the messages to. Must not be null.
     * @param messages The list of messages to send. Must not be null.
     * @throws IllegalArgumentException If {@code player} or {@code messages} is null.
     */
    public static void sendMessages(Player player, List<String> messages) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(messages, "messages cannot be null");

        String[] array = messages.stream()
                .map(TextUtil::parseColors)
                .toArray(String[]::new);

        player.sendMessage(array);
    }

    /**
     * Sends a list of formatted messages with placeholders to a player. Each message is parsed to translate color codes and replace placeholders.
     *
     * @param player       The player to send the messages to. Must not be null.
     * @param messages     The list of messages to send. Must not be null.
     * @param placeholders A map of placeholders and their replacement values. Must not be null.
     * @throws IllegalArgumentException If {@code player}, {@code messages}, or {@code placeholders} is null.
     */
    public static void sendMessages(Player player, List<String> messages, Map<Placeholder, String> placeholders) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(messages, "messages cannot be null");
        Validate.notNull(placeholders, "placeholders cannot be null");

        PlaceholderParser<String> parser = PlaceholderParserFactory.getParser(String.class);

        String[] array = messages.stream()
                .map(message -> parser.parsePlaceholders(message, placeholders))
                .map(TextUtil::parseColors)
                .toArray(String[]::new);

        player.sendMessage(array);
    }

    /**
     * Sends a formatted message from a configuration section to a player. The message is parsed to translate color codes.
     *
     * @param player  The player to send the message to. Must not be null.
     * @param section The configuration section containing the message. Must not be null.
     * @param key     The key of the message in the configuration section. Must not be null or empty.
     * @throws IllegalArgumentException If {@code player}, {@code section}, or {@code key} is null or empty.
     */
    public static void sendMessage(Player player, ConfigurationSection section, String key) {
        Validate.notNull(section, "section cannot be null");
        Validate.notEmpty(key, "key cannot be null or empty");

        String message = section.getString(key, "");
        MessageUtil.sendMessage(player, message);
    }

    /**
     * Sends a formatted message with placeholders from a configuration section to a player. The message is parsed to translate color codes and replace placeholders.
     *
     * @param player       The player to send the message to. Must not be null.
     * @param section      The configuration section containing the message. Must not be null.
     * @param key          The key of the message in the configuration section. Must not be null or empty.
     * @param placeholders A map of placeholders and their replacement values. Must not be null.
     * @throws IllegalArgumentException If {@code player}, {@code section}, {@code key}, or {@code placeholders} is null or empty.
     */
    public static void sendMessage(Player player, ConfigurationSection section, String key, Map<Placeholder, String> placeholders) {
        Validate.notNull(section, "section cannot be null");
        Validate.notEmpty(key, "key cannot be null or empty");

        String message = section.getString(key, "");
        MessageUtil.sendMessage(player, message, placeholders);
    }

    /**
     * Sends a formatted text component to a player.
     *
     * @param player    The player to send the text component to. Must not be null.
     * @param component The text component to send. Must not be null.
     * @throws IllegalArgumentException If {@code player} or {@code component} is null.
     */
    public static void sendMessage(Player player, EasyTextComponent component) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(component, "component cannot be null");

        TextComponent tc = component.toTextComponent();
        player.spigot().sendMessage(tc);
    }

    /**
     * Sends a formatted text component with placeholders to a player. The text component is parsed to replace placeholders.
     *
     * @param player       The player to send the text component to. Must not be null.
     * @param component    The text component to send. Must not be null.
     * @param placeholders A map of placeholders and their replacement values. Must not be null.
     * @throws IllegalArgumentException If {@code player}, {@code component}, or {@code placeholders} is null.
     */
    public static void sendMessage(Player player, EasyTextComponent component, Map<Placeholder, String> placeholders) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(component, "component cannot be null");
        Validate.notNull(placeholders, "placeholders cannot be null");

        PlaceholderParser<EasyTextComponent> parser = PlaceholderParserFactory.getParser(EasyTextComponent.class);
        EasyTextComponent parsed = parser.parsePlaceholders(component, placeholders);

        TextComponent tc = parsed.toTextComponent();
        player.spigot().sendMessage(tc);
    }
}
