package org.mark.regenBlock.command;

import com.google.inject.Inject;
import io.vavr.API;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.CheckReturnValue;
import org.mark.regenBlock.Plugin;
import org.mark.regenBlock.constant.Lang;
import org.mark.regenBlock.service.UserRegenBlockCoordinateService;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static io.vavr.API.Invalid;
import static org.mark.regenBlock.constant.Command.CMD_REGEN_BLOCK;
import static org.mark.regenBlock.constant.Command.getCommand;
import static org.mark.regenBlock.constant.Lang.SET_REGEN_BLOCK_AREA;
import static org.mark.regenBlock.uitls.CommandArgumentsUtil.getAt;
import static org.mark.regenBlock.uitls.CommandArgumentsUtil.getPlayer;

public class RegenBlockCommand implements CommandExecutor {

    @Inject
    private UserRegenBlockCoordinateService userRegenBlockCoordinateService;

    public RegenBlockCommand() {
        Plugin.setCommand(CMD_REGEN_BLOCK.getCmd(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        parse(sender, Arrays.asList(args))
                .toEither()
                .peekLeft(errors -> errors.forEach(sender::sendMessage))
                .peek(success -> {
                    String result = success.get();
                    if (result != null && !result.isEmpty()) {
                        sender.sendMessage(result);
                    }
                });
        return true;
    }

    public Validation<Seq<String>, Supplier<String>> parse(CommandSender sender, List<String> args) {
        switch (getCommand(getAt(args, 0).getOrElse(""))) {
            case CMD_REGEN_BLOCK_CREATE:
                if (!sender.isOp()) return Invalid(API.Seq(Lang.NO_PERMISSION.formatWithPrefix()));
                return Validation.combine(
                        getPlayer(sender),
                        getAt(args, 1)
                ).ap(this::create);
        }
        return sender.isOp()
                ? Invalid(API.Seq(Lang.REGEN_BLOCK_CMD_USAGE.formatWithPrefix().split("\n")))
                : Invalid(API.Seq());
    }

    @CheckReturnValue
    public Supplier<String> create(Player player, String regenBlockName) {
        return () -> {
            userRegenBlockCoordinateService.create(player, regenBlockName);
            return SET_REGEN_BLOCK_AREA.formatWithPrefix();
        };
    }
}
