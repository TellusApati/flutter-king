package hackathon.fluttershy.domainlogic;


/*
Class that stores story token and expiration date
 */
public class Token {

    private String textToken;
    private int expirationDate;

    public Token(String textToken, int expirationDate) {
        this.expirationDate = expirationDate;
        this.textToken = textToken;
    }

    public boolean updateExpirationDate() {
        expirationDate--;
        if (expirationDate <= 0) {
            return true;
        }
        return false;
    }

    public int getExpirationDate() {return expirationDate;}

    public String getTextToken() {
        return textToken;
    }


}
