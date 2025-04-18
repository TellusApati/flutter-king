package main.java.hackathon.fluttershy.domainlogic;

import java.util.HashMap;

/*
Processes all sessions and requests from users
 */
public class SessionManager {

    private static HashMap<String, Session> loadedSessions = new HashMap<>();

    /*
    Loads a session by sessionId
    (Additional method for when the database access will be finished)
     */
    private static Session getSession(String sessionId) {
        return loadedSessions.get(sessionId);
    }

    /*
    Called when a user communicates
     */
    public static String userRequest(String message, String sessionId) {
        Session session = getSession(sessionId);
        int command;

        if (session == null || session.hasLost()) {
            command = Command.getCommand(message);
            if (command == Command.START) {
                session = new Session();
                loadedSessions.put(sessionId, session);
                return session.getSessionResponse();
            }
            return "Извините, я не расслышала. Может вы имели ввиду \"начнем\"?";
        }

        command = Command.getAcceptOrDeny(message);

        if (command == Command.REPEAT) {
            return session.getSessionResponse();
        }
        if (command == Command.UNKNOWN) {
            return "Извините, я не расслышала. Попробуйте сказать \nда\n или \nнет\n. Или попросите повторить";
        }
        if (command == Command.ACCEPT) {
            session.acceptCitizenRequest();
        }
        if (command == Command.DENY) {
            session.denyCitizenRequest();
        }


        return session.getSessionResponse();
    }


}
