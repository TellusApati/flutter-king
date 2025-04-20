package hackathon.fluttershy.domainlogic;

public class SessionManagerResponse {


    private String text;
    private String tts;
    private boolean endSession;



    public SessionManagerResponse(String text, String tts, boolean endSession) {
        this.text = text;
        this.tts = tts;
        this.endSession = endSession;
    }

    public SessionManagerResponse(String text, String tts) {
        this.text = text;
        this.tts = tts;
        endSession = false;
    }

    public SessionManagerResponse(String text, boolean endSession) {
        this.text = text;
        tts = text;
        this.endSession = endSession;
    }

    public SessionManagerResponse(String text) {
        this.text = text;
        tts = text;
        endSession = false;
    }


    public String getText() {
        return text;
    }

    public String getTTS() {
        return tts;
    }

    public boolean getEndSession() {
        return endSession;
    }
}
