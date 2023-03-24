public class Player {
    private final String name;
    private Integer score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void setScore(int num) {
        this.score = num;
    }

    public String getName() {
        return this.name;
    }

    public Integer getScore() {
        return this.score;
    }

    public Player getPlayer() {
        return this;
    }
}
