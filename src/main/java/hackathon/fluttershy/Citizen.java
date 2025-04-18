package main.java.hackathon.fluttershy;

public class Citizen {
    private String name;
    private String text;

    private int[] acceptInfluence = new int[4];
    private int[] denyInfluence = new int[4];

    public Citizen(String name, String text, int[] acceptInfluence, int[] denyInfluence) {
        this.name = name;
        this.text = text;
        this.acceptInfluence = acceptInfluence;
        this.denyInfluence = denyInfluence;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int[] getAcceptInfluence() {
        return acceptInfluence;
    }

    public int[] getDenyInfluence() {
        return denyInfluence;
    }
}
