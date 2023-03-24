import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class T_Scoring extends Score {
    private List<ScoringObserver> observers;

    public T_Scoring() {
        observers = new ArrayList<>();
    }

    public void addObserver(ScoringObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScoringObserver observer) {
        observers.remove(observer);
    }

    public void isFullRow(int x, int[][] data, JTextArea[][] text, int length, int score, JLabel game_score) {
        int point = 100;

        int checker = x;
        while (checker < x + 4) {
            int sum = 0;
            for (int i = 1; i <= length; i++) {
                if (data[checker][i] == 1) {
                    sum++;
                }
            }

            if (sum == length) {
                removeRow(length, text, data, checker);
                this.incrementScore(point);
                game_score.setText("Game Score: " + this.score);
                notifyObservers(this.score);
            }
            checker++;
        }
    }

    public void removeRow(int length, JTextArea[][] text, int[][] data, int row) {
        for (int i = row; i >= 1; i--) {
            for (int j = 1; j <= length; j++) {
                data[i][j] = data[i - 1][j];
            }
        }
        reSetGame(length, text, data, row);
    }

    public void reSetGame(int length, JTextArea[][] text, int[][] data, int row) {
        int i = row;
        do {
            for (int j = 1; j <= length; j++) {
                setColor(data, text, i, j);
            }
            i--;
        } while (i >= 1);
    }

    public void setColor(int[][] data, JTextArea[][] text, int m, int n) {
        if (data[m][n] == 1) {
            text[m][n].setBackground(Color.ORANGE);
        } else {
            text[m][n].setBackground(Color.WHITE);
        }
    }

    private void notifyObservers(int score) {
        for (ScoringObserver observer : observers) {
            observer.updateScore(score);
        }
    }

    public int getScore() {
        return this.score;
    }

    public interface ScoringObserver {
        void updateScore(int score);
    }

    @Override
    void incrementScore(int num) {
        this.score += num;
    }
}
