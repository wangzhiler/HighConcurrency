package com.high_concurrency.hc_008_containers;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by thinkpad on 2018/4/7.
 * <p>
 * queue在并发容器中是最重要的运用最多的容器
 */
public class T04_ConcurrentQueue {

    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedDeque<>();

        for(int i=0; i<10; i++) {
            //offer类似于add方法
            //add方法有容量限制会抛异常
            //offer由返回值来判断加没加成功
            strs.offer("a" + i);
        }

        System.out.println(strs);
        System.out.println(strs.size());

        //poll 从脑袋上拿走一个
        //拿出来，原队列里的删掉
        System.out.println(strs.poll());
        System.out.println(strs.size());

        //peek 拿出来用一下 不删
        System.out.println(strs.peek());
        System.out.println(strs.size());

        //双端队列Dueue
        //add(e)--addLast(e)
        //offer(e)--offerlast(e)
        //remove()--removeFirst()
        //poll()--pollFirst()
        //element()--getFirst()
        //peek()--peekFirst()
    }
}
