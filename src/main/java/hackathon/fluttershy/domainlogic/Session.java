package hackathon.fluttershy.domainlogic;

import hackathon.fluttershy.Citizen;
import hackathon.fluttershy.CitizenService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Random;

/*
Data of specific user
*/
public class Session {

    public enum Decision {
        ACCEPT,
        DENY
    }

    private static final Random random = new Random(System.currentTimeMillis());

    private int armyBar;
    private int religionBar;
    private int populationBar;
    private int economicsBar;

    private int monthsPassed;
    private int citizensLeft;
    private int currentEvent;

    private final ArrayList<Token> tokens = new ArrayList<>();

    private boolean lost;

    @Getter
    private long lastInteractionTime;

    private Citizen currentCitizen;


    public Session() {
        armyBar = random.nextInt(10, 16);
        religionBar = random.nextInt(10, 16);
        populationBar = random.nextInt(10, 16);
        economicsBar = random.nextInt(10, 16);
        citizensLeft = 5;
        monthsPassed = 0;
        currentEvent = Event.NONE;
        lost = false;

    }

    public void generateNewCitizen() {
        citizensLeft--;
        if (!tokens.isEmpty() && tokens.getFirst().getExpirationDate() <= 0) {
            currentCitizen = CitizenService.citizenService.getByToken(tokens.getFirst().getTextToken());
            tokens.removeFirst();
        } else {
            int randomIndex = random.nextInt((int) CitizenService.citizenService.getCount());
            int id = CitizenService.citizenService.getIdsWithoutToken().get(randomIndex);
            currentCitizen = CitizenService.citizenService.getById(id);
        }
    }

