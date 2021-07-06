package com.jony.index.btree;

public class BTree {

    // 当前树的阶 默认为3
    private int order = 3;
    // 树的高度
    private int treeHeight;
    // 根节点
    private Node root;
    // 结点总数
    private int nodeSize;

    public BTree() {
        treeHeight = 0;
        nodeSize = 0;
        root = new Node();
    }

    // 插入数据
    public void put(int key, Object val){
        Node node = putVal(root, key, val, treeHeight);

        if (node == null){
            return;
        }
        root = node;
        // 分裂
        if (node.currentEntrySize == 3){
            Node tmpNode = new Node();
            Node leftNode = new Node();
            Node rightNode = new Node();

            leftNode.currentEntrySize ++;
            rightNode.currentEntrySize ++;
            // 现在是3阶，先写死
            leftNode.currentEntry[0] = node.currentEntry[0];
            leftNode.childrens[0] = node.childrens[0];
            leftNode.childrens[1] = node.childrens[1];

            rightNode.currentEntry[0] = node.currentEntry[2];
            rightNode.childrens[0] = node.childrens[2];
            rightNode.childrens[1] = node.childrens[3];

            // 设置父节点自己点数
            tmpNode.childrenNodeSize = 2;
            // 把要上移的数据赋值给父结点
            tmpNode.currentEntry[0] = node.currentEntry[order / 2];
            tmpNode.childrenNodeSize = 2;
            tmpNode.currentEntrySize ++;

            tmpNode.childrens[0] = leftNode;
            tmpNode.childrens[1] = rightNode;

            leftNode.childrenNodeSize = 2;
            rightNode.childrenNodeSize = 2;

            root = tmpNode;
        }

    }

    public static void main(String[] args) {
        BTree bTree = new BTree();
        bTree.put(1, "1");
        bTree.put(2, "2");
        bTree.put(3, "3");
        bTree.put(4, "4");
        bTree.put(5, "5");
        bTree.put(6, "6");
        bTree.put(7, "7");
        bTree.put(8, "8");
        bTree.put(9, "9");
        bTree.put(10, "10");
        bTree.put(11, "11");
        bTree.put(12, "12");
        bTree.put(13, "13");
        bTree.put(14, "14");
        bTree.put(15, "15");
        bTree.put(16, "15");
    }

    private Node putVal(Node node, int key, Object val, int treeHeight) {

        Entry newEntry = new Entry(key, val, null);

        // 当前结点为叶子结点且包含数据总数小于阶，直接添加到当前结点上
        if (node.childrenNodeSize == 0){
            if ( node.currentEntrySize < 3){
                if (node.currentEntrySize == 0){
                    node.currentEntrySize ++;
                    node.currentEntry[0] = newEntry;
                    root = node;
                    return null;
                }
                // 排序添加
                for (int i = 0; i < node.currentEntrySize; i++) {
                    Entry entry = node.currentEntry[i];
                    // 如果输入的key 小于entry里的key，则输入的key都比entry里的key小
                    // 输入key 追加在前面
                    if (key < entry.key){
                        // 先往后移动一位，空出待插入的索引位置i
                        for (int j = node.currentEntrySize - 1; j < i; j --) {
                            node.currentEntry[j + 1] = node.currentEntry[j];
                        }
                        // 插入数据
                        node.currentEntry[i] = newEntry;
                        node.currentEntrySize ++;
                        return null;
                    }
                }
                // 上面没添加则说明大于所有值
                node.currentEntry[node.currentEntrySize] = newEntry;
                node.currentEntrySize ++;

                if (node.currentEntrySize == 3){
                    // 设置临时父节点，向上抽离数据Entry
                    Node tmpNode = new Node();
                    Node leftNode = new Node();
                    Node rightNode = new Node();

                    leftNode.currentEntrySize ++;
                    rightNode.currentEntrySize ++;
                    // 现在是3阶，先写死
                    leftNode.currentEntry[0] = node.currentEntry[0];
                    rightNode.currentEntry[0] = node.currentEntry[2];

                    // 设置父节点自己点数
                    tmpNode.childrenNodeSize = 2;
                    // 把要上移的数据赋值给父结点
                    tmpNode.currentEntry[0] = node.currentEntry[order / 2];
                    tmpNode.childrenNodeSize = 2;
                    tmpNode.currentEntrySize ++;

                    tmpNode.childrens[0] = leftNode;
                    tmpNode.childrens[1] = rightNode;

                    return tmpNode;
                }
                return null;
            }else {// 添加完结点需要判断一下当前结点数据总数是否=阶数，如果等于则需要拆分
                // 设置临时父节点，向上抽离数据Entry
                Node tmpNode = new Node();
                Node leftNode = new Node();
                Node rightNode = new Node();

                leftNode.currentEntrySize ++;
                rightNode.currentEntrySize ++;
                // 现在是3阶，先写死
                leftNode.currentEntry[0] = node.currentEntry[0];
                rightNode.currentEntry[0] = node.currentEntry[2];

                // 设置父节点自己点数
                tmpNode.childrenNodeSize = 2;
                // 把要上移的数据赋值给父结点
                tmpNode.currentEntry[0] = node.currentEntry[order / 2];
                tmpNode.childrenNodeSize = 2;

                tmpNode.childrens[0] = leftNode;
                tmpNode.childrens[1] = rightNode;

                return tmpNode;
            }
        }else {
            // 非叶子结点
            int i = 0;
            for (; i < node.currentEntrySize; i ++) {
                if (key < node.currentEntry[i].key){
                    break;
                }
            }
            // 递归传递
            Node tmp = putVal(node.childrens[i], key, val, treeHeight);
            if (tmp == null){
                return null;
            }
            // 合并父节点
            node.currentEntry[node.currentEntrySize] = tmp.currentEntry[tmp.currentEntrySize - 1];
            node.currentEntrySize ++;

            // 串联子节点
            node.childrens[node.childrenNodeSize - 1] = tmp.childrens[0];
            node.childrens[node.childrenNodeSize] = tmp.childrens[1];

            node.childrenNodeSize ++;

            if (node.currentEntrySize == 3){
                Node tmpNode = new Node();
                Node leftNode = new Node();
                Node rightNode = new Node();

                leftNode.currentEntrySize ++;
                rightNode.currentEntrySize ++;
                // 现在是3阶，先写死
                leftNode.currentEntry[0] = node.currentEntry[0];
                leftNode.childrens[0] = node.childrens[0];
                leftNode.childrens[1] = node.childrens[1];

                rightNode.currentEntry[0] = node.currentEntry[2];
                rightNode.childrens[0] = node.childrens[2];
                rightNode.childrens[1] = node.childrens[3];

                // 设置父节点自己点数
                tmpNode.childrenNodeSize = 2;
                // 把要上移的数据赋值给父结点
                tmpNode.currentEntry[0] = node.currentEntry[order / 2];
                tmpNode.childrenNodeSize = 2;
                tmpNode.currentEntrySize ++;

                tmpNode.childrens[0] = leftNode;
                tmpNode.childrens[1] = rightNode;

                leftNode.childrenNodeSize = 2;
                rightNode.childrenNodeSize = 2;

                return tmpNode;
            }
            return null;
        }
    }
}
