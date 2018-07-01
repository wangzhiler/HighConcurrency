package high_concurrency.hc_009_pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 第五种线程池
 * WorkStealingPool 工作窃取线程池
 *
 * 每个线程都维护自己的一个队列
 * 如果线程A把自己队列里的任务执行完了，会去没执行完的B队列里拿任务执行
 *
 * 主动找活干 ForkJoinPool
 *
 * 适用于任务分配不均匀的场景
 */
public class T11_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));

        //daemon 主函数可能结束了，但它后台还在运行，但看不到任何输出
        //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        System.in.read(); //用于阻塞，看输出
    }

    static class R implements Runnable {
        int time;

        R(int t) {
            this.time=t;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time+" "+Thread.currentThread().getName());
        }
    }

}
