package main.java.hackathon.fluttershy.domainlogic;

public class Command {

    public final static int UNKNOWN = 0;
    public final static int START = 1;
    public final static int ACCEPT = 2;
    public final static int DENY = 3;
    public final static int REPEAT = 4;

    private final static String[] startAliases = {
            "старт",
            "давай",
            "давайте",
            "да",
            "начать",
            "начнем"
    };

    private final static String[] acceptAliases = {
            "да",
            "можно",
            "разрешить",
            "разрешаю",
            "так уж и быть",
            "ладно",
            "давай",
            "давайте",
            "пускай",
            "ещё раз"
    };

    private final static String[] denyAliases = {
            "нет",
            "нельзя",
            "отказ",
            "выгнать"
    };

    private final static String[] repeatAliases = {
            "повтори",
            "повторить",
            "ещё раз"
    };

    public static int getCommand(String message) {

        // Start
        for (String alias : startAliases) {
            if (message.contains(alias)) {
                return START;
            }
        }

        return UNKNOWN;
    }

    public static int getAcceptOrDeny(String message) {
        // Accept
        for (String alias : acceptAliases) {
            if (message.contains(alias)) {
                return ACCEPT;
            }
        }

        // Deny
        for (String alias : denyAliases) {
            if (message.contains(alias)) {
                return DENY;
            }
        }

        return UNKNOWN;
    }

}
