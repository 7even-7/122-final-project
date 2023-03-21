import java.util.Scanner;
public class mainGame {
	public Tetris tetris;
	public BejewlGame bj;
	public mainGame(){
		
	}
	public void createGame(String game, String playerName) {
		if (game.equals("T")) {
			tetris = new Tetris(playerName);
			//System.out.println("Enter NewGame to select a new Game");
			tetris.game_begin();
			
		}
		else if (game.equals("B")) {
			//bj.setName(playerName);
			bj = new BejewlGame(playerName);
			
		}
	}
	public void resetGame(String newGame, String Name) {
		if(newGame.equals("NB")&& this.bj != null) {
			
			this.bj.dispose();
			this.bj = new BejewlGame(Name);
			//this.bj.resetBoard(Name);
		}
		else
		{
			this.createGame(newGame, Name);
		}
		if (newGame.equals("NT")) {
			this.tetris.dispose();
			this.tetris.newGame(Name);
			
		}
		else {
			this.createGame(newGame, Name);
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
				Running = true;
				

				
				
				
			}
			String command = scan.nextLine();
			if (command.equals("NewGame")) {
				System.out.println("Enter your new player name");
				String newPlayer = scan.nextLine();
				
				System.out.println("Select from the following games: \n1. Tetris -> Enter NT\n2. Bejewel -> Enter NB");
				String newGame = scan.next();
				
				m.resetGame(newGame, newPlayer);
				System.out.println("Enter NewGame to select a new Game");

			}
			

		}
		

		
		
	}
}
