package com.high_concurrency.hc_001;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * 对业务写方法加锁，对业务读方法不加锁
 * 容易产生脏读问题 dirtyRead
 *
 * 解决脏读问题经常使用方式是CopyOnWrite  解释赋值
 */
public class T007_Account {

    String name;
    double balance;

    public synchronized void set(String name, double balance) {
        this.name=name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.balance=balance;
    }

    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }
    //锁定的过程中，仍可能被非锁定的代码访问


    public static void main(String[] args) {
        T007_Account a = new T007_Account();
        new Thread(()->a.set("zhangsan",100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));
    }
}
