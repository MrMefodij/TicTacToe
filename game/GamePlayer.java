package game;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayer (boolean isRealPlayer, char _playerSign){
        this.realPlayer = isRealPlayer;
        this.playerSign = _playerSign;
    }

    public boolean isRealPlayer(){return realPlayer;}

    public char getPlayerSign(){ return playerSign;}
}
