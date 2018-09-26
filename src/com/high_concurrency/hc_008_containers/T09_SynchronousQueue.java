package com.high_concurrency.hc_008_containers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by thinkpad on 2018/4/7.
 * 特殊的transferQueue
 * synchronousQueue 容量为0 所有进入的东西必须马上消费掉
 */
public class T09_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(() ->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //阻塞等待消费者消费
        //特殊的transfer 里面所有东西必须直接交给消费者消费
        strs.put("aaa");
//        strs.add("aaa");  //queue full
        System.out.println(strs.size());
    }
}
