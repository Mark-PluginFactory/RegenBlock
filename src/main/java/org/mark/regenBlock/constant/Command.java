package org.mark.regenBlock.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {
    CMD_REGEN_BLOCK("regenBlock"),
    CMD_REGEN_BLOCK_CREATE("create"),
    CMD_REGEN_BLOCK_NONE("");

    String cmd;

    public static Command getCommand(String cmd) {
        for (Command command : Command.values()) {
            if (command.cmd.equalsIgnoreCase(cmd)) {
                return command;
            }
        }

        return CMD_REGEN_BLOCK_NONE;
    }
}
