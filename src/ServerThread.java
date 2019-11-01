
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable{

    DataInputStream inFromClient;
    DataOutputStream outToClient;

    //streams for objects to send
    ObjectOutputStream objectToClient;
    ObjectInputStream objectFromClient;

    boolean connection = true; //checking if the client is still connected
    boolean isFirstTurn = true; //used to differentiate the information send on the first turn

    public Socket clientSocket;
    private static int clientIndex; //index, which is different for each client created

    private static String hintWord = ""; //used for storing the current hint word
    private static int hintNumber = 0; //used for storing the amount of allowed guesses related to the hint

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
                    outToClient.writeInt(GameState.getTurn()); //tels the clients which turn we are at in the game

                    //checking if it is the turn of the client connected to this thread
                   if(clientSocket.getPort() == (GameState.getClientID(GameState.getTurn()))){
                       System.out.println("It's my turn " + clientIndex);
                       updateGame(); //sends information about the current word objects to the client
                       makeTurn(); //receives information from the client about what they did on their turn
                   }
                    Thread.sleep(5000);

                   //closes down the socket if the game is over
                   if (GameState.isGameEnded()){
                       clientSocket.close();
                       connection = false;
                   }

                }
            }catch (Exception e){
                System.out.println("From while statement: " + e);
            }
            System.out.println("Connection lost");
        } catch (Exception e) {
            System.out.println("From all place: " + e);
        }
    }

    //for sending the information to the client
    void updateGame() throws IOException {
        outToClient.writeUTF("ready to update");
        System.out.println("updating game");
        //sending the card objects on the first turn
        if (isFirstTurn) {
            for (int i = 0; i < 25; i++)
                objectToClient.writeObject(GameState.getCard(i));
            isFirstTurn = false;
        } else { //sending a list of booleans to update which cards have been guessed on all subsequent turns
            for (int i = 0; i < 25; i++)
                outToClient.writeBoolean(GameState.getCard(i).isPlayed());
        }


        System.out.println(inFromClient.readUTF());
        //send the hint word (if there's no hint word yet, it is made to be "")
        outToClient.writeUTF(hintWord);
        //send the number of of possible guesses(if there's no number, it is made to be 0)
        outToClient.writeInt(hintNumber);
        System.out.println(inFromClient.readUTF());
    }

    //to get the info from the clients about what they didi on their turn
    void makeTurn() throws IOException{
        //Getting info from an instructor
            if (GameState.getTurn() == 0 || GameState.getTurn() == 2) { //checking if the client is an instructor
                hintWord = inFromClient.readUTF(); //updating the hint word
                System.out.println("The word is " + hintWord);
                hintNumber = inFromClient.readInt(); //updating the guess number

                System.out.println("The hint is " + hintWord + " " + hintNumber);

                GameState.setTurn(GameState.getTurn() + 1); //proceeding to the next turn
            }
        //getting info from a guesser
            else if (GameState.getTurn() == 1 || GameState.getTurn() == 3) { //checking if the client is a guesser
                boolean isTrue = false;
                for (int i = 0; i < 25; i++){ //receiving an updated list of booleans showing which cards have been guessed
                    isTrue = inFromClient.readBoolean();
                    if (isTrue) { //updating the servers card list if the card was chosen
                        GameState.setCard(i);
                    }
                }
                GameState.checkGameState(); //checking if the game should be over or continue

                GameState.setTurn(GameState.getTurn() + 1); //proceeding to the next turn
            }


    }
}