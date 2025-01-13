package com.github.syr0ws.plugincrafter.message.placeholder.parser;

import com.github.syr0ws.plugincrafter.component.EasyTextComponent;
import com.github.syr0ws.plugincrafter.message.placeholder.Placeholder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;

public class EasyTextComponentPlaceholderParser implements PlaceholderParser<EasyTextComponent> {

    private static final PlaceholderParser<String> STRING_PLACEHOLDER_PARSER = new StringPlaceholderParser();

    @Override
    public EasyTextComponent parsePlaceholders(EasyTextComponent component, Map<Placeholder, String> placeholders) {

        String text = STRING_PLACEHOLDER_PARSER.parsePlaceholders(component.getText(), placeholders);
        component.setText(text);

        if(component.getShowText() != null) {
            String showText = STRING_PLACEHOLDER_PARSER.parsePlaceholders(component.getShowText(), placeholders);
            component.setShowText(showText);
        }

        if(component.getSuggestCommand() != null) {
            String suggestCommand = STRING_PLACEHOLDER_PARSER.parsePlaceholders(component.getSuggestCommand(), placeholders);
            component.setSuggestCommand(suggestCommand);
        }

        if(component.getRunCommand() != null) {
            String runCommand = STRING_PLACEHOLDER_PARSER.parsePlaceholders(component.getRunCommand(), placeholders);
            component.setRunCommand(runCommand);
        }

        if(component.getOpenUrl() != null) {
            String openUrl = STRING_PLACEHOLDER_PARSER.parsePlaceholders(component.getOpenUrl(), placeholders);
            component.setOpenUrl(openUrl);
        }

        return component;
    }
}
