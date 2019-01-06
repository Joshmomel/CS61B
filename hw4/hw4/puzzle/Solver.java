package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;


public class Solver {
    private WorldState worldState;
    private Stack<WorldState> states;


    public Solver(WorldState initial) {
        worldState = initial;
        states = new Stack<>();

        MinPQ<Node> minPQ = new MinPQ<>();
        Node firstNode = new Node(this.worldState, 0, null);
        minPQ.insert(firstNode);

        Node node;
        Node goal = null;
        while (!minPQ.isEmpty()) {
            node = minPQ.delMin();
            if (node.worldState.isGoal()) {
                goal = node;
                break;
            }

            for (WorldState neighbor : node.worldState.neighbors()) {
                if (node.parent == null ||
                        (node.parent != null && !neighbor.equals(node.parent.worldState))) {
                    minPQ.insert(new Node(neighbor, node.move + 1, node));
                }
            }
        }

        while (goal != null) {
            states.push(goal.worldState);
            goal = goal.parent;
        }
    }


    public int moves() {
        return states.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return this.states;
    }

    private class Node implements Comparable<Node> {
        private WorldState worldState;
        private int move;
        private Node parent;
        private int priority;

        private Node(WorldState worldState, int move, Node parent) {
            this.worldState = worldState;
            this.move = move;
            this.parent = parent;
            this.priority = this.move + this.worldState.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(Node n) {
            return this.priority - n.priority;
        }
    }


}
