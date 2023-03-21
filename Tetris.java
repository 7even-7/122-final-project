import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class Tetris extends JFrame implements KeyListener, ActionListener{

//    Scanner pause = new Scanner(System.in);
//    public static void main(String[] args){
//        Tetris tetris = new Tetris();
//        tetris.game_begin();
//    }

    // Instance Variables
    private static final int t_row = 26;
    private static final int t_column = 12;
    int[][][] blocks;   // List of possible blocks to spawn
    int[][] block;      // Current block being dropped
    JTextArea[][] text;
    int [][] data;
    JLabel game_status;
    JLabel game_score;
    boolean isRunning;
    int length = t_column-2;
    int time = 1000;
    //coordinate
    int x;
    int y;
    int score = 0;
    private String playerName;

    // Constructor
    public Tetris(String name){
        //1 means block, 0 means blank
        text = new JTextArea[t_row][t_column];      // stores square graphics of game
        data = new int [t_row][t_column];           // stores which blocks are occupied
        this.playerName = name;
        
        initGamePanel();
        initExplainPanel();
        initWindow();

        isRunning = true;

        blocks = new int [][][] {
            {
                //0
                {0, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 1, 0, 0},
                {1, 0, 0, 0}
            },
            {
                //1
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0}
            },
            {
                //2
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 0, 0}
            },
            {
                //3
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {1, 1, 1, 0}
            },
            {
                //4
                {0, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 0, 0}
            },
            {
                //5
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {1, 1, 0, 0}
            },
            {
                //6
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 0, 0, 0}
            },
            {
                //7
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 0}
            },
            {
                //8
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
            },
            {
                //9
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1}
            },
            {
                //10
                {0, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 1, 0, 0}
            },
            {
                //11
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
            },
            {
                //12
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}
            },
            {
                //13
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
            },
            {
                //14
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0}
            },
            {
                //15
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0}
            }

        };
    }
    
    

    //Game UI
    public void initGamePanel(){
        JPanel game_main = new JPanel();
        game_main.setLayout(new GridLayout(t_row, t_column,1,1));

        //initial the board
        for(int i=0; i<text.length; i++){
            for(int j=0; j<text[i].length; j++){
                text[i][j] = new JTextArea(t_row, t_column);
                text[i][j].setBackground(Color.WHITE);
                text[i][j].addKeyListener(this);

                // Draw border of board
                if(j==0 || j==text[i].length-1 || i==text.length-1){
                    text[i][j].setBackground(Color.LIGHT_GRAY);
                    data[i][j]=1;
                }

                //text area cannot be edited
                text[i][j].setEditable(false);

                game_main.add(text[i][j]);
            }
        }
        this.setLayout(new BorderLayout());
        this.add(game_main, BorderLayout.CENTER);
    }

    //Instruction Panel
    public void initExplainPanel(){
        JLabel space_button;
        JLabel left_button;
        JLabel right_button;
        JLabel up_button;
        JLabel instruction;
        JLabel space_simple;
        JButton endGame;
        //left panel
        JPanel explain_Left = new JPanel();

        //instructions
        instruction = new JLabel("Instructions: ");
        left_button = new JLabel("Left Arrow: Move Left");
        right_button = new JLabel("Right Arrow: Move Right");
        up_button = new JLabel("Up Arrow: Change Direction");
        space_button = new JLabel("Space: Drop the Block");
        space_simple=new JLabel(" ");
        
        //game status
        game_status = new JLabel("Game Status: In Game");
        game_score = new JLabel("Game Score: 0");
        JLabel name = new JLabel("Player: " + this.playerName);
        endGame = new JButton("New Game");
        
        endGame.addActionListener(this);
        explain_Left.setLayout(new GridLayout(16,1));
        instruction.setForeground(Color.BLACK);
        left_button.setForeground(Color.BLUE);
        right_button.setForeground(Color.BLUE);
        up_button.setForeground(Color.BLUE);
        space_button.setForeground(Color.BLUE);
        game_status.setForeground(Color.RED);
        game_score.setForeground(Color.RED);



        explain_Left.add(instruction);
        explain_Left.add(space_button);
        explain_Left.add(left_button);
        explain_Left.add(right_button);
        explain_Left.add(up_button);
        explain_Left.add(space_simple);
        explain_Left.add(space_simple);
        explain_Left.add(name);
        explain_Left.add(game_status);
        explain_Left.add(game_score);
        explain_Left.add(endGame);

        this.add(explain_Left,BorderLayout.WEST);
      
    }

    // Window settings
    public void initWindow(){

        this.setSize(600,650);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        
    }
    

    //Start the game
    public void game_begin(){
        while(true){
            //check if game is over
            if(isRunning==true){
                try {
                    game_run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Game Over!");
                break;
            }
        }
        isGameOver();
    }

    public void isGameOver(){
        if(!isRunning){
            game_status.setText("Game Status: Game Over");
        }
    }

    //pick a random block
    public void pickBlock(){
        Random random = new Random();
        block =  blocks[random.nextInt(blocks.length)];
    }

     // runs the game for one block each
     public void game_run() throws InterruptedException {
        pickBlock();
        int length = t_column-2;
        //block drop position
        x=0;
        y=5;
    
        for(int i=0; i<t_row; i++){
            // pause execution of the thread for 1 second
            TimeUnit.SECONDS.sleep(1);
            //check if the block can be dropped
            if(!canFall(x,y)){
            // What happens when the block stops
                //set the data to 1 to represent the block is occupied
                updateBoard(x,y);
    
                // Code to remove lines
                // Detect if row is full
                isFullRow();
    
                //check game over
                passGameOverLine();
                break;
            }
            else{
                //row++
                x++;
                fall(x,y);
            }
        }
    }

    // Detect if row is full
    public void isFullRow(){
        int point=100;
        int checker=x;
        while(checker<x+4){
            int sum=0;
            for(int i=1; i<=length; i++){
                if(data[checker][i]==1){
                    sum++;
                }
            }

            if(sum == (t_column-2)){
                removeRow(checker);
                score+=point;
                game_score.setText("Game Score: "+score);
            }
            checker++;
        }
    }

    // Checks to see if the dropped block passes the game over line
    public void passGameOverLine(){
        int checker = 1;
        while (checker <= length && isRunning) {
        if (data[t_row-22][checker] == 1) {
            isRunning = false;
        }
            checker++;
        }
    }

    public boolean canFall(int m, int n) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                    if (data[m + i + 1][n + j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void updateBoard(int m, int n) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                     data[m + i][n + j] = 1;
                 }
            }
        }
    }

    public void removeRow(int row){
        for(int i=row; i>=1; i--){
            for(int j=1; j<=length; j++){
                data[i][j]=data[i-1][j];
            }
        }
        reSetGame(row);
    }


    //reset the game when a row is removed 
    public void reSetGame(int row) {
        int i = row;
        do {
            for (int j = 1; j <= length; j++) {
                if (data[i][j] == 1) {
                    setColor(i,j);
                } else {
                    setColor(i,j);
                }
            }
            i--;
        } while (i >= 1);
    }

    //when reset the board, change all 1s to Orange and change all 0s to White
    public void setColor(int m, int n){
        if(data[m][n]==1){
            text[m][n].setBackground(Color.ORANGE);
        }
        else{
            text[m][n].setBackground(Color.WHITE);
        }
    }

    public void fall(int m, int n){
        if(m>0){
            cleaning(m-1,n);
        }
        drawing(m,n);
    }


    public void cleaning(int m, int n) {
        int i = 0;
        while (i < 16) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[m][n].setBackground(Color.WHITE);
            }
            n++;
            if (col == 3) {
                m++;
                n -= 4;
            }
            i++;
        }
    }

    public void drawing(int m, int n) {
        for (int i = 0; i < 16; i++) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[m][n].setBackground(Color.ORANGE);
            }
            n++;
            if (i % 4 == 3) {
                m++;
                n -= 4;
            }
        }
    }

    public boolean checkTurn(int[][] a, int m, int n){
        for(int i=0; i<16; i++){
            int row = m + i/4;
            int col = n + i%4;
            if(a[i/4][i%4]!=0 && data[row][col]==1){
                return false;
            }
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e){
       
    }

    private boolean isEqualTo(int[][] a, int[][] b) {

        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a.length; j++) {
                if(a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }


        return true;
    }

    public int getCurrentBlock(){
        int currentBlock;
        for(currentBlock=0; currentBlock<blocks.length; currentBlock++){
            if(isEqualTo(block, blocks[currentBlock])){
                break;
            }
        }
        return currentBlock;
    }

    public int [][] change_block(int current){
        int[][] temp;
        temp=blocks[current+1];
        return temp;
    }

    @Override
    public void keyPressed(KeyEvent e){
        int current_block;
        //change direction
        if(e.getKeyCode() == 38){
            int[][] next;

            if(!isRunning){
                return;
            }

            current_block=getCurrentBlock();
            cleaning(x,y);

            if(current_block == 0 || current_block == 1 || current_block == 2 || current_block == 3){
                if(current_block==0){
                    next=change_block(current_block);
                }
                else if(current_block==1){
                    next=change_block(current_block);
                }
                else if (current_block==2){
                    next=change_block(current_block);
                }
                else{
                    next=blocks[0];
                }
                
                if(checkTurn(next,x,y)){
                    block=next;
                }
            }

            if(current_block == 4 || current_block == 5 || current_block == 6 || current_block == 7){
                if(current_block==4){
                    next=change_block(current_block);
                }
                else if(current_block==5){
                    next=change_block(current_block);
                }
                else if (current_block==6){
                    next=change_block(current_block);
                }
                else{
                    next=blocks[4];
                }
                
                if(checkTurn(next,x,y)){
                    block=next;
                }
            }

            if(current_block == 8 || current_block == 9){
                if(current_block==8){
                    next=change_block(current_block);
                }
                else{
                    next=blocks[8];
                }
                
                if(checkTurn(next,x,y)){
                    block=next;
                }
            }

            if(current_block == 10 || current_block == 11 || current_block == 12 || current_block == 13){
                if(current_block==10){
                    next=change_block(current_block);
                }
                else if(current_block==11){
                    next=change_block(current_block);
                }
                else if(current_block==12){
                    next=change_block(current_block);
                }
                else{
                    next=blocks[10];
                }
                
                if(checkTurn(next,x,y)){
                    block=next;
                }
            }

            if(current_block == 14 || current_block == 15){
                if(current_block==14){
                    next=change_block(current_block);
                }
                else{
                    next=blocks[14];
                }
                
                if(checkTurn(next,x,y)){
                    block=next;
                }
            }
            drawing(x,y);
        }

        //move left
        if (e.getKeyCode() == 37) {
            if (!isRunning) {
                return; 
            }
            if (y <= 0) {
                return;
            }
            y--;
        
            // Check if left is clear and able to move
            for (int i = x; i < x+blocks[0].length; i++) {
                for (int j = y; j < y+blocks[0].length; j++) {
                    if (block[i-x][j-y]!=0 && (j < 0 || data[i][j]==1)) {
                        y++; 
                        return;
                    }
                }
            }
            cleaning(x, y+1);
            drawing(x, y); 
        }


        //move right
        if (e.getKeyCode() == 39) {
            if (!isRunning) {
                return; 
            }
            if (y + 3 >= t_column) {
                return; 
            }
            y++; 
        
            // Check if right is clear and able to move
            for (int i = x; i < x+blocks[0].length; i++) {
                for (int j = y; j < y+blocks[0].length; j++) {
                    if (block[i-x][j-y] != 0 && (j<0 || j >= t_column || data[i][j]==1)) {
                        y--;
                        return;
                    }
                }
            }
            cleaning(x, y-1); 
            drawing(x, y);
        }
        
        //drop the block
        if(e.getKeyCode() == 32){
            if(!isRunning){
                return;
            }

            if(!canFall(x,y)){
                return;
            }

            cleaning(x,y);
            x++;
            drawing(x,y);
        }
        
    }
    public void setOff() {
    	this.isRunning = false;
    	this.dispose();
    }
    public void newGame(String name) {
    	this.isRunning = false;
    	this.dispose();
    	repaint();
    }
    @Override
    public void keyReleased(KeyEvent e){

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setOff();
		
	}
}