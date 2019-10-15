package com.learnnote.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyh
 * @Date 2019/8/11 14:50
 * @Description
 * @Note  给定一个字符串,找出不含有重复字符的最长子串长度
 */
public class Example8 {

    public static void main(String[] args) {
        //String demoString = "abcde";
        // bc  截取索引1和2的字符
        //System.out.println(demoString.substring(1,3));
        // 5
        //System.out.println(demoString.length());

        String[] strs = new String[]{"abcaabcbb", "bbbbbb", "pwwkew", "adbsdsnnmdxs"};

        for (String str : strs) {
            /** exp: abc,b,wke*/
            System.out.println(lengthOfLongestSubstring(str));
        }
    }

    public static String findMaxLengthSubString(String str) {
        Map<Character, Integer> map = new HashMap<>(16);
        int maxLen = 0;
        char currentChar;
        String result = null;
        for (int i = 0; i < str.length(); i++) {
            currentChar = str.charAt(i);
            if (map.containsKey(currentChar)) {
                // map.get(str.charAt(i))获取到的是原来存在的值的索引,当前的值还没有存放到map中去
                int currentLen = i - map.get(currentChar);
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    // substring截取字符串不包含末尾index的char,所以要 +1
                    result = str.substring(map.get(currentChar), i);
                }
            }
            map.put(currentChar, i);
        }

        return result;
    }

    public static int lengthOfLongestSubstring(String str) {
        int len = str.length(), result = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int j = 0, i = 0; j < len; j++) {
            if (map.containsKey(str.charAt(j))) {
                i = Math.max(map.get(str.charAt(j)), i);
            }

            result = Math.max(result, j - i + 1);
            map.put(str.charAt(j), j + 1);
        }

        return result;
    }
}
