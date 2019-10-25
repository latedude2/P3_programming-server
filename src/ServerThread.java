
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable{

    DataInputStream inFromClient;
    DataOutputStream outToClient;

    //streams for objects to send
    ObjectOutputStream objectToClient;
    ObjectInputStream objectFromClient;

    boolean connection = true;

    public Socket clientSocket;
    private static int clientIndex; //index, which is different for each client created

    private Card[] cards = Main.getCards();

    private static String hintWord = "";
    private static int hintNumber = 0;

    private static int guessedWord;

    ServerThread(Socket clientSocket, int clientIndex) {
        this.clientSocket = clientSocket;
        this.clientIndex = clientIndex;
    }

    public void run(){
        try {
            // Create data input and output streams
            inFromClient = new DataInputStream(
                    clientSocket.getInputStream());
            outToClient = new DataOutputStream(
                    clientSocket.getOutputStream());

            //streams for objects to send
            objectToClient = new ObjectOutputStream(outToClient);
            objectFromClient = new ObjectInputStream(inFromClient);

            // Display the date, when the client connection was made
            System.out.println("Connected to a client at " + new Date() + '\n');

            outToClient.writeUTF("You connected as number " + clientIndex);
            outToClient.writeInt(clientIndex);

            try {
                while (connection) {
                    //---------------SENT TO EVERYONE--------------------------------
                    updateGame();
                    //----------------------------------------------------------------
                    // Checking the number of turn and then doing stuff
                    //makeInstructorTurn();
                    //updateGame();

                    /*while (GameState.getTurn() == 1){
                        if (clientIndex == 1){
                            guessedWord = inFromClient.readInt();
                            cards[guessedWord].setPlayed(true);
                            GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
                        }
                    }*/
                    //makeGuessTurn();
                    //updateGame();

                    // first instructor stuff
                    while (GameState.getTurn() == 0) {
                        if (clientIndex == 0) {
                            hintWord = inFromClient.readUTF();
                            System.out.println(hintWord);
                            hintNumber = inFromClient.readInt();

                            System.out.println(hintWord + " " + hintNumber);

                            GameState.setTurn(GameState.getTurn() + 1);
                        }
                    }
                    //sending cards, instructor's word and number to everyone and giving them the turn number
                    updateGame();
                    //first guesser's turn
                    while (GameState.getTurn() == 1){
                        if (clientIndex == 1){
                            guessedWord = inFromClient.readInt();
                            cards[guessedWord].setPlayed(true);
                            GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
                        }
                    }
                    updateGame();
                    Thread.sleep(1000000); // temporary thing for easier coding
                    connection = false; //at the end make connection false
                }
            }catch (Exception e){
                System.out.println("From while statement: " + e);
            }
            System.out.println("Connection lost");
        } catch (Exception e) {
            System.out.println("From all place: " + e);
        }
    }

    //for sending the information to every client
    void updateGame() throws IOException {
        //send all the list of cards
        for (Card card : cards)
            objectToClient.writeObject(card);
        //send the hint word (if there's no hint word yet, it is made to be "")
        outToClient.writeUTF(hintWord);
        //send the number of hint words (if there's no number, it is made to be 0)
        outToClient.writeInt(hintNumber);
        //sending the number of which turn is it
        outToClient.writeInt(GameState.getTurn());
    }

    //to get the info from the instructors
    void makeInstructorTurn() throws IOException{
        // first instructor stuff
        while (GameState.getTurn() == 0) { //while turn is for 0th instructor
            if (clientIndex == 0) {
                hintWord = inFromClient.readUTF();
                System.out.println(hintWord);
                hintNumber = inFromClient.readInt();

                System.out.println(hintWord + " " + hintNumber);

                GameState.setTurn(GameState.getTurn() + 1);
            }
        }
        //while turn is for 2nd instructor
        while (GameState.getTurn() == 2) {
            if (clientIndex == 2) {
                hintWord = inFromClient.readUTF();
                System.out.println(hintWord);
                hintNumber = inFromClient.readInt();

                System.out.println(hintWord + " " + hintNumber);

                GameState.setTurn(GameState.getTurn() + 1);
            }
        }
    }

    //to get the information from guessers
    void makeGuessTurn() throws IOException{
        //1st guesser's turn
        while (GameState.getTurn() == 1) {
            if (clientIndex == 1) {
                guessedWord = inFromClient.readInt(); //get the integer of the word in the list
                cards[guessedWord].setPlayed(true); //set to the played status
                GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
            }
        }

        // 2nd guesser's turn
        while (GameState.getTurn() == 3) {
            if (clientIndex == 3) {
                guessedWord = inFromClient.readInt();
                cards[guessedWord].setPlayed(true);
                GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
            }
        }
    }
}