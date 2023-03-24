public interface GameEngine {
    abstract void createboard();

    abstract void init();

    abstract void isGameOver();

    abstract void displayScoreOnboard();

    abstract void setColor();

    abstract void updateBoard();
}
