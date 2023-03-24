import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BejewelBoard extends Score{
	private JPanel gameBoard;
	public JButton buttons[][];
	private ActionListener action;
	private ArrayList<Color> colors;
	private calculateMatch tileMatch;
	private int r;
	private int c;
	private String name;
	public BejewelBoard(int numOfRow, int numOfCol, ActionListener action, String name) {
		gameBoard = new JPanel(new GridLayout(numOfRow + 1, numOfCol));
		buttons = new JButton[numOfRow][numOfCol];
		tileMatch = new calculateMatch();
		colorData c = new colorData();
		this.r = numOfRow;
		this.c = numOfCol;
		this.action = action;
		this.colors = c.getColors();
		this.score = 0;
		this.name = name;
		this.initBoard(numOfRow, numOfCol);
		this.setGameStatus();
		this.ExitActionListener();
	}
	public void initBoard(int r, int c) {
		this.createTitle(r, c, this.action);
		this.ExitActionListener();
	}
	//creates the tiles in the board
	public void createTitle(int row, int col, ActionListener a) {
		 for (int i = 0; i < row; i++) {
		        for(int j = 0; j < col; j++) {
		        	if (i == 8) {
		        		this.setEmptyTile(i, j);
		        	}
		        	else
		        	{
			        	this.setTileContent(i, j, a);
		        	}

		        }
		      }
		
	}
	
	//sets the tile contents
	public void setTileContent(int r, int c, ActionListener a) {
		buttons[r][c] = new JButton();
		buttons[r][c].addActionListener(a);
		buttons[r][c].setFocusPainted(false);
  	    buttons[r][c].setBackground(this.colors.get((int)(Math.random() * 4)));
  	    gameBoard.add(buttons[r][c]);
		
	}
	//create empty tile for board status
	public void setEmptyTile(int r, int c) {
		buttons[r][c] = new JButton("");
		gameBoard.add(buttons[r][c]);
	}
	public void setGameStatus() {
		buttons[8][0].setText("Player: " + this.name);
	    buttons[8][1].setText("Score: " + this.score);
	    //buttons[8][2].setText("Switch Player");
	    buttons[8][2].setText("Exit Game");
	    
	    
	}
	
	public JPanel getBoard() {
		
		return gameBoard;
	}
	
	public void updateVerticalTiles(int[] matchRes) {
		int start = matchRes[0];
		int end = matchRes[1];
		int res = matchRes[2];
		int col = matchRes[3];	
//		System.out.println("Vertical:\nStart: " + start);
//		System.out.println("end: " + end);
//		System.out.println("res: " + res);
//		System.out.println("col: " + col);
		if (res >= 3) {
			this.incrementScore(res);
			for(int i = start; i <= end; i++) {
				Random rand = new Random();
				int random = rand.nextInt(6);
				//System.out.println(colors.get(random));
				buttons[i][col].setBackground(this.colors.get(random));
			}
			//updatescore
			
		
		}
	}
	
	public void updateHorizontalTiles(int[] matchRes) {
		int start = matchRes[0];
		int end = matchRes[1];
		int res = matchRes[2];
		int row = matchRes[3];
//		System.out.println("Horizontal:\nStart: " + start);
//		System.out.println("end: " + end);
//		System.out.println("res: " + res);
//		System.out.println("col: " + row);
		if (res >= 3) {
			this.incrementScore(res);
			for(int i = start; i <= end; i++) {
				Random rand = new Random();
				int random = rand.nextInt(6);
				//System.out.println(colors.get(random));
				buttons[row][i].setBackground(this.colors.get(random));
			}
			//updatescore
			
		
		}
	}
	public void updateBoard(int[] clicks) {
		//clicks -> [firstclickrow, firstclickcol, secondclickrow, secondclickcol]
		//set firstclick to secondclick's color
//		System.out.println("FirstRow: " + clicks[0] + " FirstCol: " + clicks[1]);
//		System.out.println("secondRow: " + clicks[2] + " secondCol: " + clicks[3]);

		Color firstColor = buttons[clicks[0]][clicks[1]].getBackground();
		Color secondColor = buttons[clicks[2]][clicks[3]].getBackground();
		buttons[clicks[0]][clicks[1]].setBackground(secondColor);
		
		//set second clicked location to first clicked color
		buttons[clicks[2]][clicks[3]].setBackground(firstColor);
		
		//first click matchRes
		int[] res1 = this.tileMatch.getVerticalMatch(clicks[2], clicks[3], this.r-1, buttons, firstColor);
		int[] res2 = this.tileMatch.getHorizontalMatch(clicks[2], clicks[3], this.c, buttons, firstColor);
		int[] res3 = this.tileMatch.getVerticalMatch(clicks[0], clicks[1], this.r-1, buttons, secondColor);
		int[] res4 = this.tileMatch.getHorizontalMatch(clicks[0], clicks[1], this.c, buttons, secondColor);

		//run vertical match on first clicked cursor
		this.updateVerticalTiles(res1);
		this.updateHorizontalTiles(res2);
		
		this.updateVerticalTiles(res3);
		this.updateHorizontalTiles(res4);
		//this.updateHorizontalTiles(res2);
		//this.resetClickCursor();
	}
	
	@Override
	public int getScore() {
		return this.score;
	}
	
	public void ExitActionListener() {
		buttons[8][2].addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	System.exit(0);
		    	
		    }
		});
	}
	@Override
	void incrementScore(int num) {
		this.score += score;
		buttons[8][1].setText("Score: " + Integer.toString(this.score));
		// TODO Auto-generated method stub
		this.score += num;
	}
}
