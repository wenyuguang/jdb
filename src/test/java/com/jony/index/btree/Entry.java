package com.jony.index.btree;

public class Entry {

    // 关键字
    public int key;
    // 值
    public Object value;
    // 下一个结点指针
    public Node next;

    public Entry() {
    }

    public Entry(int key, Object value, Node next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value=" + value +
                ", next=" + next +
                '}';
    }
}
