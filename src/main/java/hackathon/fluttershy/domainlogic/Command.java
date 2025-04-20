package hackathon.fluttershy.domainlogic;

import java.util.HashMap;

/*
Interprets user's words
 */
public class Command {

    //
    // Нет времени делять по токенам слов из пост запроса.!
    // (мы знаем такую фишку, не реализовали, чтобы другим фору дать.)
    //



    public final static int UNKNOWN = 0;
    public final static int START_OR_ACCEPT = 1;
    public final static int START = 2;
    public final static int ACCEPT = 3;
    public final static int DENY = 4;
    public final static int REPEAT = 5;
    public final static int EXIT = 6;

    private final static HashMap<String, Integer> dictionary = new HashMap<>();

    private static void registerDictionary() {
        dictionary.put("да", START_OR_ACCEPT);
        dictionary.put("давай", START_OR_ACCEPT);
        dictionary.put("давайте", START_OR_ACCEPT);
        dictionary.put("можно", START_OR_ACCEPT);
        dictionary.put("хорошо", START_OR_ACCEPT);

        dictionary.put("старт", START);
        dictionary.put("начать", START);
        dictionary.put("начнем", START);
        dictionary.put("начнём", REPEAT);

        dictionary.put("разрешить", ACCEPT);
        dictionary.put("разрешаю", ACCEPT);
        dictionary.put("так уж и быть", ACCEPT);
        dictionary.put("ладно", ACCEPT);
        dictionary.put("пускай", ACCEPT);

        dictionary.put("нет", DENY);
        dictionary.put("нельзя", DENY);
        dictionary.put("отказ", DENY);
        dictionary.put("выгнать", DENY);

        dictionary.put("повтори", REPEAT);
        dictionary.put("повторить", REPEAT);
        dictionary.put("еще раз", REPEAT);
        dictionary.put("ещё раз", REPEAT);

        dictionary.put("стоп", EXIT);
        dictionary.put("выйти", EXIT);
        dictionary.put("конец", EXIT);
        dictionary.put("окончить", EXIT);

    }

    public static int getCommand(String message) {
        if (dictionary.isEmpty()) {
            registerDictionary();
        }

        Integer command = dictionary.get(message);

        if (command == null) {return UNKNOWN;}

        return command;
    }

}
