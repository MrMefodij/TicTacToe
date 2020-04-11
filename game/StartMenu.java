package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    private int gameBoardSettings = 3;

    public StartMenu(){
        super("StartMenu");
        setLayout(new GridLayout(1,0,2,2));
        setSize(260,70);
        setLocation(300,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel startMenu = new JPanel();
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Game gameInstance = new Game(getGameBoardSettings());
                //gameInstance.setGameDimension(getGameBoardSettings());
                gameInstance.initGame();
                setVisible(false);
            }
        });

        JButton setGameDimension = new JButton("Game Dimension");
        setGameDimension.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(startMenu, "<html><h2> Please set new board size:</h2><p> \n " +
                        "(Minimum is 3, Maximum is 9)");
                setGameBoardSettings(checkCorrectSize(result));
            }
        });

        startMenu.setLayout(new BoxLayout(startMenu,BoxLayout.X_AXIS));
        startMenu.add(newGame);
        startMenu.add(setGameDimension);
        getContentPane().add(startMenu, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setGameBoardSettings(int size){
        gameBoardSettings = size;
    }

    private int getGameBoardSettings(){
        return gameBoardSettings;
    }

    private int checkCorrectSize(String text){
        int size = 3;
        try{
            size = Integer.parseInt(text);
        } catch (NumberFormatException e){
            displayError();
        }
        if (size < 3){
            size = 3;
        } else if (size > 9){
            size = 9;
        }
        return size;
    }

    private void displayError(){
        JFrame errorFrame = new JFrame("Error");
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JOptionPane.showMessageDialog(errorFrame,"<html><h2> Error! </h2><i> Size was incorrect! </i>", "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
