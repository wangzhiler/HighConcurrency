package com.high_concurrency.hc_008_containers;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * blog.csdn.net/sunxianghuang/article/details/52221913
 *
 * Map都是key value对
 * 1）
 * concurrenthashmap 跟 hashtable都是自带了锁的，但是con的效率高一点
 * 因为hashtable锁定整个对象，con默认把容器分成16段，每次插入之锁定其中一段，并不锁定整个对象
 * 所以con效率比hashtable高，也比synchronized、collections效率高
 *
 *
 */
public class T01_ConcurrentMap {
    public static void main(String[] args) {
        //HashMap是哈希表实现的，TreeMap是树实现的
        //skipListMap跳表结构，插入时效率较高
        /**
         * 1. hashtable 最老的map实现，默认加了锁的，效率低，现在用得少
         * 2. hashmap 自己加锁 Collections.synchronizedXXX
         * 3.
         */

        //并发hashmap  结果：2439
//        Map<String, String> map = new ConcurrentHashMap<>();

        //高并发并且排序的情况下，使用跳表map
        //需要排序，所以时间长一点
        Map<String, String> map = new ConcurrentSkipListMap<>();

        //hashtable 所有方法都加了锁的，结果：1832
//        Map<String, String> map = new Hashtable<>();

//        Map<String, String> map = new HashMap<>();

        //非并发，想要排好顺序的，用TreeMap
        //sortedmap也是排好顺序的，但是插入的时候效率低，高并发情况下效率更低

        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();
        for(int i=0; i<ths.length; i++) {
            ths[i]=new Thread(()->{
                // 100个线程，每个线程往map里加100000个数据
                // 每个线程执行完，门闩减一，所有线程执行完，计算一个时间
                // 看多线程并发情况下的效率问题
                for (int j = 0; j < 10000; j++) map.put("a" + r.nextInt(100000),
                        "a" + r.nextInt(100000));
                latch.countDown();
            });
        }
        Arrays.asList(ths).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
