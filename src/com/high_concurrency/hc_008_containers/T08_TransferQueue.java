package com.high_concurrency.hc_008_containers;

import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * eg. 一个队列，生产者生产出一个东西后，本来直接进队列。
 * 现在 先去找有没有消费者需要这个东西，有需要则直接给消费者，否则用transfer会进行阻塞
 *
 * 用于更高的并发的线程
 */
public class T08_TransferQueue {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa");

        //如果先执行transfer 后take 会阻塞，take这块执行不了了
        //put什么的不会阻塞
        /*
        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        */
    }
}
