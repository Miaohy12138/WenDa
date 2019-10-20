/**
 * Author : MIAOHY
 * Time :2019/9/20 8:26
 * Beauty is better than ugly!
 */
package com.miaohy.concurrentDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable{
    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try{
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() +"done");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemaphoreDemo  s = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            executorService.submit(s);
        }
    }
}
