import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnect {

    Thread[] thread = new Thread[4]; //finite number of threads for each client
    int threadCount = 0; //counts the threads

    void createClient(){
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started at " + new Date() + '\n');

            //Accept clients while running
            while (true) {
                Socket clientSocket = null;
                try {
                    System.out.println("Waiting for the client");
                    clientSocket = serverSocket.accept(); //accepts the client from the socket
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }

                System.out.println("client came");
                GameState.addClientID(clientSocket.getPort()); //adding the port to a list of clients so they can be messaged individually
                thread[threadCount] = new Thread(new ServerThread(clientSocket, threadCount)); //creating a thread for the client
                thread[threadCount].start();
                threadCount++;
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
}
