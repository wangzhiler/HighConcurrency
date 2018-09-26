package com.high_concurrency.hc_009_pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by thinkpad on 2018/4/7.
 * 并行计算
 *
 * 第一种线程池：固定个数的线程池
 * newFixedThreadPool
 */
public class T07_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //方法1， 一个主线程，开始时间结束时间，调用getprime
        long start = System.currentTimeMillis();
        List<Integer> results = getPrime(1, 200000);
        //1~200000之间的质数 数学计算，可能耗时很长，用线程池，分到不同线程执行，最后返回过来
        long end = System.currentTimeMillis();
        System.out.println(end - start); //21876

        final int cpuCoreNum=4;

        //方法2： 线程池
        //一般cpu有几个核就起几个线程，不少于cpu的核数
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

        MyTask t1 = new MyTask(1, 80000);
        MyTask t2 = new MyTask(80001, 130000);
        MyTask t3 = new MyTask(130001, 170000);
        MyTask t4 = new MyTask(170001, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end = System.currentTimeMillis();
        System.out.println(end - start);  //7328
    }

    static class MyTask implements Callable<List<Integer>> {

        int startPOS, endPos;

        MyTask(int s, int endPos) {
            this.startPOS = s;
            this.endPos = endPos;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPOS, endPos);
            return r;
        }
    }

    //辅助方法1 isPrime 传入一数判断是不是质数，返回true是质数
    static boolean isPrime(int num) {
        for(int i=2; i<=num/2; i++) {
            if(num%i==0) return false;
        }
        return true;
    }

    //辅助方法2 从start到end，当中有那几个数，放到List里
    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for(int i=start; i<=end; i++) {
            if(isPrime(i)) results.add(i);
        }

        return results;
    }

}
