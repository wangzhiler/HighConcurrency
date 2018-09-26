package com.high_concurrency.hc_009_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 线程池，就是一堆线程，装在某个容器里，然后运行
 *
 * 具体一点讲，
 * 一个线程池是一个池子里维护了很多线程，等待任务被扔进来
 * 同时它维护着一个任务列表，还没被执行的任务列表
 * 同时还维护了一个结束了的队列
 *
 * 一个线程池维护两个队列
 */
public class T05_ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        //固定的 线程池，内有5个线程，还没启动
        //Executors一个工具类，包括了一些工厂方法，会产生一些新的产品，主要就是ThreadPool线程池
        //Java所有线程池都实现了ExecutorService接口，所以可以用这接口指向后面返回的对象
        //且还可以往里面扔任务，submit、execute都可
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i=0; i<6; i++) {
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
// java.util.concurrent.ThreadPoolExecutor@1a4f24f  cachecode的一些信息
// [Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
// 状态正在运行，线程池大小为5，启动的任务5个，  第六个任务排队
// 排队排在线程池维护的任务队列里，大部分用的都是BlockingQueue
// 好处：任务执行完后，线程池里的线程不会消失，线程空闲，新的任务进来，不用启动新线程
// 效率高，并发性好。 启动关闭线程都需要消耗一定资源，所以线程启动之后能重用就重用


        //关闭线程池
        //shutdown()是正常关闭，会等所有线程执行完再关闭线程池
        //shutdownNow()二话不说直接关闭
        service.shutdown();
        System.out.println(service.isTerminated()); //false 因为还没执行完
        System.out.println(service.isShutdown()); //true 表示正在关闭的执行过程中
        System.out.println(service);
// java.util.concurrent.ThreadPoolExecutor@1a4f24f
// [Shutting down, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
// 状态变成shutting down


        TimeUnit.SECONDS.sleep(5);
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}








//
// java.util.concurrent.ThreadPoolExecutor@1a4f24f[Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
// false
// true
// java.util.concurrent.ThreadPoolExecutor@1a4f24f[Shutting down, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
// pool-1-thread-2
// pool-1-thread-1
// pool-1-thread-3
// pool-1-thread-5
// pool-1-thread-4
// pool-1-thread-2
// true
// true
// java.util.concurrent.ThreadPoolExecutor@1a4f24f[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 6]











