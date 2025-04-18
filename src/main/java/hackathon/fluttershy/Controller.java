package main.java.hackathon.fluttershy;

import main.java.hackathon.fluttershy.domainlogic.SessionManager;

import java.util.Scanner;

/*
Временный Пользовательский интерфейс для консоли джава
 */
public class Controller {

    /*
    Аналог метода POST запросов
     */
    public static String post(String message, String sessionId) {
        return SessionManager.userRequest(message, sessionId);
    }


    /*
    Точка входа интерфейса
     */
    public static void main(String[] args) {
        DataAccess.init();


        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Добро пожаловать в навык команды \"Флаттершай\"\n"
                + "Вы - король своего королевства.\n"
                + "Ваша задача - выслушать просьбы поселенцев и принять или отказать им.\n"
                + "Начнем?"
        );
        while (true) {
            System.out.println(post(scanner.next(), "tellus"));
        }
    }
}
