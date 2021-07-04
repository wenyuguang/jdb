package com.jony.index;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    // 是否为叶子结点
    private boolean isLeaf = false;
    // 节点数据总数
    private int count;
    // 节点数据
    private List<Entry<String, String>> entries = new ArrayList<>();
    // 内节点的子节点，为叶子结点时不用
    private List<BTreeNode> child = new ArrayList<>();

    public BTreeNode() {
        this.count = 1;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Entry<String, String>> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry<String, String>> entries) {
        this.entries = entries;
    }

    public List<BTreeNode> getChild() {
        return child;
    }

    public void setChild(List<BTreeNode> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "BTreeNode{" +
                "isLeaf=" + isLeaf +
                ", count=" + count +
                ", entries=" + entries +
                ", child=" + child +
                '}';
    }
}
class Entry<K, V> {
    private K key;
    private V value;
    private BTreeNode next;

    public Entry(K k, V v, BTreeNode next) {
        this.key = k;
        this.value = v;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public BTreeNode getNext() {
        return next;
    }

    public void setNext(BTreeNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}