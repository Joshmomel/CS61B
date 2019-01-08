package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int s) {
        Queue<Integer> fringe = new Queue<>();
        fringe.enqueue(s);
        marked[s] = true;
        announce();

        while (!fringe.isEmpty() && !targetFound) {
            int v = fringe.dequeue();
            for (Integer w : maze.adj(v)) {
                if (v == t) {
                    targetFound = true;
                    break;
                }

                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    fringe.enqueue(w);
                    distTo[w] = distTo[v] + 1;
                    announce();
                }
            }
        }


    }


    @Override
    public void solve() {
         bfs(s);
    }
}

