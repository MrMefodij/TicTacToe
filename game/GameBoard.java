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

    public GameBoard(Game currentGame){
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
        for (int i = 0; i<(dimension*dimension); i++){
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbol;
        }
    }
    Game getGame () {return game;}

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

        for (int col =0; col < dimension; col++){
            cols = true;
            rows = true;
            for (int row = 0; row < dimension; row++){
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
        for (int row = 0; row<dimension;row++){
            diag &= (gameField[row][row] == playerSymbol);
            diag2 &= (gameField[dimension - row - 1][row] == playerSymbol);
        }
        if (diag || diag2){
            result = true;
        }
        return result;
    }

    boolean isFull(){
        boolean result = true;
        for (int i = 0; i<(dimension*dimension); i++) {
            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;
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

}
