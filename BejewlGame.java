import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
public class BejewlGame extends JFrame implements GameEngine, ActionListener{
	//player score
	private int score;
	//Jbutton
	private JButton[][] buttons;
	//num of game row
	private int row;
	//num of game col
	private int col;
	private ArrayList<Color> colors;  
	private String userName;
	//log first selected row & col
	private int firstClickRow;
	private int firstClickCol;
	//log second selected rol & col
	private int secondClickdRow;
	private int secondClickCol;
	//constrctor that calls createBoard() to create gameboard
	private JButton reset;
	public BejewlGame(String player){
		
		score = 0;
		this.firstClickRow = -99;
		this.firstClickCol = -99;
		this.secondClickdRow = -99;
		this.secondClickCol = -99;
		this.row = 9;
		this.col = 8;
		this.userName = player;
		colors = new ArrayList<Color>();
		colors.add(Color.MAGENTA);
		colors.add(Color.white);
		colors.add(Color.pink);
		colors.add(Color.yellow);
		colors.add(Color.pink);
		colors.add(Color.green);
		colors.add(Color.blue);
		//create the game board
		this.createboard();
	
	}
		

	@Override
	public void createCell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createboard() {
		// TODO Auto-generated method stub
		this.setSize(1000, 850);
		JPanel gameBoard = new JPanel(new GridLayout(this.row+1, this.col));
		buttons = new JButton[this.row][this.col];
	    for (int i = 0; i < row; i++) {
	        for(int j = 0; j < col; j++) {
	        	if (i == 8) {
	        		buttons[i][j] = new JButton("");
	        		gameBoard.add(buttons[i][j]);
	        	}
//	        	else
//	        	{
//	        		buttons[i][j] = new JButton();
//			  	    buttons[i][j].addActionListener(this);
//			  	    buttons[i][j].setFocusPainted(false);
//			  	    buttons[i][j].setBackground(this.colors.get((int)(Math.random() * 4)));
//			  	    gameBoard.add(buttons[i][j]);
//	        	}
	        	else
	        	{
		        	buttons[i][j] = new JButton();
			  	    buttons[i][j].addActionListener(this);
			  	    buttons[i][j].setFocusPainted(false);
			  	    buttons[i][j].setBackground(this.colors.get((int)(Math.random() * 4)));
			  	    gameBoard.add(buttons[i][j]);
	        	}

	          
	        }
	      }
	    
	    //create the player field and score filed
	    
	    buttons[8][0].setText("Player: " + this.userName);
	    buttons[8][1].setText("Score: " + this.score);
	    //buttons[8][2].setText("Switch Player");
	    buttons[8][3].setText("Exit Game");
//	    //action listeners for switch player and exit game
	    //this.SwitchGameActionListener();
	    this.ExitActionListener();
//	    
//	    gameBoard.add(buttons[8][0]);
//	    gameBoard.add(buttons[8][1]);
//	    gameBoard.add(buttons[8][2]);
//	    gameBoard.add(buttons[8][3]);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.getContentPane().add(gameBoard, "Center");
	    
	    this.setVisible(true);
	    
	}
	
	
	
