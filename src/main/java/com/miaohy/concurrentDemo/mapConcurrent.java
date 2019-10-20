/**
 * Author : MIAOHY
 * Time :2019/9/20 10:23
 * Beauty is better than ugly!
 */
package com.miaohy.concurrentDemo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class mapConcurrent {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>();
        Map concurrentMap = Collections.synchronizedMap(map);
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    }
}
