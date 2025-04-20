package hackathon.fluttershy.domainlogic;

public class SessionResponse {

    private String text;
    private String tts;

    public SessionResponse(String text, String tts) {
        this.text = text;
        this.tts = tts;
    }

    public String getText() {
        return text;
    }

    public String getTts() {
        return tts;
    }

    public SessionManagerResponse makeSessionManagerResponse(boolean endSession) {
        return  new SessionManagerResponse(text, tts, endSession);
    }

    public SessionManagerResponse makeSessionManagerResponse() {
        return  new SessionManagerResponse(text, tts);
    }

}