	//check if the selected click is adjacent to eachother
	@Override
	public boolean checkConnected() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//check if the two clicked mouse location is connected
	public boolean isAdjacent(int fr, int fc, int sr, int sc){
		//System.out.println("FirstRow, Col:" + fr + ", " + fc + "SecondRow, Col:" + sr + ", " + sc);
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
		//System.out.println(this.firstClickRow + " " +  this.firstClickCol);
		//System.out.println(this.secondClickdRow + " " +  this.secondClickCol);
		//System.out.println(buttons[7][0].getBackground());
		//System.out.println(firstClickCol == secondClickCol);
		//boolean res = this.isAdjacent(this.firstClickRow, this.firstClickCol, this.secondClickdRow, this.secondClickCol);
		//System.out.println(res);
		if(this.isAdjacent(this.firstClickRow, this.firstClickCol, this.secondClickdRow, this.secondClickCol))
		{
			this.updateBoard();
		}
		else {
			this.resetClickCursor();
		}
		
		
	}
	//return num of matches up
	public int matchUp(int r, int c) {
		int res = 0;
		Color cellColor = buttons[r][c].getBackground();
		r--;
		while (0 <= r) {
			Color currCellColor = buttons[r][c].getBackground(); 
			if (currCellColor == cellColor) {
				res++;
			}
			else
			{
				break;
			}
			r--;
		}
		
		
		return res;
	}
	//return num of matches down
	public int matchDown(int r, int c) {
		int res = 0;
		Color cellColor = buttons[r][c].getBackground();
		r++;
		while (r < this.row) {
			Color currCellColor = buttons[r][c].getBackground(); 
			if (currCellColor == cellColor) {
				res++;
			}
			else
			{
				break;
			}
			r++;
		}
		
		
		return res;
	}
	//return num of matches left
	public int matchLeft(int r, int c) {
		int res = 0;
		Color cellColor = buttons[r][c].getBackground();
		c--;
		while (0 <= c) {
			Color currCellColor = buttons[r][c].getBackground(); 
			if (currCellColor == cellColor) {
				res++;
			}
			else
			{
				break;
			}
			c--;
		}
		
		
		return res;
	}
	//return num of matches right
	public int matchRight(int row, int col) {
		int res = 0;
		Color cellColor = buttons[row][col].getBackground();
		col++;
		while (col < this.col) {
			Color currCellColor = buttons[row][col].getBackground(); 
			if (currCellColor == cellColor) {
				res++;
			}
			else
			{
				break;
			}
			col++;
		}
		
		
		return res;
	}
	@Override
	public int displayScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void incrementScore(int num) {
		// TODO Auto-generated method stub
		this.score += num;
		
	}
	public int getScore() {
		return this.score;
	}
	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		//close the program
		System.exit(0);
		
	}
	//resets the first clicked cursor location
	public void resetClickCursor() {
		this.firstClickRow = -99;
		this.firstClickCol = -99;
	}
	public void updateBoard() {
		//up, down, left, right
		//get up and down res for the first click switch
		//set firstclick to secondclick's color
		Color temp = buttons[this.firstClickRow][this.firstClickCol].getBackground();
		buttons[this.firstClickRow][this.firstClickCol].setBackground(buttons[this.secondClickdRow][this.secondClickCol].getBackground());
		
		//set second clicked location to first clicked color
		buttons[this.secondClickdRow][this.secondClickCol].setBackground(temp);
		//run vertical match on first clicked cursor
		this.getVerticalMatch(this.firstClickRow, this.firstClickCol);
		this.getVerticalMatch(this.secondClickdRow, this.secondClickCol);
		//run horizontal match on first clicked cursor
		this.getHorizontalMatch(this.firstClickRow, this.firstClickCol);
		this.getHorizontalMatch(this.secondClickdRow, this.secondClickCol);
		//this.resetClickCursor();
		buttons[8][1].setText("Score: " + Integer.toString(this.getScore()));
		this.resetClickCursor();
	}
	public void getVerticalMatch(int r, int c) {
		//row

		int up = this.matchUp(r, c);
		int down = this.matchDown(r, c);
		int startPoint = r - up;
		int res = up + 1 + down;
		//System.out.println("Start: " + startPoint + " result: " + res);
		if (res >= 3) {
			for(int i = startPoint; i <= r+down; i++) {
				Random rand = new Random();
				int random = rand.nextInt(6);
				//System.out.println(colors.get(random));
				buttons[i][c].setBackground(this.colors.get(random));
			}
			//updatescore
			this.incrementScore(res);
		
		}
		
		
		
	}
	
	public void getHorizontalMatch(int r, int c) {
		int left = this.matchLeft(r, c);
		int right = this.matchRight(r, c);
		int startPoint = c - left;
		int res = left + 1 + right;
		//System.out.println("start: " + startPoint + "res: " + res);
		if (res >= 3) {
			for(int i = startPoint; i <= c+right; i++) {
				Random rand = new Random();
				int random = rand.nextInt(6);
				//System.out.println(colors.get(random));
				buttons[r][i].setBackground(this.colors.get(random));
			}
			//updatescore
			this.incrementScore(res);
		
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton click = (JButton) e.getSource();
		//System.out.println(click.getBackground());
		//System.out.println(this.firstClickCol);
		 for (int i = 0; i < this.row; i++) {
		      for(int j = 0; j < this.col; j++) {
		        if(click == buttons[i][j]) {
		        //set first click location
		        //System.out.println("row: " + i + " Col: " + j);
		          if (this.firstClickCol == -99) 
		          {
		        	  this.setFirstClick(i, j);
		        	  //System.out.println(this.firstClickRow + " , " + this.firstClickCol); 
		        	  
			          
		          }
		         //set second click location
		          else this.setSecondClick(i, j);
		        }
		      }
		    }
		 
		
		
		
	}
	//sets the visibility of Jframe to false
	public void setOff() {
		this.setVisible(false);
	}
	
	
	//change to a new player event listener
	public void SwitchGameActionListener() {
		buttons[8][2].addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
//		    	setOff();
//		    	PlayerLogin newGame = new PlayerLogin();
		    	dispose();
		    	
		    	
		    	
		    	
		    }
		});
	}

	//exit game event listener
	public void ExitActionListener() {
		buttons[8][3].addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	System.exit(0);
		    	
		    }
		});
	}
	public void resetBoard(String playerName) {
		this.dispose();
		//this.setVisible(false);
		//new BejewlGame(playerName);
		repaint();
		
	}
	
}
