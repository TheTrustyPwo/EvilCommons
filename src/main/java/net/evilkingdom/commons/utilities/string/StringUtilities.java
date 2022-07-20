package net.evilkingdom.commons.utilities.string;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtilities {

    /**
     * Allows you to colorize a string.
     *
     * @param string ~ The string that needs to be colorized.
     * @return The colorized string.
     */
    public static String colorize(String string) {
        final Pattern pattern = Pattern.compile("&#(\\w{5}[\\da-f])");
        final Matcher matcher = pattern.matcher(string);
        final StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    /**
     * Allows you to colorize a list of string.
     *
     * @param strings ~ The list of strings that needs to be colorized.
     * @return The list of colorized strings.
     */
    public static ArrayList<String> colorize(final ArrayList<String> strings) {
        return strings.stream()
                .map(StringUtilities::colorize)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Allows you to validate if a string contains any of the listed words in it.
     * It will automatically detect replacements of numbers and other characters.
     *
     * @param string ~ The string that needs to be validated.
     * @return If the string contains any of the listed words in it.
     */
    public static boolean contains(String string, final ArrayList<String> words) {
        string = string.replace("1", "i")
                .replace("!", "i")
                .replace("3", "e")
                .replace("4", "a")
                .replace("@", "a")
                .replace("5", "s")
                .replace("7", "t")
                .replace("0", "o")
                .replace("9", "g")
                .toLowerCase().replaceAll("[^a-zA-Z]", "");
        for (int start = 0; start < string.length(); start++) {
            for (int offset = 1; offset < string.length() + 1 - start; offset++) {
                final String wordToValidate = string.substring(start, start + offset);
                if (words.contains(wordToValidate)) {
                    return true;
                }
            }
        }
        return false;
    }
}
