import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class Tetris extends JFrame implements KeyListener, ActionListener, GameEngine {
    // Instance Variables
    private static final int t_row = 26;
    private static final int t_column = 12;
    private int[][][] blocks;   // List of possible blocks to spawn
    private int[][] block;      // Current block being dropped
    JTextArea[][] text;
    private int[][] data;
    private JLabel game_status;
    private JLabel game_score;
    private boolean isRunning;
    private int length = t_column - 2;
    private int time = 1000;

    // Coordinates
    private int x;
    private int y;
    private int score = 0;
    private Player p;
    private T_Paint tPaint = new T_Paint();
    private T_Scoring getScore = new T_Scoring();
    private JPanel explain_Left;

    // Constructor
    public Tetris(String name) {
        //1 means block, 0 means blank
        text = new JTextArea[t_row][t_column];      // stores square graphics of game
        data = new int[t_row][t_column];           // stores which blocks are occupied
        this.p = new Player(name);
        colorData color = new colorData();
        this.init();

        isRunning = true;

        T_Blocks tetromino = T_Blocks.getInstance();
        blocks = tetromino.getBlocks();
    }

    //Game UI
    @Override
    public void createboard() {
        JPanel game_main = new JPanel();
        game_main.setLayout(new GridLayout(t_row, t_column, 1, 1));

        //initialize the board
        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < text[i].length; j++) {
                text[i][j] = new JTextArea(t_row, t_column);
                text[i][j].setBackground(Color.WHITE);
                text[i][j].addKeyListener(this);

                // Draw border of board
                drawBoarder(i, j, text, data);
                //text area cannot be edited
                text[i][j].setEditable(false);
                game_main.add(text[i][j]);
            }
        }
        this.setLayout(new BorderLayout());
        this.add(game_main, BorderLayout.CENTER);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        createboard();
        initExplainPanel();
        initWindow();
    }

    public void drawBoarder(int i, int j, JTextArea[][] text, int[][] data) {
        if (j == 0 || j == text[i].length - 1 || i == text.length - 1) {
            text[i][j].setBackground(Color.LIGHT_GRAY);
            data[i][j] = 1;
        }
    }

    //Instruction Panel
    public void initExplainPanel() {
        JLabel space_button;
        JLabel left_button;
        JLabel right_button;
        JLabel up_button;
        JLabel instruction;
        JLabel space_simple;
        JButton endGame;
        //left panel
        this.explain_Left = new JPanel();

        //instructions
        instruction = new JLabel("Instructions: ");
        left_button = new JLabel("Left Arrow: Move Left");
        right_button = new JLabel("Right Arrow: Move Right");
        up_button = new JLabel("Up Arrow: Change Direction");
        space_button = new JLabel("Space: Drop the Block");
        space_simple = new JLabel(" ");

        //game status
        game_status = new JLabel("Game Status: In Game");

        JLabel name = new JLabel("Player: " + this.p.getName());
        endGame = new JButton("New Game");

        endGame.addActionListener(this);
        explain_Left.setLayout(new GridLayout(16, 1));
        instruction.setForeground(Color.BLACK);
        left_button.setForeground(Color.BLUE);
        right_button.setForeground(Color.BLUE);
        up_button.setForeground(Color.BLUE);
        space_button.setForeground(Color.BLUE);
        game_status.setForeground(Color.RED);

        explain_Left.add(instruction);
        explain_Left.add(space_button);
        explain_Left.add(left_button);
        explain_Left.add(right_button);
        explain_Left.add(up_button);
        explain_Left.add(space_simple);
        explain_Left.add(space_simple);
        explain_Left.add(name);
        explain_Left.add(game_status);
        this.displayScoreOnboard();
        explain_Left.add(endGame);
        this.add(explain_Left, BorderLayout.WEST);
    }

    // Window settings
    public void initWindow() {
        this.setSize(600, 650);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tetris");
    }


    //Start the game
    @Override
    public void updateBoard() {
        while (true) {
            //check if game is over
            if (isRunning == true) {
                try {
                    game_run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Game Over!");
                break;
            }
        }
        isGameOver();
    }

    public void isGameOver() {
        if (!isRunning) {
            game_status.setText("Game Status: Game Over");
        }
    }


    //pick a random block
    public void pickBlock() {
        Random random = new Random();
        block = blocks[random.nextInt(blocks.length)];
    }

    T_Operations operations = new T_Operations();

    // runs the game for one block each
    public void game_run() throws InterruptedException {
        pickBlock();
        int length = t_column - 2;
        //block drop position
        x = 0;
        y = 5;

        for (int i = 0; i < t_row; i++) {
            // pause execution of the thread for 1 second
            TimeUnit.SECONDS.sleep(1);
            //check if the block can be dropped
            if (!operations.canFall(block, blocks, data, x, y)) {
                // What happens when the block stops
                //set the data to 1 to represent the block is occupied
                operations.updateBoard(block, blocks, data, x, y);

                // Code to remove lines
                // Detect if row is full
                getScore.isFullRow(x, data, text, length, score, game_score);

                //check game over
                passGameOverLine();
                break;
            } else {
                //row++
                x++;
                fall(x, y);
            }
        }
    }

    public void passGameOverLine() {
        for (int j = 1; j <= length; j++) {
            if (data[t_row - 22][j] == 1 && isRunning) {
                isRunning = false;
                break;
            }
        }
    }

    public void fall(int m, int n) {
        if (m > 0) {
            tPaint.cleaning(block, text, data, m - 1, n);
        }
        tPaint.drawing(block, text, m, n);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean isEqualTo(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getCurrentBlock() {
        int currentBlock;
        for (currentBlock = 0; currentBlock < blocks.length; currentBlock++) {
            if (isEqualTo(block, blocks[currentBlock])) {
                break;
            }
        }
        return currentBlock;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int current_block;
        //change direction
        if (e.getKeyCode() == 38) {
            if (!isRunning) {
                return;
            }

            current_block = getCurrentBlock();
            tPaint.cleaning(block, text, data, x, y);

            RotateShape rotateShape = new RotateShape(blocks);
            block = rotateShape.rotate(current_block, data, x, y, tPaint);
            tPaint.drawing(block, text, x, y);
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
            for (int i = x; i < x + blocks[0].length; i++) {
                for (int j = y; j < y + blocks[0].length; j++) {
                    if (block[i - x][j - y] != 0 && (j < 0 || data[i][j] == 1)) {
                        y++;
                        return;
                    }
                }
            }
            tPaint.cleaning(block, text, data, x, y + 1);
            tPaint.drawing(block, text, x, y);
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
            for (int i = x; i < x + blocks[0].length; i++) {
                for (int j = y; j < y + blocks[0].length; j++) {
                    if (block[i - x][j - y] != 0 && (j < 0 || j >= t_column || data[i][j] == 1)) {
                        y--;
                        return;
                    }
                }
            }
            tPaint.cleaning(block, text, data, x, y - 1);
            tPaint.drawing(block, text, x, y);
        }

        //drop the block
        if (e.getKeyCode() == 32) {
            if (!isRunning) {
                return;
            }

            if (!operations.canFall(block, blocks, data, x, y)) {
                return;
            }

            tPaint.cleaning(block, text, data, x, y);
            x++;
            tPaint.drawing(block, text, x, y);
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
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setOff();
    }

    public Player getCurrPlayer() {
        this.p.setScore(this.getScore.getScore());
        return this.p;
    }


    @Override
    public void displayScoreOnboard() {
        // TODO Auto-generated method stub
        //game_score = new JLabel("Game Score: 0");
        game_score = new JLabel("Game Score: 0");
        game_score.setForeground(Color.RED);
        explain_Left.add(game_score);
    }


    @Override
    public void setColor() {
        // TODO Auto-generated method stub
    }
}