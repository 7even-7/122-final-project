import java.awt.Color;
import javax.swing.JButton;

interface MatchStrategy {
    int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile);
}

class UpMatchStrategy implements MatchStrategy {
    public int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        r--;
        while (0 <= r) {
            Color currCellColor = buttons[r][c].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            r--;
        }
        return res;
    }
}

class DownMatchStrategy implements MatchStrategy {
    public int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        r++;
        while (r < rowsOrColumns) {
            Color currCellColor = buttons[r][c].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            r++;
        }
        return res;
    }
}

class LeftMatchStrategy implements MatchStrategy {
    public int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        c--;
        while (0 <= c) {
            Color currCellColor = buttons[r][c].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            c--;
        }
        return res;
    }
}

class RightMatchStrategy implements MatchStrategy {
    public int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        c++;
        while (c < rowsOrColumns) {
            Color currCellColor = buttons[r][c].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            c++;
        }
        return res;
    }
}

public class calculateMatch {
    private MatchStrategy upMatchStrategy;
    private MatchStrategy downMatchStrategy;
    private MatchStrategy leftMatchStrategy;
    private MatchStrategy rightMatchStrategy;

    public calculateMatch() {
        upMatchStrategy = new UpMatchStrategy();
        downMatchStrategy = new DownMatchStrategy();
        leftMatchStrategy = new LeftMatchStrategy();
        rightMatchStrategy = new RightMatchStrategy();
    }

    public int getUpMatch(int r, int c, JButton[][] buttons, Color markedTile) {
        return upMatchStrategy.getMatch(r, c, buttons.length, buttons, markedTile);
    }

    public int getDownMatch(int r, int c, int rowLen, JButton[][] buttons, Color markedTile) {
        return downMatchStrategy.getMatch(r, c, rowLen, buttons, markedTile);
    }

    public int getLeftMatch(int r, int c, JButton[][] buttons, Color markedTile) {
        return leftMatchStrategy.getMatch(r, c, buttons[0].length, buttons, markedTile);
    }

    public int getRightMatch(int r, int c, int colLen, JButton[][] buttons, Color markedTile) {
        return rightMatchStrategy.getMatch(r, c, colLen, buttons, markedTile);
    }

    public int[] getHorizontalMatch(int r, int c, int columns, JButton[][] buttons, Color markedTile) {
        // Get the left and right matching tiles and calculate the start and end position
        int left = this.getLeftMatch(r, c, buttons, markedTile);
        int right = this.getRightMatch(r, c, columns, buttons, markedTile);
        int startTilePosition = c - left;
        int endTilePosition = c + right;
        int score = left + 1 + right;
        return new int[]{startTilePosition, endTilePosition, score, r};
    }

    public int[] getVerticalMatch(int r, int c, int rows, JButton[][] buttons, Color markedTile) {
        // Get the up and down matching tiles and calculate the start and end position
        int up = this.getUpMatch(r, c, buttons, markedTile);
        int down = this.getDownMatch(r, c, rows, buttons, markedTile);
        int startTilePosition = r - up;
        int endTilePosition = r + down;
        int score = up + 1 + down;
        return new int[]{startTilePosition, endTilePosition, score, c};
    }
}