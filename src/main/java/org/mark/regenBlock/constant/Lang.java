package org.mark.regenBlock.constant;

public enum Lang {
    PREFIX("&6&l[*] &f"),
    REGEN_BLOCK_CMD_USAGE("/regenblock create [name] - 리젠블록을 구역을 지정합니다."),
    SET_REGEN_BLOCK_AREA("리젠 블럭 영역을 지정해주세요"),
    FIRST("첫번째"),
    SECOND("두번째"),
    SET_COORDINATES("%s 좌표를 설정합니다."),
    SET_COORDINATES_COMPETED("%s 리젠 블럭 영억 설정이 완료 되었습니다."),
    COORDINATES("x: %s y: %s z: %s "),
    NO_ARGUMENT("%d 번째 인자를 입력해주세요."),
    NOT_A_NUMBER("%s 는 숫자가 아닙니다."),
    NOT_A_BOOLEAN("%s 는 ."),
    OFFLINE_PLAYER("%s 님은 접속중이지 않습니다!"),
    ISNT_PLAYER("플레이어가 아닙니다."),
    NO_PERMISSION("&c권한이 없습니다."),
    UNKNOWN("%s 는 존재하지 않습니다.");

    private final String message;

    Lang(String message) {
        this.message = message;
    }

    public static String colorize(String message) {
        return message.replace("&", "§");
    }

    public String format(Object... args) {
        return colorize(String.format(message, args));
    }

    public String formatWithPrefix(Object... args) {
        StringBuilder builder = new StringBuilder();
        String[] lines = String.format(message, args).split("\n");
        String prefix = Lang.PREFIX.format();
        builder.append(prefix).append(colorize(lines[0]));
        for (int i = 1; i < lines.length; i++) {
            builder.append('\n').append(prefix).append(lines[i]);
        }
        return builder.toString();
    }
}
