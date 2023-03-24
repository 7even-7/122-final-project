import java.awt.event.*;

import javax.swing.*;

public class BejewlGame extends JFrame implements GameEngine, ActionListener {
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
    private int secondClickdRow;
    private int secondClickCol;

    // Constructor that calls createBoard() to create gameboard
    private JButton reset;
    private BejewelBoard boardObj;
    private Player player;

    public BejewlGame(String name) {
        this.player = new Player(name);
        this.row = 9;
        this.col = 8;
        this.init();
    }

    public void initClicks() {
        this.firstClickRow = -99;
        this.firstClickCol = -99;
        this.secondClickdRow = -99;
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
    public boolean isAdjacent(int fr, int fc, int sr, int sc) {
        int rowDiff = Math.abs(sr - fr);
        int colDiff = Math.abs(sc - fc);
        return (fr == sr && colDiff == 1) || (fc == sc && rowDiff == 1);
    }

    //set first clicked target
    public void setFirstClick(int r, int c) {
        this.firstClickCol = c;
        this.firstClickRow = r;
    }

    //set second clicked target
    //updates the board if the clicked location is adjacent
    public void setSecondClick(int r, int c) {
        this.secondClickCol = c;
        this.secondClickdRow = r;
        if (this.isAdjacent(this.firstClickRow, this.firstClickCol, this.secondClickdRow, this.secondClickCol)) {
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
        int[] clicks = {this.firstClickRow, this.firstClickCol, this.secondClickdRow,
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
