package hackathon.fluttershy.controller;

import hackathon.fluttershy.domainlogic.SessionManager;
import hackathon.fluttershy.domainlogic.SessionManagerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller
{
    @PostMapping
    public Response takeData(@RequestBody Request request) {
        Map<String, Object> response = new HashMap<>();

        Object version = request.version;
        Map<String, Object> session = request.session;
        String originalUtterance = (String) request.request.get("original_utterance");
        String text =
                "Добро пожаловать в навык команды \"Флаттершай\"\n"
                + "Вы - король своего королевства.\n"
                + "Ваша задача - выслушать поселенцев и принять или отклонить их просьбы.\n"
                + "Начнем?";
        String tts = "<speaker audio=\"alice-music-harp-1.opus\"><speaker audio=\"alice-music-gong-1.opus\">" + text;
        boolean endSession = false;

        if (originalUtterance != null && !originalUtterance.isEmpty()) {
            String sessionId = (String) request.session.get("session_id");
            SessionManagerResponse sessionManagerResponse = SessionManager.userRequest(originalUtterance, sessionId);
            text = sessionManagerResponse.getText();
            tts = sessionManagerResponse.getTTS();
            endSession = sessionManagerResponse.getEndSession();
        }

        response.put("text", text);
        response.put("tts", tts);
        response.put("end_session", endSession);

        return new Response(version, session, response);
    }
}

