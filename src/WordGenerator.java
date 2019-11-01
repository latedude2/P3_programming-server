import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class WordGenerator{

    private String[] wordList = new String[969]; //Array of the words in the text file
    private String[] wordsToDisplay = new String[25]; //Array of the words to send to the client

    void readFile(){
        try{
            
            String fileName = "src/wordsList.txt"; //Path of the text file with words
            int wordAmount = 0; //Used to fill the words in the list

            //Read from text file and save in a string
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            //Fill the array with words
            while(line != null){
                wordList[wordAmount] = line;
                wordAmount++;
                line = bufferedReader.readLine();
            }

            //Create set to avoid word repetition
            Set<String> wordListSet = new HashSet<>();
            Collections.addAll(wordListSet, wordList);
            String[] array = wordListSet.toArray(new String[wordListSet.size()]);
            Set<String> wordsToDisplaySet = new HashSet<>();

            //System.out.println(wordListSet);



            //Create array of words to display from set
            for(int j = 0; j < 25; j++)
            {
                int item = new Random().nextInt(array.length); //Random number from the set's size
                System.out.println(item);
                System.out.println(array[item]);
                wordsToDisplaySet.add(array[item]);
                System.out.println(wordsToDisplaySet);

                wordsToDisplay = wordsToDisplaySet.toArray(new String[wordsToDisplaySet.size()]);


                //System.out.println(wordsToDisplay[j]);
            }
            System.out.println(Arrays.toString(array));
            System.out.println(Arrays.toString(wordsToDisplaySet.toArray(new String[wordsToDisplaySet.size()])));
            System.out.println(Arrays.toString(wordsToDisplay));
            System.out.println(array.length);
            System.out.println(wordsToDisplaySet);

            bufferedReader.close();
        }
        catch(IOException e){
                e.printStackTrace();
        }
    }

    public int getRandom(int max){
        return (int)(Math.random()*max);
    }

    public String[] getWordsToDisplay() {
        return wordsToDisplay;
    }

    public String getWord(int index) {
        return wordsToDisplay[index];
    }
}