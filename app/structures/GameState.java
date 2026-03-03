package structures;

import structures.basic.Player;
import structures.basic.Position;
import structures.basic.Unit;

public class GameState {

    public boolean gameInitialised = false;

    public Player humanPlayer;
    public Player aiPlayer;

    public int turn;
    public boolean something = false;
    public Unit humanAvatar;
    public Unit aiAvatar;

    public GameState() {
        humanPlayer = new Player();
        aiPlayer = new Player();
        turn = 1;
    }
    public Position humanAvatarPosition;
    public Position aiAvatarPosition;
}