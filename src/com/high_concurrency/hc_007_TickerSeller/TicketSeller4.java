package com.high_concurrency.hc_007_TickerSeller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 *
 * 分析下面的程序可能会产生哪些问题？
 * 重复销售？超额销售？
 *
 *
 */
public class TicketSeller4 {
    //队列，即容器
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static{
        for (int i = 0; i < 1000; i++) tickets.add("票编号：" + i);
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            new Thread(()->{

                while (true) {
                    //poll() 拿出头的数据 head 是同步的
                    //如果没拿着，返回null
                    String s = tickets.poll();
                    if(s==null) break;
                    else System.out.println("销售了--" + s);

                }

            }).start();
        }
    }
}
