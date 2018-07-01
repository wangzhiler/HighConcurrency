package high_concurrency.hc_008_containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 写时复制，写的时候效率非常低，读的时候效率非常高
 * 适合少写多读
 *
 * 当添加元素时，把容器复制一份，在后面加上新加入的元素
 * 然后把对原来容器的引用直到新的上面
 *
 * 好处：对于读的线程不用加锁
 * 常用于：事件监听器的队列，每次读都得判断整个队列是否有监听器被触发，而加入比较小
 *
 */
public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> lists =
                new ArrayList<>(); //这个会出现并发问题 327 98395
//                new Vector<>();  //431 100000 结果正确
//                new CopyOnWriteArrayList<>(); //23255 100000

        Random r = new Random();
        Thread[] ths = new Thread[100];
        for(int i=0; i<ths.length; i++) {
            Runnable task=new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<1000; i++) {
                        lists.add("a"+r.nextInt(10000));
                    }
                }
            };
            ths[i] = new Thread(task);
        }

        runAndComputeTime(ths);
        System.out.println(lists.size());

    }

    static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t -> t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }
}













