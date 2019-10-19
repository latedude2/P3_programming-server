package server;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.net.*;
import java.io.*;

public class TestClient {

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 8000);
            // Create data input and output streams
            DataInputStream inFromServer = new DataInputStream(
                    socket.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(
                    socket.getOutputStream());

            System.out.println(inFromServer.readUTF());
        } catch (IOException e){}

    }
}
