class GameState {

    private static int turn = 0;

    static int getTurn() {
        return turn;
    }

    static void setTurn(int turn) {
        if (turn + 1 < 4) //if next turn would be for client, who's index is less then 4
            GameState.turn = turn;
        else if (turn + 1 == 4) // if next turn would be for client with index 4. it turns to 0 instead
            GameState.turn = 0;
    }
}
