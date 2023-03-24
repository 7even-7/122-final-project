import java.util.Scanner;

public class MainGame {
    public Tetris tetris;
    public BejeweledGame bj;
    public Winner bjPlayers;
    public Winner tetrisPlayers;

    public MainGame() {
        this.bjPlayers = new Winner();
        this.tetrisPlayers = new Winner();
    }

    public void createGame(String game, String playerName) {
        if (game.equals("T")) {
            tetris = new Tetris(playerName);
            tetris.updateBoard();

        } else if (game.equals("B")) {
            bj = new BejeweledGame(playerName);
        }
    }

    public void resetGame(String newGame, String Name) {
        if (newGame.equals("NB")) {
            if (this.tetris != null) {
                this.tetris.dispose();
            }
            if (this.bj != null) {
                this.bj.dispose();
                this.bj = new BejeweledGame(Name);
            } else {
                this.createGame("B", Name);
            }
        }

        if (newGame.equals("NT")) {
            if (this.bj != null) {
                this.bj.dispose();
            }
            if (this.tetris != null) {
                this.tetris.setOff();
                this.tetris = new Tetris(Name);
                tetris.updateBoard();
            } else {
                this.createGame("T", Name);
            }
        }
    }

    public void updateWinner() {
        if (this.bj != null) {
            this.bjPlayers.addPlayer(this.bj.getCurrPlayer());
        }

        if (this.tetris != null) {
            this.tetrisPlayers.addPlayer(this.tetris.getCurrPlayer());
        }
    }

    public void printWinner() {
        if (!bjPlayers.isEmpty()) {
            Player p = this.bjPlayers.getPlayers().get(this.bjPlayers.getPlayers().size() - 1);
            System.out.println(
                    "Game: " + "Bejewel " +
                            "Winner: " + p.getName()
                            + " Score: " + p.getScore());
        }
        if (!tetrisPlayers.isEmpty()) {
            Player p = this.tetrisPlayers.getPlayers().get(this.tetrisPlayers.getPlayers().size() - 1);
            System.out.println(
                    "Game: " + "Tetris " +
                            " Winner: " + p.getName()
                            + " Score: " + p.getScore());
        }
    }

    public static void main(String[] args) {
        //CMD approach
        Scanner scan = new Scanner(System.in);
        MainGame mainGame = new MainGame();
        boolean running = false;
        while (true) {

            if (!running) {
                System.out.println("Enter your name");

                String playerName = scan.nextLine();

                System.out.println("Select from the following games: \n1. Tetris -> Enter T\n2. Bejewel -> Enter B");
                String game = scan.next();
                mainGame.createGame(game, playerName);
                System.out.println("Enter NewGame to select a new Game");
                System.out.println("Enter EndGame to end the Game");

                running = true;
            }

            String command = scan.nextLine();

            if (command.equals("NewGame")) {
                mainGame.updateWinner();
                System.out.println("Enter your new player name");
                String newPlayer = scan.nextLine();

                System.out.println("Select from the following games: \n1. Tetris -> Enter NT\n2. Bejewel -> Enter NB");
                String newGame = scan.next();

                mainGame.resetGame(newGame, newPlayer);
                System.out.println("Enter NewGame to select a new Game");
                System.out.println("Enter EndGame to end the Game");
            }

            if (command.equals("EndGame")) {
                mainGame.updateWinner();
                mainGame.printWinner();
                System.exit(0);
            }
        }
    }
}
