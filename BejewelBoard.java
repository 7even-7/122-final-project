import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BejewelBoard extends Score {
    private JPanel gameBoard;
    public JButton[][] buttons;
    private ActionListener action;
    private ArrayList<Color> colors;
    private calculateMatch tileMatch;
    private int rows;
    private int columns;
    private String playerName;

    public BejewelBoard(int numberOfRows, int numberOfColumns, ActionListener action, String playerName) {
        gameBoard = new JPanel(new GridLayout(numberOfRows + 1, numberOfColumns));
        buttons = new JButton[numberOfRows][numberOfColumns];
        tileMatch = new calculateMatch();
        ColorData colorData = new ColorData();
        this.rows = numberOfRows;
        this.columns = numberOfColumns;
        this.action = action;
        this.colors = colorData.getColors();
        this.score = 0;
        this.playerName = playerName;
        this.initBoard(numberOfRows, numberOfColumns);
        this.setGameStatus();
    }

    public void initBoard(int numberOfRows, int numberOfColumns) {
        this.createTitle(numberOfRows, numberOfColumns, this.action);
    }

    //creates the tiles in the board
    public void createTitle(int row, int col, ActionListener a) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 8) {
                    this.setEmptyTile(i, j);
                } else {
                    this.setTileContent(i, j, a);
                }
            }
        }
    }

    //sets the tile contents
    public void setTileContent(int row, int col, ActionListener a) {
        buttons[row][col] = new JButton();
        buttons[row][col].addActionListener(a);
        buttons[row][col].setFocusPainted(false);
        buttons[row][col].setBackground(this.colors.get((int) (Math.random() * 4)));
        gameBoard.add(buttons[row][col]);
    }

    //create empty tile for board status
    public void setEmptyTile(int row, int col) {
        buttons[row][col] = new JButton("");
        gameBoard.add(buttons[row][col]);
    }

    public void setGameStatus() {
        buttons[8][0].setText("Player: " + this.playerName);
        buttons[8][1].setText("Score: " + this.score);
    }

    public JPanel getBoard() {
        return gameBoard;
    }

    public void updateVerticalTiles(int[] matchRes) {
        int start = matchRes[0];
        int end = matchRes[1];
        int res = matchRes[2];
        int col = matchRes[3];

        if (res >= 3) {
            this.incrementScore(res);
            for (int i = start; i <= end; i++) {
                Random rand = new Random();
                int random = rand.nextInt(6);
                buttons[i][col].setBackground(this.colors.get(random));
            }
        }
    }

    public void updateHorizontalTiles(int[] matchRes) {
        int start = matchRes[0];
        int end = matchRes[1];
        int res = matchRes[2];
        int row = matchRes[3];

        if (res >= 3) {
            this.incrementScore(res);
            for (int i = start; i <= end; i++) {
                Random rand = new Random();
                int random = rand.nextInt(6);
                buttons[row][i].setBackground(this.colors.get(random));
            }
        }
    }

    public void updateBoard(int[] clicks) {
        Color firstColor = buttons[clicks[0]][clicks[1]].getBackground();
        Color secondColor = buttons[clicks[2]][clicks[3]].getBackground();
        buttons[clicks[0]][clicks[1]].setBackground(secondColor);

        //set second clicked location to first clicked color
        buttons[clicks[2]][clicks[3]].setBackground(firstColor);

        //first click matchRes
        int[] res1 = this.tileMatch.getVerticalMatch(clicks[2], clicks[3], this.rows - 1, buttons, firstColor);
        int[] res2 = this.tileMatch.getHorizontalMatch(clicks[2], clicks[3], this.columns, buttons, firstColor);
        int[] res3 = this.tileMatch.getVerticalMatch(clicks[0], clicks[1], this.rows - 1, buttons, secondColor);
        int[] res4 = this.tileMatch.getHorizontalMatch(clicks[0], clicks[1], this.columns, buttons, secondColor);

        //run vertical match on first clicked cursor
        this.updateVerticalTiles(res1);
        this.updateHorizontalTiles(res2);

        this.updateVerticalTiles(res3);
        this.updateHorizontalTiles(res4);
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    void incrementScore(int num) {
        this.score += score;
        buttons[8][1].setText("Score: " + Integer.toString(this.score));
        // TODO Auto-generated method stub
        this.score += num;
    }
}
