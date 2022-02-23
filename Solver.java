/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private Board[] solutions;
    private int numOfSteps = 0;

    private class SearchNode {
        public Board current;
        public int moves;
        public Board previous;
        public int priority;

        public SearchNode(Board current, int moves, Board previous) {
            this.current = current;
            this.moves = moves;
            this.previous = previous;

            this.priority = moves + current.manhattan();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ minPq = new MinPQ();
        SearchNode initNode = new SearchNode(initial, 0, null);
        minPq.insert(initNode);

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return numOfSteps;
    }

    // sequence of board in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        return null;
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        return;
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);
        //
        // // solve the puzzle
        // Solver solver = new Solver(initial);
        //
        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }
}
