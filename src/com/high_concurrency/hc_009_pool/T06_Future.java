package com.high_concurrency.hc_009_pool;

import java.util.concurrent.*;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * Future
 * 未来的某个时间点上，任务执行完了，会返回一个结果
 *
 * FutureTask 会有一个返回值
 */
public class T06_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //FutureTask 包装了一个callable类型，有返回值 是一个泛型
        //所以要把callable包装成任务的话，必须得指定泛型是什么类型
        //通过FutureTask指定它是一个Integer
        FutureTask<Integer> task=new FutureTask<Integer>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });
        //new Callable(){Integer call();}

        new Thread(task).start();

        System.out.println(task.get());
        //task执行完后，返回的结果，通过阻塞方法拿到
        //阻塞方法，一直等着任务执行完成，什么时候执行完，什么时候get到

        //***************
        ExecutorService service = Executors.newFixedThreadPool(5);
        //FutureTask包装了一个任务
        //submit可以直接往里面扔一个callable，之后返回了一个结果，放在Future里面，不需要FutureTask
        //因为在它内部，submit已经给你new了一个futureTask,所以结果放future就好
        Future<Integer> f=service.submit(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });

        System.out.println(f.isDone());  //false
        System.out.println(f.get());  //1
        System.out.println(f.isDone());  //true 阻塞完再得到的结果一定是true

    }
}
