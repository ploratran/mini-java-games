package com.udacity;

import java.util.Arrays;

/**
 * Created by udacity 2016
 *  The Main class containing game logic and backend 2D array
 */
public class Game {

    private char turn; // who's turn is it, 'x' or 'o' ? x always starts
    private boolean twoPlayer; // true if this is a 2 player game, false if AI playing
    private char [][] grid; // a 2D array of chars representing the game grid
    private int freeSpots; // counts the number of empty spots remaining on the board (starts from 9  and counts down)
    private static GameUI gui;

    /**
     * Create a new single player game
     * default # of player is 2
     */
    public Game() {
        newGame(true);
    }

    /**
     * Create a new game by clearing the 2D grid and restarting the freeSpots counter and setting the turn to x
     * @param twoPlayer: true if this is a 2 player game, false if playing against the computer
     */
    public void newGame(boolean twoPlayer){
        //sets a game to one or two player
        this.twoPlayer = twoPlayer;
        // initialize all chars in 3x3 game grid to '-'
        // i is column and j is row:
        grid = new char[3][3];
        //fill all empty slots with -
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                grid[i][j] = '-';
            }
        }
        //start with 9 free spots and decrement by one every time a spot is taken
        freeSpots = 9;
        //x always starts
        turn = 'x';
    }


    /**
     * Gets the char value at that particular position in the grid array
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return the char value at the position (i,j):
     *          'x' if x has played here
     *          'o' if o has played here
     *          '-' if no one has played here
     *          '!' if i or j is out of bounds
     */
    public char gridAt(int i, int j){
        if(i>=3||j>=3||i<0||j<0)
            return '!';
        return grid[i][j];
    }

    /**
     * Places current player's char at position (i,j)
     * Uses the variable turn to decide what char to use
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return boolean: true if play was successful, false if invalid play
     */
    public boolean playAt(int i, int j){
        //check for index boundaries:
        if(i>=3||j>=3||i<0||j<0)
            return false;
        //check if this position is available
        // if current position has no player, bail out if not available:
        if(grid[i][j] != '-'){
            return false; //bail out if not available
        }
        //update grid with new play based on who's turn it is
        grid[i][j] = turn;
        //update free spots
        freeSpots--;
        return true;
    }


    /**
     * Override
     * @return string format for 2D array values
     */
    public String toString(){
        return Arrays.deepToString(this.grid);
    }

    /**
     * Performs the winner check and displays a message if game is over
     * @return true if game is over to start a new game
     */
    public boolean doChecks() {
        //check if there's a winner or tie ?
        String winnerMessage = checkGameWinner(grid);
        if (!winnerMessage.equals("None")) {
            gui.gameOver(winnerMessage);
            newGame(true);
            // return true when game is OVER:
            return true;
        }
        // return false when game is NOT OVER:
        return false;
    }

    /**
     * Allows computer to play in a single player game or switch turns for 2 player game
     */
    public void nextTurn(){
        //check if single player game, then let computer play turn
        if(!twoPlayer){
            if(freeSpots == 0){
                return ; //bail out if no more free spots
            }
            int ai_i, ai_j;
            do {
                //randomly pick a position (ai_i,ai_j) from 0-2
                ai_i = (int) (Math.random() * 3);
                ai_j = (int) (Math.random() * 3);
            } while(grid[ai_i][ai_j] != '-'); //keep trying if this spot was taken
            //update grid with new play, computer is always o
            grid[ai_i][ai_j] = 'o';
            //update free spots
            freeSpots--;
        }
        else{
            //change turns
            if(turn == 'x'){
                turn = 'o';
            }
            else{
                turn = 'x';
            }
        }
        return;
    }


    /**
     * Checks if the game has ended either because a player has won, or if the game has ended as a tie.
     * If game hasn't ended the return string has to be "None",
     * If the game ends as tie, the return string has to be "Tie",
     * If the game ends because there's a winner, it should return "X wins" or "O wins" accordingly
     * @param grid 2D array of characters representing the game board
     * @return String indicating the outcome of the game: "X wins" or "O wins" or "Tie" or "None"
     */
    public String checkGameWinner(char [][]grid) {

        String result = "None";
        int size = 3, empty_space = 0;
        //Student code goes here ...
        // VERTICAL MOVE CHECK
        // declare 2 variables: 'countX' and 'countY'
        // iterate thru cols
        // iterate thru rows
        // if current rows is 'x', increment countX
        // if current rows is 'o', increment countY
        // check if countX or countY equal to 3 => return winner

        for (int rows = 0; rows < size; rows++) {
            int countX = 0, countO = 0;
            for (int cols = 0; cols < size; cols++) {
                if (grid[rows][cols] == 'x') {
                    countX += 1;
                }
                if (grid[rows][cols] == 'o') {
                    countO += 1;
                }
            }
            if (countX == size) {
                return "X wins";
            }
            if (countO == size) {
                return "O wins";
            }
        }

        // HORIZONTAL MOVE
        // iterate thru rows
        // iterate thru cols
        // declare 2 variables: 'countX' and 'countO'
        // check if current position is 'x' => increment countX
        // if current position is 'o' => increment countO
        // if countX or countO equals size => return winner
        for (int rows = 0; rows < size; rows++) {
            int countX = 0, countO = 0;
            for (int cols = 0; cols < size; cols++) {
                if (grid[cols][rows] == 'x') {
                    countX += 1;
                }
                else if (grid[cols][rows] == 'o') {
                    countO += 1;
                } else {
                    empty_space += 1;
                }
            }
            if (countX == size) {
                return "X wins";
            }
            if (countO == size) {
                return "O wins";
            }
        }

        // DIAGONAL:
        // iterate thru rows
        // iterate thru cols
        // declare 2 variables: 'countX' and 'countO'
        // if [rows][cols] is 'x' => increment countX
        // if [rows][cols] is 'o' => increment countO
        int countX1 = 0, countX2 = 0, countO1 = 0, countO2 = 0;
        // if countX or countO equal size => return winner
        for (int rows = 0; rows < size; ++rows) {

            if (grid[rows][rows] == 'x') {
                countX1 += 1;
            }
            if (grid[rows][rows] == 'o') {
                countO1 += 1;
            }

            if (countX1 == size) {
                return "X wins";
            }
            if (countO1 == size) {
                return "O wins";
            }
        }

        // DIAGONAL:
        // iterate thru rows
        // iterate thru cols
        // declare 2 variables: 'countX' and 'countO'
        // if [rows][size - 1 - row] is 'x' => increment countX
        // if [rows][size - 1 - row] is 'o' => increment countO
        for (int rows = 0; rows < size; rows++) {
            if (grid[rows][size - 1 - rows] == 'x') {
                countX2 += 1;
            }
            if (grid[rows][size - 1 - rows] == 'o') {
                countO2 += 1;
            }

            if (countX2 == size) {
                return "X wins";
            }
            if (countO2 == size) {
                 return "O wins";
            }
        }

        if (empty_space == 0) {
            return "Tie";
        }

        return result;
    }

    /**
     * Main function
     * @param args command line arguments
     */
    public static void main(String args[]){
        Game game = new Game();
        gui = new GameUI(game);
    }

}
