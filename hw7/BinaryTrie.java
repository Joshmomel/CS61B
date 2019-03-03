

import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    private Map<Character, Integer> frequencyTable;
    private Character[] keys;
    private Node trie;

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.frequencyTable = frequencyTable;
        Object[] ts = frequencyTable.keySet().toArray();
        this.keys = Arrays.copyOf(ts, ts.length, Character[].class);
        trie = buildTrie(keys);
    }

    private Node buildTrie(Character[] keys) {
        MinPQ<Node> pq = new MinPQ<>();
        // initialze priority queue with singleton trees
        for (int i = 0; i < keys.length; i++) {
            int frequency = frequencyTable.get(keys[i]);
            pq.insert(new Node(keys[i], frequency, null, null));
        }
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }



    public Match longestPrefixMatch(BitSequence querySequence) {
        Node p = this.trie;
        String store = "";
        BitSequence s;
        for (int i = 0; i < querySequence.length(); i++) {
            int num = querySequence.bitAt(i);
            store += Integer.toString(num);
            p = getPosition(p, num);
            if (frequencyTable.containsKey(p.ch)) {
                Character c = p.ch;
                s = new BitSequence(store);
                return new Match(s, c);
            }
        }
        return null;
    }

    private Node getPosition(Node node, int num) {
        if (num == 0) {
            return node.left;
        }
        return node.right;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> map = new HashMap<>();
        buildTableHelper(map, trie, "");
        return map;
    }

    private void buildTableHelper(Map map, Node node, String s) {
        if (!node.isLeaf()) {
            buildTableHelper(map, node.left,  s + '0');
            buildTableHelper(map, node.right, s + '1');
        } else {
            map.put(node.ch, new BitSequence(s));
        }
    }
}