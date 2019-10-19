package server;

public class Cards {

    private String name;
    private int number;
    //number values:
    // 0 is death (only 1 card)
    // 1 is neutral (7 cards)
    // 2 is BLUE (8 cards)
    // 3 is RED (9 cards)
    private boolean played = false;

    public String getName() { return name; }
    public int getNumber() { return number; }
    public boolean isPlayed(){ return played;}

    public void setName(String name) { this.name = name; }
    public void setNumber(int number) { this.number = number; }
    public void setPlayed(boolean played) { this.played = played; }
}
