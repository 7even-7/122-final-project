import java.util.ArrayList;
import java.util.Collections;

public class Winner {
    private final ArrayList<Player> players;
    boolean empty = true;

    public Winner() {
        this.players = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        this.empty = false;
        this.players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        Collections.sort(this.players,
                (o1, o2) -> o1.getScore().compareTo(o2.getScore()));
        return this.players;
    }

    public boolean isEmpty() {
        return this.empty;
    }

}
