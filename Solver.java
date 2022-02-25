/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private Board[] solutions;
    private int numOfSteps = 0;

    private class SearchNode {
        public Board board;
        public int moves;
        public Board previous;
        public int priority;

        public SearchNode(Board current, int moves, Board previous) {
            this.board = current;
            this.moves = moves;
            this.previous = previous;

            this.priority = moves + current.manhattan();
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            if ((a.priority - b.priority) > 0) return 1;
            if ((a.priority - b.priority) < 0) return -1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        solutions = new Board[64];
        MinPQ<SearchNode> minPq = new MinPQ<SearchNode>(new SearchNodeComparator());
        SearchNode initNode = new SearchNode(initial, 0, null);
        minPq.insert(initNode);

        while (true) {
            Board minBoard = minPq.delMin().board;
            if (minBoard.isGoal()) {
                break;
            }
            solutions[numOfSteps] = minBoard;
            Iterator<Board> neighborsIterator = minBoard.neighbors();
            while (neighborsIterator.hasNext()) {
                Board neighbor = neighborsIterator.next();
                SearchNode node = new SearchNode(neighbor, numOfSteps + 1, minBoard);
                if (!node.board.equals(node.previous)) {
                    minPq.insert(node);
                }
            }
            numOfSteps += 1;
        }
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

    private class SolutionItertor implements Iterator<Board> {
        private int index = 0;

        public boolean hasNext() {
            if (index >= numOfSteps) return false;
            return solutions[index] != null;
        }

        public Board next() {
            Board nextBoard = solutions[index];
            index += 1;
            return nextBoard;
        }
    }

    private class SolutionIterable implements Iterable<Board> {
        public Iterator iterator() {
            return new SolutionItertor();
        }
    }

    // sequence of board in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return new SolutionIterable();
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
