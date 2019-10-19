package server;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.readFile();

        Cards[] cards = new Cards[wordGenerator.getWordsToDisplay().length];

        cards = createCardValues(wordGenerator, cards);

        //method for array randomization
        cards = randomizeCardsArray(cards);

        // just for displaying the values of cards
        for (int i = 0; i < cards.length; i++)
            System.out.println(cards[i].getNumber() + " " + cards[i].getName());

        ClientConnect clientConnect = new ClientConnect();
        clientConnect.createClient();
    }

    public static Cards[] randomizeCardsArray(Cards[] array){
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
            cards[i].setName(wordGenerator.getWord(i));
            if (i == 0)
                cards[i].setNumber(0);
            else if (i <= 7)
                cards[i].setNumber(1);
            else if (i <= 15)
                cards[i].setNumber(2);
            else
                cards[i].setNumber(3);
        }
        return cards;
    }

}
