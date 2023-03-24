import java.awt.event.*;

import javax.swing.*;

public class BejeweledGame extends JFrame implements GameEngine, ActionListener {
    // num of game row
    private int row;
    private JButton[][] buttons;

    // num of game col
    private int col;
    private String userName;

    // log first selected row & col
    private int firstClickRow;
    private int firstClickCol;

    // log second selected rol & col
    private int secondClickRow;
    private int secondClickCol;

    // Constructor that calls createBoard() to create gameboard
    private JButton reset;
    private BejewelBoard boardObj;
    private Player player;

    public BejeweledGame(String name) {
        this.player = new Player(name);
        this.row = 9;
        this.col = 8;
        this.init();
    }

    public void initClicks() {
        this.firstClickRow = -99;
        this.firstClickCol = -99;
        this.secondClickRow = -99;
        this.secondClickCol = -99;
    }

    public void initBoardObj() {
        boardObj = new BejewelBoard(this.row, this.col, this, this.player.getName());
    }

    @Override
    public void createboard() {
        this.setSize(1000, 850);

        //get board
        JPanel gameBoard = boardObj.getBoard();
        this.buttons = boardObj.buttons;

        //set operations
        this.add(gameBoard);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(gameBoard, "Center");
        this.setVisible(true);
    }

    //check if the selected click is adjacent to eachother

    //check if the two clicked mouse location is connected
    public boolean isAdjacent(int firstRow, int firstCol, int secondRow, int secondCol) {
        int rowDiff = Math.abs(secondRow - firstRow);
        int colDiff = Math.abs(secondCol - firstCol);
        return (firstRow == secondRow && colDiff == 1) || (firstCol == secondCol && rowDiff == 1);
    }

    //set first clicked target
    public void setFirstClick(int row, int col) {
        this.firstClickCol = col;
        this.firstClickRow = row;
    }

    //set second clicked target
    //updates the board if the clicked location is adjacent
    public void setSecondClick(int row, int col) {
        this.secondClickCol = col;
        this.secondClickRow = row;
        if (this.isAdjacent(this.firstClickRow, this.firstClickCol, this.secondClickRow, this.secondClickCol)) {
            this.updateBoard();
        } else {
            this.resetClickCursor();
        }
    }

    //resets the first clicked cursor location
    public void resetClickCursor() {
        this.firstClickRow = -99;
        this.firstClickCol = -99;
    }

    public void updateBoard() {
        int[] clicks = {this.firstClickRow, this.firstClickCol, this.secondClickRow,
                this.secondClickCol};
        this.boardObj.updateBoard(clicks);
        this.buttons = this.boardObj.buttons;
        this.resetClickCursor();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton click = (JButton) e.getSource();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (click == buttons[i][j]) {
                    //set first click location
                    if (this.firstClickCol == -99) {
                        this.setFirstClick(i, j);
                    }
                    //set second click location
                    else this.setSecondClick(i, j);
                }
            }
        }
    }

    public Player getCurrPlayer() {
        this.player.setScore(this.boardObj.getScore());
        return this.player.getPlayer();
    }


    @Override
    public void init() {
        // TODO Auto-generated method stub
        this.initClicks();
        this.initBoardObj();
        this.createboard();
    }

    @Override
    public void isGameOver() {
        // TODO Auto-generated method stub
    }

    @Override
    public void displayScoreOnboard() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setColor() {
        // TODO Auto-generated method stub
    }
}
