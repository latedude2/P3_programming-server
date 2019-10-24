
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
    private int clientIndex; //index, which is different for each client created

    private Card[] cards = Main.getCards();

    private String hintWord = "";
    private int hintNumber = 0;

    private int guessedWord;

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

                    // Checking the number of turn and then do stuff
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

                    //sending instructor's word and number of words to everyone
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

    void updateGame() throws IOException {
        for (Card card : cards) {
            objectToClient.writeObject(card);
        }

        outToClient.writeUTF(hintWord);
        outToClient.writeInt(hintNumber);

        outToClient.writeInt(GameState.getTurn()); //sending the number of which turn is it
    }

    void makeInstructorTurn() throws IOException{
        if (GameState.getTurn()%2 == 0){
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

            while (GameState.getTurn() == 2) {
                if (clientIndex == 2) {
                    hintWord = inFromClient.readUTF();
                    System.out.println(hintWord);
                    hintNumber = inFromClient.readInt();

                    System.out.println(hintWord + " " + hintNumber);

                    GameState.setTurn(GameState.getTurn() + 1);
                }
            }
            //sending cards, instructor's word and number to everyone and giving them the turn number
            updateGame();
        }
    }

    void makeGuessTurn() throws IOException{
        if (GameState.getTurn()%2 == 1) {
            //first guesser's turn
            while (GameState.getTurn() == 1) {
                if (clientIndex == 1) {
                    guessedWord = inFromClient.readInt();
                    cards[guessedWord].setPlayed(true);
                    GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
                }
            }
            updateGame();

            //second guesser's turn
            while (GameState.getTurn() == 3) {
                if (clientIndex == 3) {
                    guessedWord = inFromClient.readInt();
                    cards[guessedWord].setPlayed(true);
                    GameState.setTurn(GameState.getTurn() + 1); //increase the turn number to go out of loop
                }
            }
            updateGame();
        }
    }
}