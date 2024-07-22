// Name: Vikram Murali 

import java.util.*;
import java.io.*;

// This class make the game ConnectFour where two players take turns to drop colored discs 
// into a 7 columns by 6 rows grid. The goal of the game is for a player to make a horizontal, vertical, 
// or diagonal line of four of their own discs.
public class ConnectFour extends AbstractStrategyGame {
    private final int rows = 6;
    private final int columns = 7;
    private final int[][] board;
    private int currentPlayer;
    private int winner;
    private int movesCount;

    // Constructs a new ConnectFour game initializing an empty board and setting
    // the starting player
    public ConnectFour() {
        this.board = new int[rows][columns]; 
        this.currentPlayer = 1; 
        this.winner = -1; 
        this.movesCount = 0; 
    }

    // Returns a string of instructions on how to play ConnectFour 
    public String instructions() {
        return "Connect Four is played by choosing a column to drop a disc into. "
                + "Try to connect four of your own discs in a line horizontally, vertically, "
                + "or diagonally before your opponent. The columns are numbered 1 to 7.";
    }

    // Generates a visual representation of the game using characters
    // Returns string representation of current state of the game board
    public String toString() {
        String boardString = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (board[row][col] == 0) {
                    boardString += ".";
                } else if (board[row][col] == 1) {
                    boardString += "R";
                } else {
                    boardString += "Y";
                }
                if (col < columns - 1){
                    boardString += " | ";
                } 
            }
            boardString += "\n";
        }
        return boardString;
    }

    // Checks if the game is over. The game is considered over if a player
    // has won or if all cells on the board are filled without any player 
    // winning which is a tie
    // returns true if the game is over either by win or tie, false otherwise
    public boolean isGameOver() {
        if(winner != -1){
            return true;
        }
        return movesCount == rows * columns;
    }

    // Retrieves the winner of the game
    // returns the player number of the winner or -1 if there is no winner yet
    // returns 0 in the event of a tie
    public int getWinner() {
        if(isGameOver() && winner == -1){
            return 0;
        }
        return winner;
    }

    // Determines which player should play next
    // Returns the number of the next player or -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        } else {
            return currentPlayer;
        }
    }

    // Processes the players move from user input
    // Parameters:
    //  input: player's move
    // throws IllegalArgumentException if the chosen column is full or invalid
    public void makeMove(Scanner input) {
        int column = input.nextInt() - 1;
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException();
        }
        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][column] == 0) {
                board[row][column] = currentPlayer;
                movesCount++;
                if (isWinningMove(row, column)) {
                    winner = currentPlayer;
                }
                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }
                return;
            }
        }
        throw new IllegalArgumentException("Column " + (column + 1) + " is full.");
    }

    // Checks if the last move made is a winning move
    // Parameters:
    //  row: the row of the last placed disc 
    //  column: the column of the last placed disc 
    // Returns true if the move completes a line of four discs in a row vertically,
    // horizontally, or diagonally otherwise returns false 
    private boolean isWinningMove(int row, int col) {
        int tokenToCheck = board[row][col];
        if (row <= rows - 4) {
            if (board[row][col] == tokenToCheck && board[row + 1][col] == tokenToCheck &&
                board[row + 2][col] == tokenToCheck && board[row + 3][col] == tokenToCheck) {
                return true;
            }
        }
        for (int c = 0; c < columns - 3; c++) {
            if (board[row][c] == tokenToCheck && board[row][c + 1] == tokenToCheck &&
                board[row][c + 2] == tokenToCheck && board[row][c + 3] == tokenToCheck) {
                return true;
            }
        }
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < columns - 3; c++) {
                if (board[r][c] == tokenToCheck && board[r - 1][c + 1] == tokenToCheck &&
                    board[r - 2][c + 2] == tokenToCheck && board[r - 3][c + 3] == tokenToCheck) {
                    return true;
                }
            }
        }
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < columns - 3; c++) {
                if (board[r][c] == tokenToCheck && board[r + 1][c + 1] == tokenToCheck &&
                    board[r + 2][c + 2] == tokenToCheck && board[r + 3][c + 3] == tokenToCheck) {
                    return true;
                }
            }
        }
        return false;
    }
}