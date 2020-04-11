package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int _row, int _cell, GameButton _gButton){
        this.row = _row;
        this.cell = _cell;
        this.button = _gButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)){
            updateByPlayerData(board);
            if (board.isFull()){
                board.getGame().showMessage("Draw!");
                board.emptyField();
            } else {
                updateByAiData(board);
            }
        } else {
            board.getGame().showMessage("Turn was incorrect!");
        }
    }
    private void updateByPlayerData(GameBoard board){
        board.updateGameField(row,cell);

        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("You Win!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    private void updateByAiData(GameBoard board){
        int x,y;
        Ai ai = new Ai(board);
        x = ai.getAiTurnX();
        y = ai.getAiTurnY();

        board.updateGameField(x,y);
        int cellIndex = (board.getBoardDimension() * x) + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Computer WIN!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }


}
