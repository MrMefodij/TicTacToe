package game;

import java.util.Random;

public class Ai {
    private int x,y;
    public Ai(GameBoard board){
        int[][] cellWight = calculateCellWeight(board);
        int[] bestCell = calculateBestCell(cellWight, board.getBoardDimension());
        y = bestCell[0];
        x = bestCell[1];
    }
    public int getAiTurnX(){
        return x;
    }
    public int getAiTurnY(){
        return y;
    }

    private int[][] calculateCellWeight(GameBoard board){
        int _dimension = board.getBoardDimension();
        int[][] cellWeight = new int[_dimension][_dimension];
        for (int x = 0; x<_dimension; x++){
            for (int y = 0; y <_dimension; y++){
                if (!board.isTurnable(x,y)){
                    cellWeight[y][x] = -1;
                } else {
                    cellWeight[y][x] = calculateWeight(board,x,y);
                }
            }
        }
        printTable(cellWeight);
        return cellWeight;
    }

    private int calculateWeight(GameBoard board, int x, int y){
        int result = -1;
        int horWeight = horizontalWeight(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        int verWeight = verticalWeight(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        int diaWeight = DiagonalWeight(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        int anDiaWeight = AnDiagonalWeight(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        int anPlWin = AnPlayerWin(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        int AiWin = AiCheckWin(board,board.getGame().getCurrentPlayer().getPlayerSign(), x, y);
        result = horWeight + verWeight + diaWeight + anDiaWeight + anPlWin + AiWin;
        return result;
    }

    private int horizontalWeight(GameBoard board, char playerSign, int x, int y){
        int result = 0;
        char[][] gameBoard = board.getGameField();
        //System.out.println(anotherPlayerSign(playerSign));
        for (int row = 0; row < board.getBoardDimension(); row++){
            result+=(gameBoard[row][x] == playerSign) ? 1 : 0;
            if (gameBoard[row][x] == anotherPlayerSign(playerSign)){
                result = 0;
                break;
            }
        }
        return result;
    }

    private int verticalWeight(GameBoard board, char playerSign, int x, int y) {
        int result = 0;
        char[][] gameBoard = board.getGameField();
        for (int col = 0; col < board.getBoardDimension(); col++){
            result+=(gameBoard[y][col] == playerSign) ? 1 : 0;
            if (gameBoard[y][col] == anotherPlayerSign(playerSign)) {
                result = 0;
                break;
            }
        }
        return result;
    }

    private int DiagonalWeight(GameBoard board, char playerSign, int x, int y){
        int result = 0;
        char[][] gameBoard = board.getGameField();
        if (x == y) {
            for (int row =0; row < board.getBoardDimension(); row++) {
                result += (gameBoard[row][row] == playerSign) ? 1 : 0;
                if (gameBoard[row][row] == anotherPlayerSign(playerSign)) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    private int AnDiagonalWeight(GameBoard board, char playerSign, int x, int y){
        int result = 0;
        char[][] gameBoard = board.getGameField();
        if ( y == (board.getBoardDimension() - x - 1) ) {
            for (int row =0; row < board.getBoardDimension(); row++) {
                result += (gameBoard[board.getBoardDimension() - row - 1][row] == playerSign) ? 1 : 0;
                if (gameBoard[board.getBoardDimension() - row - 1][row] == anotherPlayerSign(playerSign)) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    private int AnPlayerWin(GameBoard board, char playerSign, int x, int y){
        int result = 0;
        char anPlSign = anotherPlayerSign(playerSign);
        int _dimention = board.getBoardDimension();
        if (horizontalWeight(board, anPlSign, x ,y) == _dimention-1  || verticalWeight(board, anPlSign, x ,y)== _dimention-1
                || DiagonalWeight(board, anPlSign, x ,y)== _dimention-1 || AnDiagonalWeight(board, anPlSign, x ,y)== _dimention-1 ){
            result = board.getBoardDimension();
        }
        return result;
    }

    private int AiCheckWin(GameBoard board, char playerSign, int x, int y) {
        int result = 0;
        int _dimention = board.getBoardDimension();
        if (horizontalWeight(board, playerSign, x ,y) == _dimention-1  || verticalWeight(board, playerSign, x ,y)== _dimention-1
                || DiagonalWeight(board, playerSign, x ,y)== _dimention-1 || AnDiagonalWeight(board, playerSign, x ,y)== _dimention-1 ){
            result = board.getBoardDimension();
        }
        return result;
    }

    private int[] calculateBestCell(int[][] cellWeight,int size){
        int[] bestCell = {0,0};
        int maxSellWeight=0;

        for (int x = 0; x<size; x++){
            for (int y = 0; y <size; y++) {
                if (cellWeight[y][x] > maxSellWeight){
                    maxSellWeight = cellWeight[y][x];
                }
            }
        }
        bestCell = findRandomMax(cellWeight,maxSellWeight, size);
        System.out.println("Max Sell Weight["+(bestCell[0]+1) +"," + (bestCell[1]+1) +"] - "+ maxSellWeight);
        return bestCell;
    }

    static private int[] findRandomMax(int[][] SellWeight, int maxSellWeight, int size){
        int [] maxSellsAllX = new int[size*size];
        int [] maxSellsAllY = new int[size*size];
        int randomMax[] = new int[2];
        int counter =0;
        for (int x = 0;x < size;x++){
            for(int y = 0; y < size; y++){
                if (SellWeight[y][x] == maxSellWeight){
                    maxSellsAllY[counter] = y;
                    maxSellsAllX[counter] = x;
                    counter++;
                }
            }
        }
        Random rnd = new Random();
        int takeRandom = rnd.nextInt(counter);
        randomMax[0] = maxSellsAllY[takeRandom];
        randomMax[1] = maxSellsAllX[takeRandom];
        return randomMax;
    }

    private void printTable(int[][] gameField){
        System.out.println("Sells weight:");
        for (int i = 0; i<=gameField.length;i++){
            System.out.print(i + "\t");
        }
        System.out.println();

        for (int i = 0; i< gameField.length; i++){
            System.out.print(i+1 + "\t");
            for (int j = 0; j< gameField.length; j++){
                System.out.print(gameField[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private char anotherPlayerSign(char sign){
        return (sign == 'O') ? 'X' : 'O';
    }
}
