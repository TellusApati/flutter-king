package main.java.hackathon.fluttershy;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class DataAccess {

    private static ArrayList<Citizen> citizens = new ArrayList<>();

    private static int lastDataItemIndex = 0;

    private static void addCitizen(String name, String text, int[] acceptInfluence, int[] denyInfluence) {
        citizens.add(new Citizen(name, text, acceptInfluence, denyInfluence));
        lastDataItemIndex++;
    }

    public static void init() {
        addCitizen("Купец Гален", "Милорд, разрешите ли вы открыть ярмарку под защитой короны?", new int[]{-1, 0, 0, 2}, new int[]{1, 0, -2, 0});
        addCitizen("Монах Варен", "Пожертвуете ли вы на восстановление святынь?", new int[]{0, 2, 0, -2}, new int[]{0, -1, 0, 1});
        addCitizen("Сержант Эдмонд", "", new int[]{2, 0, 0, -2}, new int[]{-1, 0, 0, 1});
        addCitizen("Крестьянка Элиза", "Дадите ли вы зерна голодающим?", new int[]{0, 0, 2, -2}, new int[]{0, 0, -2, 1});
        addCitizen("Посланник епископа Ралмара", "Запретите ли вечерние танцы как греховные?", new int[]{0, 2, -2, 0}, new int[]{0, -2, 2, 0});
    }

    public static int getLastDataItemIndex() {
        return lastDataItemIndex;
    }

    public static Citizen getCitizen(int index) {
        return citizens.get(index);
    }


}

