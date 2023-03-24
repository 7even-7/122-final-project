public class RotateShape {
    private int[][][] blocks;

    public RotateShape(int[][][] blocks) {
        this.blocks = blocks;
    }

    public int[][] changeBlock(int current) {
        return blocks[current + 1];
    }

    public int[][] rotate(int currentBlock, int[][] data, int x, int y, T_Paint tPaint) {
        BlockState blockState = BlockStateFactory.getBlockState(currentBlock);
        int[][] next = blockState.getNextBlock(currentBlock, blocks);

        if (tPaint.checkTurn(next, data, x, y)) {
            return next;
        }

        return null;
    }

    interface BlockState {
        int[][] getNextBlock(int currentBlock, int[][][] blocks);
    }

    static class BlockStateFactory {
        public static BlockState getBlockState(int currentBlock) {
            if (currentBlock >= 0 && currentBlock <= 3) {
                return new BlockState0To3();
            } else if (currentBlock >= 4 && currentBlock <= 7) {
                return new BlockState4To7();
            } else if (currentBlock >= 8 && currentBlock <= 9) {
                return new BlockState8To9();
            } else if (currentBlock >= 10 && currentBlock <= 13) {
                return new BlockState10To13();
            } else if (currentBlock >= 14 && currentBlock <= 15) {
                return new BlockState14To15();
            }
            throw new IllegalArgumentException("Invalid block range");
        }
    }

    static class BlockState0To3 implements BlockState {
        @Override
        public int[][] getNextBlock(int currentBlock, int[][][] blocks) {
            return currentBlock < 3 ? blocks[currentBlock + 1] : blocks[0];
        }
    }

    static class BlockState4To7 implements BlockState {
        @Override
        public int[][] getNextBlock(int currentBlock, int[][][] blocks) {
            return currentBlock < 7 ? blocks[currentBlock + 1] : blocks[4];
        }
    }

    static class BlockState8To9 implements BlockState {
        @Override
        public int[][] getNextBlock(int currentBlock, int[][][] blocks) {
            return currentBlock < 9 ? blocks[currentBlock + 1] : blocks[8];
        }
    }

    static class BlockState10To13 implements BlockState {
        @Override
        public int[][] getNextBlock(int currentBlock, int[][][] blocks) {
            return currentBlock < 13 ? blocks[currentBlock + 1] : blocks[10];
        }
    }

    static class BlockState14To15 implements BlockState {
        @Override
        public int[][] getNextBlock(int currentBlock, int[][][] blocks) {
            return currentBlock < 15 ? blocks[currentBlock + 1] : blocks[14];
        }
    }
}
