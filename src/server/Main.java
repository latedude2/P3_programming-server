package server;

public class Main {
    public static void main(String[] args) {

        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.readFile();
      
        ClientConnect clientConnect = new ClientConnect();
        clientConnect.createClient();

    }
}
