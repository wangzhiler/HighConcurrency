package com.high_concurrency.hc_007_TickerSeller;

import java.util.ArrayList;
import java.util.List;

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
 * ArrayList的方法都不是同步的
 *
 */
public class TicketSeller1 {
    static List<String> tickets = new ArrayList<>();

    static{
        for (int i = 0; i < 10000; i++) tickets.add("票编号：" + i);
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            new Thread(()->{
                while (tickets.size() > 0) {
                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
