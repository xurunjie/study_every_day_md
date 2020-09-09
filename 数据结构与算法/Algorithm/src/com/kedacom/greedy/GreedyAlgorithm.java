package com.kedacom.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author python
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 创建广播电台
        HashMap<String, HashSet<String>> broadCasts = new HashMap<>();
        // 将各个电台放入 broadCasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        // 加入到 map
        broadCasts.put("k1", hashSet1);
        broadCasts.put("k2", hashSet2);
        broadCasts.put("k3", hashSet3);
        broadCasts.put("k4", hashSet4);
        broadCasts.put("k5", hashSet5);

        // allAreas 存放所有地区
        HashSet<String> allAreas = new HashSet<>();

        for (Map.Entry<String, HashSet<String>> stringHashSetEntry : broadCasts.entrySet()) {
            allAreas.addAll(stringHashSetEntry.getValue());
        }

        // 创建 arrayList 存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();

        // 定义一个临时的集合,再遍历的过程中,存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();


        String maxKey;
        // 定义给 maxKey, 保存在依次遍历中,能够覆盖最大地区的电台
        // 如果 maxKey 不为 null,则会加入到 selects
        while (allAreas.size() != 0) {
            // 没进行依次 while 循环
            maxKey = null;
            // 如果 allAreas 不为 0,则表示还没有覆盖掉所有的地区
            for (String key : broadCasts.keySet()) {
                // 每进行依次 for
                tempSet.clear();
                // 当前这个 key 能够覆盖的地区
                HashSet<String> areas = broadCasts.get(key);
                tempSet.addAll(areas);
                // 求出 tempSet 和 allAreas 集合的交集
                tempSet.retainAll(allAreas);
                // 如果当前这个集合包含的未付盖地区的数量,比 maxKey 指向的集合地区还多
                // 就需要充值 maxKey
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > broadCasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }
            if (maxKey != null) {
                selects.add(maxKey);
                allAreas.removeAll(broadCasts.get(maxKey));
            }
        }

        System.out.println("selects = " + selects);
    }
}
