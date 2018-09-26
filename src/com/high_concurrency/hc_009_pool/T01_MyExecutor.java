package com.high_concurrency.hc_009_pool;

import java.util.concurrent.Executor;

/**
 * Created by thinkpad on 2018/4/7.
 * <p>
 * 认识接口Executor
 *
 * java框架线程池的最顶层接口
 *
 * 可以直接调用run
 * 也可以new一个Thread来调用run
 */
public class T01_MyExecutor implements Executor {

    public static void main(String[] args) {
        new T01_MyExecutor().execute(() -> System.out.println("hello executor"));
    }

    @Override
    public void execute(Runnable command) {
        //new Thread(command).run();
        command.run();
    }
}
