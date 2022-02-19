/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

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
        boardTiles = tiles;
        dimension = tiles.length;
        hamming = hamming();
        manhattan = manhattan();
    }

    // string representation of this board
    public String toString() {
        System.out.println(dimension);
        for (int row = 0; row < dimension; row += 1) {
            for (int col = 0; col < dimension; col += 1) {
                System.out.print(boardTiles[row][col] + " ");
            }
            System.out.println();
        }
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

        public void swapTile(int[][] array, int thisRow, int thisCol, int thatRow, int thatCol) {
            int temp = array[thisRow][thisCol];
            array[thisRow][thisCol] = array[thatRow][thatCol];
            array[thatRow][thatCol] = temp;
        }

        public int[][] createDuplicateTiles() {
            int[][] constructionTiles = new int[dimension][dimension];
            for (int row = 0; row < dimension; row += 1) {
                constructionTiles[row] = Arrays.copyOf(boardTiles[row], dimension);
            }
            return constructionTiles;
        }

        public NeighborsIterator() {
            findEmptyTile();

            if (emptyRowIndex == 0 && emptyColIndex == 0) {

            }
            neighbors[boardIndex] = new Board(constructionTiles);
        }

        public boolean hasNext() {
            return neighbors[index] != null;
        }

        public Board next() {
            int currentIndex = index;
            index += 1;
            return neighbors[currentIndex];
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

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
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());

        }
    }
}
