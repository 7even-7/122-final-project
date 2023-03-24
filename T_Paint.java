import javax.swing.*;
import java.awt.*;

public class T_Paint {

    public void cleaning(int[][] block, JTextArea[][] text, int[][] data, int blockXCoordinate, int blockYCoordinate) {
        int i = 0;
        while (i < 16) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[blockXCoordinate][blockYCoordinate].setBackground(Color.WHITE);
            }
            blockYCoordinate++;
            if (col == 3) {
                blockXCoordinate++;
                blockYCoordinate -= 4;
            }
            i++;
        }
    }

    public void drawing(int[][] block, JTextArea[][] text, int blockXCoordinate, int blockYCoordinate) {
        for (int i = 0; i < 16; i++) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[blockXCoordinate][blockYCoordinate].setBackground(Color.ORANGE);
            }
            blockYCoordinate++;
            if (i % 4 == 3) {
                blockXCoordinate++;
                blockYCoordinate -= 4;
            }
        }
    }

    public boolean checkTurn(int[][] nextBlockRotation, int[][] data, int blockXCoordinate, int blockYCoordinate) {
        for (int i = 0; i < 16; i++) {
            int row = blockXCoordinate + i / 4;
            int col = blockYCoordinate + i % 4;
            if (nextBlockRotation[i / 4][i % 4] != 0 && data[row][col] == 1) {
                return false;
            }
        }
        return true;
    }
}


