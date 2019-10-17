package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordGenerator{
    private String[] wordList = new String[969];
    private String[] wordsToDisplay = new String[25];


    public void readFile(){
        try{
            String fileName = "src/server/data/wordsList.txt"; //path of the text file with words
            int wordAmount = 0; // Used to fill the words in the list
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null){
                wordList[wordAmount] = line;
                wordAmount++;
                line = bufferedReader.readLine();
            }
            for(int i = 0; i < 25; i++){
                wordsToDisplay[i] = wordList[getRandom(968)];
            }
        }
        catch(IOException e){
                e.printStackTrace();
        }

    }

    public int getRandom(int max){
        double rand = Math.random()*max;
        return (int)rand;
    }

}