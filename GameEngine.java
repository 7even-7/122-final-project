
public interface GameEngine {
	abstract public void createCell();
	abstract public void getCell();
	abstract public void createboard();
	abstract boolean checkConnected();
	abstract int displayScore();
	abstract void incrementScore(int num);
	abstract void gameOver();
	abstract void endGame();

}
