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
                System.out.print("Enter your player name:\n>>> ");

                String playerName = scan.nextLine();

                System.out.print("\nSelect from the following games: \n1. Tetris -> Enter T\n2. Bejewel -> Enter B\n>>> ");
                String game = handleInvalidInputs(new String[]{"B", "T"}, scan);

                System.out.println("\n...Waiting for game to end. Please wait.");
                mainGame.createGame(game, playerName);

                System.out.print("\nEnter \"NewGame\" to start a game with another Player, \n or enter \"EndGame\" to end the game and display the winner: \n>>> ");

                running = true;
            }

            String command = handleInvalidInputs(new String[]{"EndGame", "NewGame"}, scan);

            if (command.equals("NewGame")) {
                mainGame.updateWinner();

                System.out.print("\nEnter the name for a new player: \n>>> ");
                String newPlayer = scan.nextLine();


                System.out.print("\nSelect from the following games: \n1. Tetris -> Enter NT\n2. Bejewel -> Enter NB\n>>> ");
                String newGame = handleInvalidInputs(new String[]{"NT", "NB"}, scan);

                System.out.println("\n...Waiting for game to end. Please wait.");
                mainGame.resetGame(newGame, newPlayer);

                System.out.print("\nEnter \"NewGame\" to start a game with another Player, \n or enter \"EndGame\" to end the game and display the winner: \n>>> ");
            }

            if (command.equals("EndGame")) {
                mainGame.updateWinner();

                System.out.println();
                mainGame.printWinner();
                System.exit(0);
            }
        }
    }

    private static String handleInvalidInputs(String[] validInputs, Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            for (String validInput : validInputs) {
                if (input.equals(validInput)) {
                    return input;
                }
            }

            System.out.print("...Invalid input. Please try again. >>> ");
        }
    }
}


