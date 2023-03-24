import java.awt.Color;
import javax.swing.JButton;

interface MatchStrategy {
    int getMatch(int r, int c, int rowsOrColumns, JButton[][] buttons, Color markedTile);
}

class UpMatchStrategy implements MatchStrategy {
    public int getMatch(int row, int col, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        row--;
        while (0 <= row) {
            Color currCellColor = buttons[row][col].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            row--;
        }
        return res;
    }
}

class DownMatchStrategy implements MatchStrategy {
    public int getMatch(int row, int col, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        row++;
        while (row < rowsOrColumns) {
            Color currCellColor = buttons[row][col].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            row++;
        }
        return res;
    }
}

class LeftMatchStrategy implements MatchStrategy {
    public int getMatch(int row, int col, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        col--;
        while (0 <= col) {
            Color currCellColor = buttons[row][col].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            col--;
        }
        return res;
    }
}

class RightMatchStrategy implements MatchStrategy {
    public int getMatch(int row, int col, int rowsOrColumns, JButton[][] buttons, Color markedTile) {
        int res = 0;
        col++;
        while (col < rowsOrColumns) {
            Color currCellColor = buttons[row][col].getBackground();
            if (currCellColor == markedTile) {
                res++;
            } else {
                break;
            }
            col++;
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

    public int getUpMatch(int row, int col, JButton[][] buttons, Color markedTile) {
        return upMatchStrategy.getMatch(row, col, buttons.length, buttons, markedTile);
    }

    public int getDownMatch(int row, int col, int rowLen, JButton[][] buttons, Color markedTile) {
        return downMatchStrategy.getMatch(row, col, rowLen, buttons, markedTile);
    }

    public int getLeftMatch(int row, int col, JButton[][] buttons, Color markedTile) {
        return leftMatchStrategy.getMatch(row, col, buttons[0].length, buttons, markedTile);
    }

    public int getRightMatch(int row, int col, int colLen, JButton[][] buttons, Color markedTile) {
        return rightMatchStrategy.getMatch(row, col, colLen, buttons, markedTile);
    }

    public int[] getHorizontalMatch(int row, int col, int columns, JButton[][] buttons, Color markedTile) {
        // Get the left and right matching tiles and calculate the start and end position
        int left = this.getLeftMatch(row, col, buttons, markedTile);
        int right = this.getRightMatch(row, col, columns, buttons, markedTile);
        int startTilePosition = col - left;
        int endTilePosition = col + right;
        int score = left + 1 + right;
        return new int[]{startTilePosition, endTilePosition, score, row};
    }

    public int[] getVerticalMatch(int row, int col, int rows, JButton[][] buttons, Color markedTile) {
        // Get the up and down matching tiles and calculate the start and end position
        int up = this.getUpMatch(row, col, buttons, markedTile);
        int down = this.getDownMatch(row, col, rows, buttons, markedTile);
        int startTilePosition = row - up;
        int endTilePosition = row + down;
        int score = up + 1 + down;
        return new int[]{startTilePosition, endTilePosition, score, col};
    }
}