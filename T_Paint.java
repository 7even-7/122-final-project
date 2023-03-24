import javax.swing.*;
import java.awt.*;

public class T_Paint {

    public void cleaning(int[][] block, JTextArea[][] text, int[][] data, int m, int n) {
        int i = 0;
        while (i < 16) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[m][n].setBackground(Color.WHITE);
            }
            n++;
            if (col == 3) {
                m++;
                n -= 4;
            }
            i++;
        }
    }

    public void drawing(int[][] block, JTextArea[][] text, int m, int n) {
        for (int i = 0; i < 16; i++) {
            int row = i / 4;
            int col = i % 4;
            if (block[row][col] != 0) {
                text[m][n].setBackground(Color.ORANGE);
            }
            n++;
            if (i % 4 == 3) {
                m++;
                n -= 4;
            }
        }
    }

    public boolean checkTurn(int[][] a, int[][] data, int m, int n) {
        for (int i = 0; i < 16; i++) {
            int row = m + i / 4;
            int col = n + i % 4;
            if (a[i / 4][i % 4] != 0 && data[row][col] == 1) {
                return false;
            }
        }
        return true;
    }
}


