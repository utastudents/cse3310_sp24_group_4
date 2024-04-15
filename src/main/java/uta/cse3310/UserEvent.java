package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    public int GameId; // the game ID of game room
    public PlayerType PlayerIdx; // either 1, 2, 3, 4, or none
    public int Button;
    public int playerCount;
    public int numOfPlayers;

    UserEvent() {

    }

    UserEvent(int _GameId, PlayerType _PlayerIdx, int _Button) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
    }
}
