package com.high_concurrency.hc_008_containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/7.
 */
public class T03_SynchronizedList {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        //返回的是加了锁的list
        List<String> strsSync = Collections.synchronizedList(strs);
    }
}
