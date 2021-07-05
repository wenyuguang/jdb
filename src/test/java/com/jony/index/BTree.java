package com.jony.index;

/**
 *  The {@code BTree} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>size</em>, and <em>is-empty</em> methods.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a B-tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  The <em>get</em>, <em>put</em>, and <em>contains</em> operations
 *  each make log<sub><em>m</em></sub>(<em>n</em>) probes in the worst case,
 *  where <em>n</em> is the number of key-value pairs
 *  and <em>m</em> is the branching factor.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/62btree">Section 6.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class BTree<Key extends Comparable<Key>, Value>  {

    /**
     * max children per B-tree node = M-1
     * (must be even and greater than 2)
     */
    private static final int M = 4;

    /**
     * root of the B-tree
     */
    private Node root;
    /**
     * height of the B-tree
     */
    private int height;
    /**
     * number of key-value pairs in the B-tree
     */
    private int size;
    /**
     * key是否重复
     */
    boolean isRepeatKey = false;

    /**
     * helper B-tree node data type
     */
    private static final class Node {
        /**
         * number of children
         */
        private int childrenNum;
        /**
         * the array of children
         */
        final private Entry[] children = new Entry[M];

        /**
         * create a node with childrenNum children
         * @param childrenNum count
         */
        private Node(int childrenNum) {
            this.childrenNum = childrenNum;
        }
    }

    /**
     * internal nodes: only use key and next
     * external nodes: only use key and value
     */
    private static class Entry {

        private Comparable key;
        private Object val;
        /**
         * helper field to iterate over array entries
         */
        private Node next;
        public Entry(Comparable key, Object val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty B-tree.
     */
    public BTree() {
        root = new Node(0);
    }

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }


    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null){
            throw new IllegalArgumentException("argument to get() is null");
        }
        return search(root, key, height);
    }

    private Value search(Node node, Key key, int height) {

        Entry[] children = node.children;

        // external node
        if (height == 0) {
            for (int j = 0; j < node.childrenNum; j ++) {
                if (equal(key, children[j].key)) {
                    return (Value) children[j].val;
                }
            }
        }

        // internal node
        else {
            for (int j = 0; j < node.childrenNum; j ++) {
                if (j + 1 == node.childrenNum || less(key, children[j + 1].key))
                    return search(children[j].next, key, height - 1);
            }
        }
        return null;
    }


    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null){
            throw new IllegalArgumentException("argument key to put() is null");
        }
        Node splitedNode = insert(root, key, val, height);
        if (!isRepeatKey){
            size ++;
        }
        if (splitedNode == null){
            return;
        }

        // need to split root
        Node tmpRoot = new Node(2);
        tmpRoot.children[0] = new Entry(root.children[0].key, null, root);
        tmpRoot.children[1] = new Entry(splitedNode.children[0].key, null, splitedNode);
        root = tmpRoot;
        height ++;
    }

    /**
     *
     * @param node
     * @param key
     * @param val
     * @param height
     * @return
     */
    private Node insert(Node node, Key key, Value val, int height) {
        int j;
        Entry entry = new Entry(key, val, null);

        // key去重
        isRepeatKey = false;
        for (int i = 0; i < node.childrenNum; i++) {
            if (equal(node.children[i].key, key)) {
                isRepeatKey = true;

                entry.next = node.children[i].next;
                node.children[i] = entry;
                return null;
            }
        }

        // external node
        if (height == 0) {
            for (j = 0; j < node.childrenNum; j ++) {
                if (less(key, node.children[j].key)){
                    break;
                }
            }
        }

        // internal node
        else {
            for (j = 0; j < node.childrenNum; j ++) {
                if ((j + 1 == node.childrenNum) || less(key, node.children[j + 1].key)) {
                    // 递归查找，树的高度减1
                    Node splitedNode = insert(node.children[j ++].next, key, val, height - 1);
                    if (splitedNode == null) {
                        return null;
                    }
                    entry.key = splitedNode.children[0].key;
                    entry.val = null;
                    entry.next = splitedNode;
                    break;
                }
            }
        }

        for (int i = node.childrenNum; i > j; i --){
            node.children[i] = node.children[i - 1];
        }
        node.children[j] = entry;
        node.childrenNum ++;
        if (node.childrenNum < M){
            return null;
        }else {
            return split(node);
        }
    }

    /**
     * split node in half
     * @param node
     * @return
     */
    private Node split(Node node) {

        Node newNode = new Node(M / 2);
        node.childrenNum = M / 2;
        for (int j = 0; j < M / 2; j ++){
            newNode.children[j] = node.children[M / 2 + j];
        }
        return newNode;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node node, int height, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = node.children;

        if (height == 0) {
            for (int j = 0; j < node.childrenNum; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        }
        else {
            for (int j = 0; j < node.childrenNum; j++) {
                if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
                s.append(toString(children[j].next, height-1, indent + "     "));
            }
        }
        return s.toString();
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean equal(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }


    /**
     * Unit tests the {@code BTree} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        BTree<String, String> st = new BTree<String, String>();
        st.put("1", "1");
        st.put("2", "2");
        st.put("3", "3");
        st.put("4", "4");
        st.put("5", "5");
        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu",    "128.112.128.15");
        st.put("www.cs.princeton.edu", "128.112.136.11231");
        st.put("www.yale.edu",         "130.132.143.21");
        st.put("www.simpsons.com",     "209.052.165.60");
        st.put("www.apple.com",        "17.112.152.32");
        st.put("www.amazon.com",       "207.171.182.16");
        st.put("www.ebay.com",         "66.135.192.87");
        st.put("www.cnn.com",          "64.236.16.20");
        st.put("www.google.com",       "216.239.41.99");
        st.put("www.nytimes.com",      "199.239.136.200");
        st.put("www.microsoft.com",    "207.126.99.140");
        st.put("www.dell.com",         "143.166.224.230");
        st.put("www.slashdot.org",     "66.35.250.151");
        st.put("www.espn.com",         "199.181.135.201");
        st.put("www.weather.com",      "63.111.66.11");
        st.put("www.yahoo.com",        "216.109.118.65");
        st.put("123", "123");
        st.put("123", "4534");
        st.put("123", "adfgfg");


        System.out.println("123:  " + st.get("123"));
        System.out.println(" cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();
    }
}