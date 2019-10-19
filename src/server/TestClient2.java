package server;

import java.net.*;
import java.io.*;

public class TestClient2 {

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 8000);
            // Create data input and output streams
            DataInputStream inFromServer = new DataInputStream(
                    socket.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(
                    socket.getOutputStream());
            ObjectInputStream objectFromServer = new ObjectInputStream(inFromServer);
            ObjectOutputStream objectToServer = new ObjectOutputStream(outToServer);

            System.out.println(inFromServer.readUTF());
        } catch (
                IOException e){}
    }
}