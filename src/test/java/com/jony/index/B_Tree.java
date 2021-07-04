package com.jony.index;

import java.util.ArrayList;
import java.util.List;

public class B_Tree {

    // 阶数
    private int order = 3;

    // 根节点
    private BTreeNode root;
    // 高度
    private int height = 0;

    public B_Tree() {

    }
    public B_Tree(int order) {
        this.order = order;
    }

    public void put(String key, String value){

        BTreeNode node = insert(root, key, value, height);


        boolean empty = isEmpty(this.root);

        Entry<String, String> entry1 = new Entry<>(key, value, null);
        // 根节点为空，创建根节点
        if (empty){
            root = new BTreeNode();
            // 节点计数增加
            root.setCount(1);
            // 添加数据
            root.getEntries().add(entry1);
        }else {
            // 存在根节点
            // ①一个结点上至多存在order-1个数据  order 为阶数
            // 根节点 无子节点
            if (root.getChild().size() == 0 && 0 <= root.getCount() && root.getCount() + 1 < this.order){
                // 节点计数增加
                root.setCount(root.getCount() + 1);
                // 添加数据，左到右升序
                List<Entry<String, String>> entries = root.getEntries();
                int size = entries.size();
                // 倒序遍历 ,最大的在右侧
                for (int i = size; i > 0; i --) {
                    Entry<String, String> entry = entries.get(i - 1);
                    // 判断大小
                    if (entry.getKey().compareTo(key) < 0){
                        entries.add(i, entry1);
                        // 右边是较大数，左边会更小，所以插入一次即结束循环，否则会一直插入
                        break;
                    }
                }
            }else {
                // 右子结点有内容，则判断右子节点
                if(root.getEntries().equals("")){
                    //结点数据数量 等于 阶数 需要拆分
                    // 临时根节点
                    BTreeNode tmpNode = new BTreeNode();
                    tmpNode.setCount(1);
                    // m/2 向上取整，取出该数据提升为父节点
                    int middle = (int) Math.ceil(this.order / 2);
                    Entry<String, String> tmpEntry = root.getEntries().get(middle);
                    tmpNode.getEntries().add(tmpEntry);

                    // 先把数据追加在一起，然后进行拆分
                    List<Entry<String, String>> entries = root.getEntries();
                    int size = entries.size();
                    // 倒序遍历 ,最大的在右侧
                    for (int i = size; i > 0; i --) {
                        Entry<String, String> entry = entries.get(i - 1);
                        // 判断大小
                        if (entry.getKey().compareTo(key) < 0){
                            entries.add(i, entry1);
                            // 右边是较大数，左边会更小，所以插入一次即结束循环，否则会一直插入
                            break;
                        }
                    }

                    BTreeNode leftNode = new BTreeNode();
                    BTreeNode rightNode = new BTreeNode();

                    // 拆开之后 分别拿出左边的节点和右边的节点
                    List<Entry<String, String>> leftList = new ArrayList<>();
                    List<Entry<String, String>> rightList = new ArrayList<>();
                    for (int i = 0; i < middle; i++) {
                        Entry<String, String> leftEntry = entries.get(i);
                        leftList.add(leftEntry);
                    }
                    // 跳过 middle 的数据
                    for (int i = middle + 1; i < this.order; i++) {
                        Entry<String, String> rightEntry = entries.get(i);
                        rightList.add(rightEntry);
                    }
                    leftNode.setEntries(leftList);
                    rightNode.setEntries(rightList);

                    // 左右节点加入到父节点
                    tmpNode.getChild().add(leftNode);
                    tmpNode.getChild().add(rightNode);

                    tmpEntry.setNext(rightNode);

                    root = tmpNode;
                    // 设置右结点

                }else {
                    List<Entry<String, String>> rootEntries = root.getEntries();
                    for (int i = 0; i < rootEntries.size(); i++) {
                        Entry<String, String> rootEntry = rootEntries.get(i);
//                        if (rootEntry.getKey().compareTo(key))
                    }
                }
            }
        }
    }

    private BTreeNode insert(BTreeNode root, String key, String value, int height) {

        return null;
    }

    public void delete(){

    }
    public void update(){

    }
    public void find(){

    }

    /**
     * 节点是否为空
     * @param node
     * @return
     */
    private boolean isEmpty(BTreeNode node){
        if (null == node){
            return true;
        }
        if (node.getCount() == 0){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        B_Tree b_tree = new B_Tree();
        b_tree.put("1", "张三法外狂徒");
        b_tree.put("2", "张三法外狂徒1");
        b_tree.put("3", "张三法外狂徒2");
        b_tree.put("4", "张三法外狂徒3");
        b_tree.put("5", "张三法外狂徒4");
        b_tree.put("6", "张三法外狂徒5");
        b_tree.put("7", "张三法外狂徒6");
        b_tree.put("8", "张三法外狂徒7");
        System.out.println(b_tree);
    }
}
