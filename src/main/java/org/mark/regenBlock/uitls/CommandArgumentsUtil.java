package org.mark.regenBlock.uitls;

import io.vavr.control.Validation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static io.vavr.API.*;
import static org.mark.regenBlock.constant.Lang.*;

public class CommandArgumentsUtil {
    public static Validation<String, String> getAt(List<String> list, int index) {
        return list.size() > index ? Valid(list.get(index)) : Invalid(NO_ARGUMENT.formatWithPrefix(index + 1));
    }

    public static Validation<String, Integer> parseInt(String contents) {
        try {
            return Valid(Integer.parseInt(contents));
        } catch (Exception ex) {
            return Invalid(NOT_A_NUMBER.formatWithPrefix(contents));
        }
    }

    public static Validation<String, Boolean> parseBoolean(String contents) {
        return Try(() -> Boolean.parseBoolean(contents)).toValidation(NOT_A_BOOLEAN.formatWithPrefix(contents));
    }

    public static Validation<String, Player> getPlayer(String name) {
        return Option(Bukkit.getPlayer(name))
                .toValidation(OFFLINE_PLAYER.formatWithPrefix(name));
    }

    public static Validation<String, Player> getPlayer(CommandSender sender) {
        return sender instanceof Player
                ? Valid((Player) sender)
                : Invalid(ISNT_PLAYER.formatWithPrefix());
    }
}
