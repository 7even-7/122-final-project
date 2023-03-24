public class T_Operations {

    public T_Operations() {
    }

    public void updateBoard(int[][] block, int[][][] blocks, int[][] data, int m, int n) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                    data[m + i][n + j] = 1;
                }
            }
        }
    }

    public boolean canFall(int[][] block, int[][][] blocks, int[][] data, int m, int n) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                    if (data[m + i + 1][n + j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}