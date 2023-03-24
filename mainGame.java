import java.util.Scanner;
public class mainGame {
	public Tetris tetris;
	public BejewlGame bj;
	public Winner bjPlayers;
	public Winner tetrisPlayers;
	public mainGame(){
		this.bjPlayers = new Winner();
		this.tetrisPlayers = new Winner();
	}
	public void createGame(String game, String playerName) {
		if (game.equals("T")) {
			tetris = new Tetris(playerName);
			//System.out.println("Enter NewGame to select a new Game");
			tetris.updateBoard();;
			
		}
		else if (game.equals("B")) {
			//bj.setName(playerName);
			bj = new BejewlGame(playerName);
			
		}
	}
	public void resetGame(String newGame, String Name) {
		if(newGame.equals("NB")) {
			if (this.tetris != null) {
				this.tetris.dispose();
			}
			if (this.bj != null) {
				this.bj.dispose();
				this.bj = new BejewlGame(Name);
			}
			else
			{
				this.createGame("B", Name);
			}
			//this.bj.resetBoard(Name);
		}
		
		if (newGame.equals("NT")) {
			if (this.bj != null) {
				this.bj.dispose();
			}
			if (this.tetris != null) {
				this.tetris.setOff();
				this.tetris = new Tetris(Name);
				tetris.updateBoard();
			}
			else
			{
				this.createGame("T", Name);
			}
			
		}
		
	}
	public void updateWinner() {
		if (this.bj != null) {
			this.bjPlayers.addPlayer(this.bj.getCurrPlayer());

		}
		
		if(this.tetris != null) {
			this.tetrisPlayers.addPlayer(this.tetris.getCurrPlayer());
		}
	}
	public void printWinner() {
		if (!bjPlayers.isEmpty()) {
			Player p = this.bjPlayers.getPlayers().get(this.bjPlayers.getPlayers().size()-1);
			System.out.println(
					"Game: " + "Bejewel " + 
					"Winner: " + p.getName()
					+ " Score: " + p.getScore());
		}
		if (!tetrisPlayers.isEmpty()) {
			Player p = this.tetrisPlayers.getPlayers().get(this.tetrisPlayers.getPlayers().size()-1);
			System.out.println(
					"Game: " + "Tetris " + 
					" Winner: " + p.getName()
					+ " Score: " + p.getScore());
		}
		
	}
	public static void main(String arvg[]) {
		//GUI approach
//		PlayerLogin game = new PlayerLogin();
//		
		Scanner scan = new Scanner(System.in);
		//CMD approach
		mainGame m = new mainGame();
		boolean Running = false;
		while (true) {
			
			if(!Running) {
				System.out.println("Enter your name");
				
				String playerName = scan.nextLine();
				
				System.out.println("Select from the following games: \n1. Tetris -> Enter T\n2. Bejewel -> Enter B");
				String game = scan.next();
				m.createGame(game, playerName);
				System.out.println("Enter NewGame to select a new Game");
				System.out.println("Enter EndGame to end the Game");

				Running = true;
				
			}
			String command = scan.nextLine();
			if (command.equals("NewGame")) {
				m.updateWinner();
				//System.out.println(Running);
				System.out.println("Enter your new player name");
				String newPlayer = scan.nextLine();
				
				System.out.println("Select from the following games: \n1. Tetris -> Enter NT\n2. Bejewel -> Enter NB");
				String newGame = scan.next();
				
				m.resetGame(newGame, newPlayer);
				System.out.println("Enter NewGame to select a new Game");
				System.out.println("Enter EndGame to end the Game");


			}
			if (command.equals("EndGame")){
					m.updateWinner();
					m.printWinner();
			

			}
			
		}
		
	}
}
