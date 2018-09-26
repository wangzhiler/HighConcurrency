package com.high_concurrency.hc_009_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 * <p>
 * 第二种线程池 弹性的
 * newCachedThreadPool
 * 开始的时候什么线程都没有，来一个任务起一个线程
 * 来任务1起线程1 来任务2起线程2 来任务3时如果任务1/2空闲了，就直接用1/2 没空闲则起线程3
 * 起到系统能支撑的最大限度为止 也不能超过int类型的最大数
 * 每个线程默认，空闲时间超过60s，会自动销毁 AliveTime=60s 可以自己制定
 */
public class T08_CachedPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        //[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]

        for(int i=0; i<2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);
        //[Running, pool size = 2, active threads = 2, queued tasks = 0, completed tasks = 0]
        TimeUnit.SECONDS.sleep(80);
        System.out.println(service);
        //[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 2]

    }
}
