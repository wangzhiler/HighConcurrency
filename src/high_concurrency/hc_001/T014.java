package high_concurrency.hc_001;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thinkpad on 2018/4/6.
 * <p>
 * 解决同样的问题更高效的方法，使用AtomXXX类
 * AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用时原子性的
 */
public class T014 {

    AtomicInteger count = new AtomicInteger(0);

    //不需要用synchronized incrementAndGet原子操作，可以理解成synchronzied
    //但是是从系统底层实现的，实现方式不一样，所以效率比synchronzied高很多
    /*synchronized*/ void m() {
        for(int i=0; i<10000; i++)

            //if（count.get()<10000)  如果多了这句话
            // 虽然get 根incrementAndGet各自都具备原子性
            // 但是在两句话的中间仍可能有东西插入，所以整体就不具备原子性了

            count.incrementAndGet();   //用于替代count++
        //incrementAndGet返回+1后的新值
        //getAndIncrement返回旧值，但数据+1
    }

    public static void main(String[] args) {
        T014 t = new T014();

        List<Thread> threads = new ArrayList<Thread>();

        for(int i=0; i<10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);

    }
}
