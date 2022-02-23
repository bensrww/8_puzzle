/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private int[][] boardTiles;
    private int dimension;
    private int hamming;
    private int manhattan;

    // create a board from n-by-n array of tiles,
    // where tiles[row][col]] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        int[][] constructionTiles = new int[dimension][dimension];
        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                constructionTiles[row][col] = tiles[row][col];
            }
        }
        boardTiles = constructionTiles;
        hamming = hamming();
        manhattan = manhattan();
    }

    // string representation of this board
    public String toString() {
        System.out.println(dimension);
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                s.append(String.format("%2d ", boardTiles[row][col]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                final int targetPosition = row * dimension + col + 1;
                if (boardTiles[row][col] != targetPosition && (
                        row != dimension - 1 && col != dimension - 1
                )) {
                    hammingDistance += 1;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhanttanDistance = 0;
        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                final int targetPosition = row * dimension + col + 1;
                final int currentElement = boardTiles[row][col];
                if (currentElement != targetPosition && row != dimension - 1
                        && col != dimension - 1) {
                    final int targetRowIndex = currentElement / dimension;
                    final int targetColIndex = currentElement % dimension - 1;
                    manhanttanDistance += Math.abs(row - targetRowIndex);
                    manhanttanDistance += Math.abs(col - targetColIndex);
                }
            }
        }
        return manhanttanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;

        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                if (this.boardTiles[row][col] != that.boardTiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterator<Board> neighbors() {
        return new NeighborsIterator();
    }

    public int[][] createDuplicateTiles() {
        int[][] constructionTiles = new int[dimension][dimension];
        for (int row = 0; row < dimension; row += 1) {
            constructionTiles[row] = Arrays.copyOf(boardTiles[row], dimension);
        }
        return constructionTiles;
    }

    public void swapTile(int[][] array, int thisRow, int thisCol, int thatRow, int thatCol) {
        int temp = array[thisRow][thisCol];
        array[thisRow][thisCol] = array[thatRow][thatCol];
        array[thatRow][thatCol] = temp;
    }

    private class NeighborsIterator implements Iterator<Board> {
        Board[] neighbors = new Board[4];
        int index = 0;
        int emptyRowIndex;
        int emptyColIndex;

        public void findEmptyTile() {
            for (int row = 0; row < dimension; row += 1) {
                for (int col = 0; col < dimension; col += 1) {
                    if (boardTiles[row][col] == 0) {
                        emptyRowIndex = row;
                        emptyColIndex = col;
                        return;
                    }
                }
            }
        }

        public Board getUpNeighbor() {
            int[][] duplicate = createDuplicateTiles();
            swapTile(duplicate, emptyRowIndex, emptyColIndex, emptyRowIndex - 1,
                     emptyColIndex);
            return new Board(duplicate);
        }

        public Board getDownNeighbor() {
            int[][] duplicate = createDuplicateTiles();
            swapTile(duplicate, emptyRowIndex, emptyColIndex, emptyRowIndex + 1,
                     emptyColIndex);
            return new Board(duplicate);
        }

        public Board getLeftNeighbor() {
            int[][] duplicate = createDuplicateTiles();
            swapTile(duplicate, emptyRowIndex, emptyColIndex, emptyRowIndex,
                     emptyColIndex - 1);
            return new Board(duplicate);
        }

        public Board getRightNeighbor() {
            int[][] duplicate = createDuplicateTiles();
            swapTile(duplicate, emptyRowIndex, emptyColIndex, emptyRowIndex,
                     emptyColIndex + 1);
            return new Board(duplicate);
        }

        public NeighborsIterator() {
            findEmptyTile();

            // Empty cell at corners
            // Empty cell at top-left
            if (emptyRowIndex == 0 && emptyColIndex == 0) {
                // Swap empty with right
                neighbors[0] = getRightNeighbor();
                // Swap empty with below
                neighbors[1] = getDownNeighbor();
            }

            // Empty cell at top-right
            else if (emptyRowIndex == 0 && emptyColIndex == dimension - 1) {
                neighbors[0] = getLeftNeighbor();
                neighbors[1] = getDownNeighbor();
            }

            // Empty cell at bottom-left
            else if (emptyRowIndex == dimension - 1 && emptyColIndex == 0) {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getRightNeighbor();
            }

            // Empty cell at bottom-right
            else if (emptyRowIndex == dimension - 1 && emptyColIndex == dimension - 1) {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getLeftNeighbor();
            }

            // ----------------------------------------------------------------------------------
            // Empty cell at 4 sides, but not corners

            // Empty cell at top row
            else if (emptyRowIndex == 0) {
                neighbors[0] = getDownNeighbor();
                neighbors[1] = getLeftNeighbor();
                neighbors[2] = getRightNeighbor();
            }

            // Empty cell at bottom row
            else if (emptyRowIndex == dimension - 1) {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getLeftNeighbor();
                neighbors[2] = getRightNeighbor();
            }

            // Empty cell at left col
            else if (emptyColIndex == 0) {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getDownNeighbor();
                neighbors[2] = getRightNeighbor();
            }

            // Empty cell at right col
            else if (emptyColIndex == dimension - 1) {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getDownNeighbor();
                neighbors[2] = getLeftNeighbor();
            }

            // Empty cell can be swapped with 4 surrounding positions
            else {
                neighbors[0] = getUpNeighbor();
                neighbors[1] = getDownNeighbor();
                neighbors[2] = getLeftNeighbor();
                neighbors[3] = getRightNeighbor();
            }
        }

        public boolean hasNext() {
            if (index >= 4) return false;
            return neighbors[index] != null;
        }

        public Board next() {
            Board currentBoard = neighbors[index];
            index += 1;
            return currentBoard;
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] duplicate = createDuplicateTiles();
        int sourceRow = StdRandom.uniform(dimension);
        int sourceCol = StdRandom.uniform(dimension);
        int destRow = StdRandom.uniform(dimension);
        int destCol = StdRandom.uniform(dimension);

        while (duplicate[sourceRow][sourceCol] == 0 || duplicate[destRow][destCol] == 0 || (
                sourceRow == destRow && sourceCol == destCol)) {
            sourceRow = StdRandom.uniform(dimension);
            sourceCol = StdRandom.uniform(dimension);
            destRow = StdRandom.uniform(dimension);
            destCol = StdRandom.uniform(dimension);
        }

        StdRandom.uniform(dimension);
        swapTile(duplicate, sourceRow, sourceCol, destRow,
                 destCol);
        return new Board(duplicate);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        for (String filename : args) {
            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println(initial.toString());

            Iterator<Board> i = initial.neighbors();
            while (i.hasNext()) {
                Board b = i.next();
                System.out.println(b);
            }
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());

        }
    }
}
