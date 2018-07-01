package high_concurrency.hc_007_TickerSeller;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

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
public class TicketSeller3 {
    static Vector<String> tickets = new Vector<>();

    static{
        for (int i = 0; i < 10000; i++) tickets.add("票编号：" + i);
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            new Thread(()->{
                //都放锁里
                //可行，效率不高
                while (true) {
                    synchronized (tickets) {
                        if(tickets.size()<=0) break;
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("销售了--" + tickets.remove(0));
                    }
                }

            }).start();
        }
    }
}
