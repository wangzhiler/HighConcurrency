package high_concurrency.hc_001;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另外一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外的对象
 *
 */
public class T016 {

    //所以锁在堆内存里 new object的对象上
    //不是锁在栈内存里o的引用

    //Object o是存在一块小内存，引用的内存里
    //new Object()在堆内存里，然后锁是所在堆内存里的
    Object o = new Object();
    void m() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T016 t = new T016();
        //启动第一个线程
        new Thread(t::m,"t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());

        //创建第二个线程
        Thread t2 = new Thread(t::m, "t2");

        t.o=new Object();  //锁对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程2将永远得不到执行击毁
        t2.start();
    }
}