package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public ServerThread(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            // Create data input and output streams
            DataInputStream isFromClient = new DataInputStream(
                    this.clientSocket.getInputStream());
            DataOutputStream osToClient = new DataOutputStream(
                    this.clientSocket.getOutputStream());

            // Display the client number
            System.out.println("Connected to a client " + " at " + new Date() + '\n');

            while (true) {
                //Do communication with client here
            }

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}