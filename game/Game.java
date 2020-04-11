package game;

import javax.swing.*;

public class Game {
    private GameBoard board;
    private GamePlayer[] gamePlayers = new GamePlayer[2];
    private int playersTurn = 0;

    public Game(int size){
        this.board = new GameBoard(this, size);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    void passTurn(){
        playersTurn = (playersTurn == 0) ? 1:0;
    }

    public GamePlayer getCurrentPlayer() {return gamePlayers[playersTurn]; }
    public void showMessage(String messageText){
        JOptionPane.showMessageDialog(board, messageText);
    }
}
