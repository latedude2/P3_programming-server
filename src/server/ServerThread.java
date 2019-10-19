package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable{

    protected Socket clientSocket = null;
    private int clientIndex; //index, which is different for each client created

    public ServerThread(Socket clientSocket, int clientIndex) {
        this.clientSocket = clientSocket;
        this.clientIndex = clientIndex;
    }

    public void run() {
        try {
            // Create data input and output streams
            DataInputStream inFromClient = new DataInputStream(
                    this.clientSocket.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(
                    this.clientSocket.getOutputStream());

            //streams for objects to send
            ObjectInputStream objectFromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream objectToClient = new ObjectOutputStream(outToClient);

            // Display the date, when the client connection was made
            System.out.println("Connected to a client at " + new Date() + '\n');

            while (true) {
                //tells the first message to the client, that they're
                // connected and which client are they
                outToClient.writeUTF("You connected as number " + clientIndex);

            }

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}