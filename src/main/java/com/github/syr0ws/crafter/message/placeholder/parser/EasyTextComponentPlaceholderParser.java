package com.github.syr0ws.crafter.message.placeholder.parser;

import com.github.syr0ws.crafter.component.EasyTextComponent;
import com.github.syr0ws.crafter.message.placeholder.Placeholder;

import java.util.List;
import java.util.Map;

public class EasyTextComponentPlaceholderParser implements PlaceholderParser<EasyTextComponent> {

    private static final PlaceholderParser<String> STRING_PLACEHOLDER_PARSER = new StringPlaceholderParser();

    @Override
    public EasyTextComponent parsePlaceholders(EasyTextComponent component, Map<Placeholder, String> placeholders) {

        if(component == null) {
            throw new IllegalArgumentException("component cannot be null");
        }

        if(placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

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

        List<EasyTextComponent> parsedExtra = component.getExtra().stream()
                .map(extra -> this.parsePlaceholders(extra, placeholders))
                .toList();

        component.setExtra(parsedExtra);

        return component;
    }
}
