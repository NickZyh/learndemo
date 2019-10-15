package com.learnnote.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author Zyh
 * @Date 2019/8/23 13:34
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class Example11 {

    public List<List<Integer>> combine(int n, int k) {
        if (n <= 0 || k <= 0 || k > n) {
            throw new RuntimeException();
        }

        List<List<Integer>> allList = new ArrayList<>();
        foreach(allList, n, k,1, new ArrayList<>());
        return allList;
    }

    public void foreach(List<List<Integer>> res, int n, int k, int start, List<Integer> list) {
        if (list.size() == k) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i <= n; i++) {
            list.add(i);
            foreach(res, n, k,i + 1, list);
            list.remove(list.size() - 1);
        }
    }

    public List<List<Integer>> combine1(int n, int k) {
        if (n <= 0 || k <= 0 || k > n) {
            throw new RuntimeException();
        }

        List<List<Integer>> allList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int count = 1;
            List<Integer> element = new ArrayList<>();
            for (int j = i; count <= k && j <= n; j++, count++) {
                element.add(j);
                allList.add(element);
            }
        }
        return allList;
    }

    // return:从根到叶子的全路径value
    public List<List<Integer>> pathSum(TreeNode root, int sum){
        if(root == null){
            return null;
        }

        List<List<Integer>> pathList = new ArrayList<>();
        // 路径存储
        List<Integer> path = new ArrayList<>();
        findPath(root, sum, path, pathList);
        return pathList;
    }

    private void findPath(TreeNode node, int remainSum,
                          List<Integer> path, List<List<Integer>> pathList) {
        if(node == null) {
            return;
        }

        if(node.left == null && node.right == null) {
            if(node.val == remainSum) {
                List<Integer> pathResult = new ArrayList<>();
                for (int value : path) {
                    pathResult.add(value);
                }
                pathResult.add(node.val);
                pathList.add(pathResult);
            }
            return;
        }

        path.add(node.val);
        findPath(node.left, remainSum - node.val, path, pathList);
        findPath(node.right, remainSum - node.val, path, pathList);
        // 容器复用
        path.remove(path.size() - 1);
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }
}