    public SessionResponse getSessionResponse() {

        if (religionBar <= 0) {
            lost = true;
            return new SessionResponse(
                    "Вы были объявлены еретиком и казнены. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Вы были объявлены еретиком и казнены. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (religionBar >= 25) {
            lost = true;
            return new SessionResponse(
                    "Благодаря силе церкви вы были признаны святым, однако власть перешла в руки церкви. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Благодаря силе церкви вы были признаны святым, однако власть перешла в руки церкви. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (armyBar <= 0) {
            lost = true;
            return new SessionResponse(
                    "Из-за слабой армии соседнее королевство захватило ваше царство. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Из-за слабой армии соседнее королевство захватило ваше царство. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (armyBar >= 25) {
            lost = true;
            return new SessionResponse(
                    "Несколько высокопоставленных генералов свергли вас и установили хунту. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Несколько высокопоставленных генералов свергли вас и установили хунту. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (populationBar <= 0) {
            lost = true;
            return new SessionResponse(
                    "Народ поднял восстание. Вас свергли. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Народ поднял восстание. Вас свергли. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (populationBar >= 25) {
            lost = true;
            return new SessionResponse(
                    "Люди вознесли вас. Вы стали символом вечного правления. Игра окончена. " + getMonthsPassedDescription() + "\nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Люди вознесли вас. Вы стали символом вечного правления. Игра окончена. " + getMonthsPassedDescription() + " Попытаетесь снова?"
            );
        }
        if (economicsBar <= 0) {
            lost = true;
            return new SessionResponse(
                    "Казна пуста. Вы были проданы в рабство. Игра окончена. " + getMonthsPassedDescription() + " \nПопытаетесь снова?",
                    "<speaker audio=\"alice-sounds-human-laugh-3.opus\"><speaker audio=\"alice-sounds-game-loss-3.opus\">" +
                            "Казна пуста. Вы были проданы в рабство. Игра окончена. " + getMonthsPassedDescription() + "  Попытаетесь снова?"
            );
        }

        if (currentCitizen == null) {generateNewCitizen();}


        String monthDescription = "";
        if (citizensLeft == 0) {
            citizensLeft = 5;
            monthsPassed++;
            monthDescription = "Наступил новый месяц. " + getMonthsPassedDescription();

            if (random.nextInt(0, (int) Math.ceil(20.0/monthsPassed)) == 0) {
                currentEvent = random.nextInt(Event.MIN_EVENT_VALUE, Event.MAX_EVENT_VALUE);
                monthDescription += "\n" + getEventDescription();
            }

        }



        lastInteractionTime = System.currentTimeMillis();
        String decisionAffects = getDecisionAffects();
        return new SessionResponse(
                monthDescription + "\n\n" + currentCitizen.getName() + "\n\n"
                        + currentCitizen.getText() + "\n\n"
                        + "Решение может повлиять на: " + decisionAffects
                        + "\nУ вас:"
                        + "\nАрмия: " + armyBar
                        + "\nРелигия: " + religionBar
                        + "\nНаселение: " + populationBar
                        + "\nКазна: " + economicsBar,
                "<speaker audio=\"alice-sounds-game-ping-1.opus\">" +
                        monthDescription + "\n\n" + currentCitizen.getName() + "<[small]>"
                        + currentCitizen.getText() + "\n\n"
                        + "Решение может повлиять на: " + decisionAffects
                        + "\nУ вас:"
                        + "\nАрмия: " + armyBar
                        + "\nРелигия: " + religionBar
                        + "\nНаселение: " + populationBar
                        + "\nКазна: " + economicsBar
        );

    }

    private String getMonthsPassedDescription() {
        // Месяц
        // Месяца
        // Месяцев
        if (monthsPassed == 1) {
            return "Прошел " + monthsPassed + " месяц с начала вашего правления";
        }
        if (monthsPassed <= 4 && monthsPassed != 0) {
            return "Прошло " + monthsPassed + " месяца с начала вашего правления";
        }
        return "Прошло " + monthsPassed + " месяцев с начала вашего правления";
    }

    public void makeDecision(Decision decision) {
        for (Token token : tokens) {
            token.updateExpirationDate();
        }

        switch (currentEvent) {
            case Event.WAR -> armyBar -= 1;
            case Event.MOBILIZATION -> armyBar += 1;
            case Event.BAD_HARVEST -> populationBar -= 1;
            case Event.IMMIGRATION -> populationBar += 1;
            case Event.GREAT_CONSTRUCTION -> economicsBar -= 1;
            case Event.GOLD_RUSH -> economicsBar += 1;
            case Event.HOLY_FIGURE_ASSASSINATION -> religionBar -= 1;
            case Event.HOLY_MONTH ->  religionBar += 1;
        }


        int[] influence = new int[4];
        String token;
        if (decision == Decision.ACCEPT) {
            if (currentEvent == Event.MIRRORED_DECISIONS) {
                influence[0] = currentCitizen.getDi_army();
                influence[1] = currentCitizen.getDi_religion();
                influence[2] = currentCitizen.getDi_population();
                influence[3] = currentCitizen.getDi_economics();
                token = currentCitizen.getDeny_token();
            } else {
                influence[0] = currentCitizen.getAi_army();
                influence[1] = currentCitizen.getAi_religion();
                influence[2] = currentCitizen.getAi_population();
                influence[3] = currentCitizen.getAi_economics();
                token = currentCitizen.getAccept_token();
            }
        } else {
            if (currentEvent == Event.MIRRORED_DECISIONS) {
                influence[0] = currentCitizen.getAi_army();
                influence[1] = currentCitizen.getAi_religion();
                influence[2] = currentCitizen.getAi_population();
                influence[3] = currentCitizen.getAi_economics();
                token = currentCitizen.getAccept_token();
            } else {
                influence[0] = currentCitizen.getDi_army();
                influence[1] = currentCitizen.getDi_religion();
                influence[2] = currentCitizen.getDi_population();
                influence[3] = currentCitizen.getDi_economics();
                token = currentCitizen.getDeny_token();
            }
        }

        if (token != null) {
            tokens.add(new Token(token, 5));
        }

        armyBar += influence[0];
        religionBar += influence[1];
        populationBar += influence[2];
        economicsBar += influence[3];

        generateNewCitizen();
    }

    public boolean hasLost() {
        return lost;
    }

    private String getDecisionAffects() {
        int[] acceptInfluence = new int[] {
                currentCitizen.getAi_army(),
                currentCitizen.getAi_religion(),
                currentCitizen.getAi_population(),
                currentCitizen.getAi_economics()
        };
        int[] denyInfluence = new int[] {
                currentCitizen.getDi_army(),
                currentCitizen.getDi_religion(),
                currentCitizen.getDi_population(),
                currentCitizen.getDi_economics()
        };
        String decisionAffects = "";

        int disinformationType = -1;
        if (currentEvent == Event.DISINFORMATION_CAMPAIGN) {
            disinformationType = random.nextInt(0, 8);
        }

        // Army
        boolean applyTrueAffect = (acceptInfluence[0] != 0 || denyInfluence[0] != 0) && disinformationType != 0;
        boolean applyFalseAffect = acceptInfluence[0] == 0 && denyInfluence[0] == 0 && disinformationType == 4;
        if (applyTrueAffect || applyFalseAffect) {
            decisionAffects += "армию";
        }
        // Religion
        applyTrueAffect = (acceptInfluence[1] != 0 || denyInfluence[1] != 0) && disinformationType != 1;
        applyFalseAffect = acceptInfluence[1] == 0 && denyInfluence[1] == 0 && disinformationType == 5;
        if (applyTrueAffect || applyFalseAffect) {
            decisionAffects = applyComma(decisionAffects);
            decisionAffects += "религию";
        }
        // Population
        applyTrueAffect = (acceptInfluence[2] != 0 || denyInfluence[2] != 0) && disinformationType != 2;
        applyFalseAffect = acceptInfluence[0] == 0 && denyInfluence[2] == 0 && disinformationType == 6;
        if (applyTrueAffect || applyFalseAffect) {
            decisionAffects = applyComma(decisionAffects);
            decisionAffects += "население";
        }
        // Economics
        applyTrueAffect = (acceptInfluence[3] != 0 || denyInfluence[3] != 0) && disinformationType != 3;
        applyFalseAffect = acceptInfluence[3] == 0 && denyInfluence[3] == 0 && disinformationType == 7;
        if (applyTrueAffect || applyFalseAffect) {
            decisionAffects = applyComma(decisionAffects);
            decisionAffects += "экономику";
        }

        return decisionAffects;
    }

    private String applyComma(String text) {
        if (!text.isEmpty()) {
            return text + ", ";
        }
        return text;
    }

    private String getEventDescription() {
        return switch (currentEvent) {
            case Event.WAR -> "Соседнее государство объявило вам войну. До конца месяца ваша армия будет снижаться.";
            case Event.MOBILIZATION -> "Внезапное повышение количества разбойников на ващих территориях вызвало мобилизацию. До конца месяца ваша армия будет повышаться.";
            case Event.BAD_HARVEST -> "Этот месяц оказался неуражайным. До конца месяца ваше население будет снижаться.";
            case Event.IMMIGRATION -> "Начался прилив иммигрантов из соседнего государства. До конца месяца ваше население будет повышаться.";
            case Event.GREAT_CONSTRUCTION -> "Ваше государство начало масштабное строительство. До конца месяца ваша казна будет снижаться.";
            case Event.GOLD_RUSH -> "В вашем государстве открыли новые золотые шахты. До конца месяца ваша казна будет повышаться.";
            case Event.HOLY_FIGURE_ASSASSINATION -> "В вашем государстве совершили покушение на церковную фигуру. До конца месяца религия будет понижаться.";
            case Event.HOLY_MONTH -> "Церковь объявила этот месяц святым. До конца месяца религия будет повышаться.";

            case Event.DISINFORMATION_CAMPAIGN -> "Вражеское государство запустило кампанию массовой дезинформации. До конца месяца подсказки влияния могут быть нерпавильными.";
            case Event.MIRRORED_DECISIONS -> "Злой маг проклял вас. До конца месяца все ваши решения будут противоположными";
            default -> "";
        };
    }


}
