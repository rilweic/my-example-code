package com.git.chapter13;/**
 * Created by lichao on 16-5-27.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * MyMap
 *
 * @author Li Chao
 * @date 16-5-27
 * @time 下午1:32
 */
public class MyMap {
    public static void main(String[] args) {
        System.out.println("Hello");
    }

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("001", "zhangsan");
        map.put("002", "lisi");
        map.put("003", "wangwu");

        return map;
    }
}
