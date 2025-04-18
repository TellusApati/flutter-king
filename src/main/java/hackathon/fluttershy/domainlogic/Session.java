package main.java.hackathon.fluttershy.domainlogic;

import main.java.hackathon.fluttershy.Citizen;
import main.java.hackathon.fluttershy.DataAccess;

import java.util.Random;

/*
Data of specific user
*/
public class Session {

    private static Random random = new Random();

    private int armyBar;
    private int religionBar;
    private int populationBar;
    private int economicsBar;

    private boolean lost;

    private Citizen currentCitizen;


    public Session() {
        armyBar = random.nextInt(10, 16);
        religionBar = random.nextInt(10, 16);
        populationBar = random.nextInt(10, 16);
        economicsBar = random.nextInt(10, 16);
        lost = false;
    }

    public void generateNewCitizen() {
        currentCitizen = DataAccess.getCitizen(random.nextInt(0, DataAccess.getLastDataItemIndex()));
    }

    public String getSessionResponse() {

        if (religionBar <= 0) {
            lost = true;
            return "Вы были объявлены еретиком и казнены. Игра окончена.\nПопытаетесь снова?";
        }
        if (religionBar >= 25) {
            lost = true;
            return "Благодаря силе церкви вы были признаны святым, однако власть перешла в руки церкви. Игра окончена.\nПопытаетесь снова?";
        }
        if (armyBar <= 0) {
            lost = true;
            return "Из-за слабой армии соседнее королевство захватило ваше царство. Игра окончена.\nПопытаетесь снова?";
        }
        if (armyBar >= 25) {
            lost = true;
            return "Несколько высокопоставленных генералов свергли вас и установили хунту. Игра окончена.\nПопытаетесь снова?";
        }
        if (populationBar <= 0) {
            lost = true;
            return "Народ поднял восстание. Вас свергли. Игра окончена.\nПопытаетесь снова?";
        }
        if (populationBar >= 25) {
            lost = true;
            return "Люди вознесли вас. Вы стали символом вечного правления. Игра окончена.\nПопытаетесь снова?";
        }
        if (economicsBar <= 0) {
            lost = true;
            return "Казна пуста. Вы были проданы в рабство. Игра окончена.\nПопытаетесь снова?";
        }

        if (currentCitizen == null) {generateNewCitizen();}

        int[] acceptInfluence = currentCitizen.getAcceptInfluence();
        int[] denyInfluence = currentCitizen.getDenyInfluence();


        String decisionAffects = "";

        // Army
        if (acceptInfluence[0] != 0 || denyInfluence[0] != 0) {
            decisionAffects += "армию";
        }
        // Religion
        if (acceptInfluence[1] != 0 || denyInfluence[1] != 0) {
            if (!decisionAffects.isEmpty()) { decisionAffects += ", "; }
            decisionAffects += "религию";
        }
        // Population
        if (acceptInfluence[2] != 0 || denyInfluence[2] != 0) {
            if (!decisionAffects.isEmpty()) { decisionAffects += ", "; }
            decisionAffects += "население";
        }
        // Economics
        if (acceptInfluence[3] != 0 || denyInfluence[3] != 0) {
            if (!decisionAffects.isEmpty()) { decisionAffects += ", "; }
            decisionAffects += "экономику";
        }


        return
                "\n\n\n" + currentCitizen.getName() + "\n\n"
                + currentCitizen.getText() + "\n\n"
                + "Решение может повлиять на: " + decisionAffects
                + "\nУ вас:"
                + "\nАрмия: " + armyBar
                + "\nРелигия: " + religionBar
                + "\nНаселение: " + populationBar
                + "\nЭкономика: " + economicsBar;
    }

    public void acceptCitizenRequest() {
        int[] acceptInfluence = currentCitizen.getAcceptInfluence();
        armyBar += acceptInfluence[0];
        religionBar += acceptInfluence[1];
        populationBar += acceptInfluence[2];
        economicsBar += acceptInfluence[3];
        generateNewCitizen();
    }

    public void denyCitizenRequest() {
        int[] denyInfluence = currentCitizen.getDenyInfluence();
        armyBar += denyInfluence[0];
        religionBar += denyInfluence[1];
        populationBar += denyInfluence[2];
        economicsBar += denyInfluence[3];
        generateNewCitizen();
    }

    public boolean hasLost() {
        return lost;
    }


}
