package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnect {

    Thread[] thread = new Thread[4]; //finite number of threads for each client
    int threadCount = 0; //counts the threads

    void createClient(){
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started at " + new Date() + '\n');

            //Accept clients while running
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept(); //accepts the client from the socket
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }

                thread[threadCount] = new Thread(new ServerThread(clientSocket,threadCount));
                thread[threadCount].start();
                threadCount++;
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }

    public int getThreadCount() {
        return threadCount;
    }
}
