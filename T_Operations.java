public class T_Operations {

    public T_Operations() {
    }

    public void updateBoard(int[][] block, int[][][] blocks, int[][] data, int blockXCoordinate, int blockYCoordinate) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                    data[blockXCoordinate + i][blockYCoordinate + j] = 1;
                }
            }
        }
    }

    public boolean canFall(int[][] block, int[][][] blocks, int[][] data, int blockXCoordinate, int blockYCoordinate) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (block[i][j] != 0) {
                    if (data[blockXCoordinate + i + 1][blockYCoordinate + j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}