package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;

    static char nullSymbol = '\u0000';

    private Game game;

    public GameBoard(Game currentGame ,int _dimetion){
        this.setDimension(_dimetion);
        this.game = currentGame;
        initField();
    }

    private void initField() {
        setBounds(cellSize * dimension, cellSize * dimension, 400,300);
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension,150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        for (int i = 0; i<(dimension * dimension); i++){
            GameButton fieldButton = new GameButton(i,this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField(){
        for (int i = 0; i<(this.dimension*this.dimension); i++){
            gameButtons[i].setText("");

            int x = i / this.dimension;
            int y = i % this.dimension;

            gameField[x][y] = nullSymbol;
        }
    }
    public Game getGame () {return game;}

    boolean isTurnable(int x, int y){
        boolean result = false;
        if (gameField[y][x] == nullSymbol)
            result = true;
        return  result;
    }

    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin(){
        boolean result = false;

        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();
        if (checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)){
            result = true;
        }
        return result;
    }

    private boolean checkWinLines(char playerSymbol){
        boolean cols, rows, result;
        result = false;

        for (int col =0; col < this.dimension; col++){
            cols = true;
            rows = true;
            for (int row = 0; row < this.dimension; row++){
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }
            if (cols || rows){
                result = true;
                break;
            }
            if (result){
                break;
            }
        }
        return result;
    }

    private boolean checkWinDiagonals(char playerSymbol){
        boolean result = false;
        boolean diag = true;
        boolean diag2 = true;
        for (int row = 0; row<this.dimension;row++){
            diag &= (gameField[row][row] == playerSymbol);
            diag2 &= (gameField[this.dimension - row - 1][row] == playerSymbol);
        }
        if (diag || diag2){
            result = true;
        }
        return result;
    }

    boolean isFull(){
        boolean result = true;
        for (int i = 0; i<(this.dimension*this.dimension); i++) {
            int x = i / this.dimension;
            int y = i % this.dimension;
            if (gameField[y][x] == nullSymbol) {
                result = false;
                break;
            }
        }
        return result;
    }

    public GameButton getButton(int buttonIndex){
        return gameButtons[buttonIndex];
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getBoardDimension(){
        return dimension;
    }

    public char[][] getGameField(){
        return gameField;
    }
}
