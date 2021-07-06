package com.jony.index.btree;

import java.util.Arrays;

public class Node {

    // 当前节点包含数据总数
    public int currentEntrySize;
    // 子节点总数
    public int childrenNodeSize;

    public Entry[] currentEntry = new Entry[3];

    public Node[] childrens = new Node[4];

    @Override
    public String toString() {
        return "Node{" +
                "currentEntrySize=" + currentEntrySize +
                ", childrenNodeSize=" + childrenNodeSize +
                ", currentEntry=" + Arrays.toString(currentEntry) +
                ", childrens=" + Arrays.toString(childrens) +
                '}';
    }
}
