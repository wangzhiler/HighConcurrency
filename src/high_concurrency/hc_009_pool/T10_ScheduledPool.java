package high_concurrency.hc_009_pool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 第四种线程池
 * ScheduledPool 任务排好表格的定时器线程池
 * 一般可以用来替代Timer
 * ScheduledPool内部线程可以复用，而Timer每次要new一个新线程
 */
public class T10_ScheduledPool {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        //scheduleAtFixedRate 以固定的频率执行某个任务
        // 4个参数 runnable initialDelay起始时的延迟时间 period每隔多少时间执行一个任务 时间单位
        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);

    }

}
