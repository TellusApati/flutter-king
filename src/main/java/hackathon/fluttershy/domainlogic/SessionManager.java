package hackathon.fluttershy.domainlogic;

import java.util.HashMap;

/*
Processes all sessions and requests from users
 */
public class SessionManager {

    private static final HashMap<String, Session> loadedSessions = new HashMap<>();


    /*
    Called when a user sends POST request

    Interprets user's commands and deals with them
     */
    public static SessionManagerResponse userRequest(String message, String sessionId) {
        // Clear unused sessions
        long currentTime = System.currentTimeMillis();
        for (String key : loadedSessions.keySet()) {
            if (currentTime - loadedSessions.get(key).getLastInteractionTime() >= 1000 * 60 * 60 * 6) {
                loadedSessions.remove(key);
            }
        }

        Session session = loadedSessions.get(sessionId);
        int command = Command.getCommand(message);

        if (command == Command.UNKNOWN) {
            return new SessionManagerResponse("Извините, я не расслышал. Попробуйте сказать \"да\" или \"нет\". Или попросите повторить");
        }

        if (command == Command.EXIT) {
            return new SessionManagerResponse("Останавливаем игру.", true);
        }

        if (session == null || session.hasLost()) {
            if (command == Command.START || command == Command.START_OR_ACCEPT) {
                session = new Session();
                loadedSessions.put(sessionId, session);
                return session.getSessionResponse().makeSessionManagerResponse();
            }
            return new SessionManagerResponse("Извините, я не расслышал. Может вы имели ввиду \"начнем\"?");
        }

        if (command == Command.REPEAT) {
            return session.getSessionResponse().makeSessionManagerResponse();
        }
        if (command == Command.ACCEPT || command == Command.START_OR_ACCEPT) {
            session.makeDecision(Session.Decision.ACCEPT);
        }
        if (command == Command.DENY) {
            session.makeDecision(Session.Decision.DENY);
        }

        return session.getSessionResponse().makeSessionManagerResponse();
    }


}
