package server;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.readFile();

        //method for Cards array creation and randomization
        Cards[] cards = randomizeCardsArray(
                createCardValues(wordGenerator, new Cards[wordGenerator.getWordsToDisplay().length]));

        //displaying the values of cards (needed just for visualization while coding)
        for (Cards card : cards) System.out.println(card.getNumber() + " " + card.getName());

        ClientConnect clientConnect = new ClientConnect();
        clientConnect.createClient();
    }

    static Cards[] randomizeCardsArray(Cards[] array){
        Random random = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            Cards temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    static Cards[] createCardValues(WordGenerator wordGenerator, Cards[] cards){
        for (int i = 0; i < wordGenerator.getWordsToDisplay().length; i++){
            cards[i] = new Cards();
            cards[i].setName(wordGenerator.getWord(i)); //sets the name of the card from the respective word in the wordlist
            if (i == 0)
                cards[i].setNumber(0); // first card for death
            else if (i <= 7)
                cards[i].setNumber(1); // 7 cards as neutral
            else if (i <= 15)
                cards[i].setNumber(2); // 8 cards for blue team
            else
                cards[i].setNumber(3); // 9 cards for red team
        }
        return cards;
    }

}
