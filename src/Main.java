import java.util.Random;

public class Main {

    public static GameState gameState;
    static Card[] cards;

    public static void main(String[] args) {

        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.readFile();

        //method for Cards array creation and randomization
        cards = randomizeCardsArray(createCardValues(wordGenerator, new Card[wordGenerator.getWordsToDisplay().length]));

        //displaying the values of cards (needed just for visualization while coding)
        for (Card card : cards) System.out.println(card.getNumber() + " " + card.getName());

        ClientConnect clientConnect = new ClientConnect();
        clientConnect.createClient();
    }

    static Card[] randomizeCardsArray(Card[] array){
        Random random = new Random();  // Random number generator

        //composing an array of random words from the word list
        for (int i=0; i<array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            
            Card temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    static Card[] createCardValues(WordGenerator wordGenerator, Card[] cards){
        for (int i = 0; i < wordGenerator.getWordsToDisplay().length; i++){
            cards[i] = new Card();
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

    public static Card[] getCards() {
        return cards;
    }
}
