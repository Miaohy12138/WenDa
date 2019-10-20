/**
 * Author : MIAOHY
 * Time :2019/9/20 8:35
 * Beauty is better than ugly!
 */
package com.miaohy.concurrentDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/*
* 1、默认都是非公平锁
* 2、可限时
* 3、可重入
* 4、condition  await signal*/
public class ReentrantLockDemo implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition=  lock.newCondition();
    public static int i = 0;
    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo t  = new ReentrantLockDemo();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
