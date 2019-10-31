import java.net.InetAddress;

class GameState {

    private static int turn = 0; //keeping track of which turn we are at in the game
    private static int[] clientID = new int[4]; //a list for the clients' ports to message them individually
    private static int idIndex = 0; //keeping track of which client is connecting
    private static Card[] cards = Main.getCards(); //for storing the list of 25 card objects for the game
    private static boolean gameEnded = false; //used to close the server when the game is over

    //checks if the game is over or if the game should keep going
    static void checkGameState(){
        int blueCards = 0; //counting the amount of blue cards that have been guessed
        int redCards = 0; //counting the red cards that have been guessed

        //looping through to check all the cards
        for (int i = 0; i < 25; i++){
            if (cards[i].isPlayed()){ //checking if the card has been guessed
                if (cards[i].getNumber() == 0){ //ending the game if the black card has been chosen
                    gameEnded = true;
                    if (turn - 1 == 1 || turn - 1 == 0){ //checking if which team chose the black card based on turn count
                        System.out.println("Blue team wins");
                    }else {
                        System.out.println("red team wins");
                    }
                } else if (cards[i].getNumber() == 2){ //registering that the chosen card was blue
                    blueCards++;
                } else if (cards[i].getNumber() == 3){ //registering that the chosen cars was red
                    redCards++;
                }
            }
        }

        if (blueCards == 8){ //checking if all blue cards have been guessed
            gameEnded = true;
            System.out.println("Blue team wins");
        } else if (redCards == 9){ //checking if all red cards have been guessed
            gameEnded = true;
            System.out.println("Red team wins");
        }
    }

    public static boolean isGameEnded() {
        return gameEnded;
    }

    static int getTurn() {
        return turn;
    }

    //used to start the next turn
    static void setTurn(int turn) {
        if (turn < 4) //Making sure we dont count turns higher than the amount of players
            GameState.turn = turn;
        else if (turn == 4) //resetting turn count after each round
            GameState.turn = 0;
    }

    //used to get the clients port id for each player
    public static int getClientID(int pos) {
        return clientID[pos];
    }

    //used for adding clients to the list of port IDs
    public static void addClientID(int newID) {
        GameState.clientID[idIndex] = newID;
        //cycling through the positions in the client list
        if(idIndex < 4){
            idIndex++;
        } else if (idIndex == 4){
            idIndex = 0;
        }
    }

    public static Card getCard(int cardNum) {
        return cards[cardNum];
    }

    //used to register that a card has been guessed
    public static void setCard(int cardNum) {
        cards[cardNum].setPlayed(true);
    }
}
