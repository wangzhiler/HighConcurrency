package high_concurrency.hc_009_pool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 第六种线程池 ForkJoinPool
 *
 * 先分叉再合并  类似map reduce
 *
 */
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static{
        for(int i=0; i<nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum()); //stream api
        //stream不使用多线程 parallels 使用多线程
    }

    /*
    //RecursiveAction没有返回值 相当于Runnable类型
    static class AddTask extends RecursiveAction {
        int start, end; //下标起始位置，结束位置

        AddTask(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                //如果end-start小于50000，就不分了，打印
                for (int i = start; i < end; i++) sum += nums[i];
                System.out.println("from:" + start + " to:" + end + "=" + sum);
                //只能打印，因为RecursibeAction没有返回值
            } else {
                int middle = start + (end - start) / 2;
                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }
    */

    static class AddTask extends RecursiveTask<Long> {
        int start, end; //下标起始位置，结束位置

        AddTask(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                //如果end-start小于50000，就不分了
                for (int i = start; i < end; i++) sum += nums[i];
                return sum;
            } else {
                int middle = start + (end - start) / 2;
                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);

        long result=task.join();
        System.out.println(result);

//        System.in.read();
    }
}
