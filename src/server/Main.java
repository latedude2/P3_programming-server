package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started at " + new Date() + '\n');
            //Accept clients while running
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }

                new Thread(
                        new ServerThread(
                                clientSocket, "Multithreaded Server")
                ).start();
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }


}
