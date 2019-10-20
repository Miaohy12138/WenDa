/**
 * Author : MIAOHY
 * Time :2019/9/20 8:50
 * Beauty is better than ugly!
 */
package com.miaohy.concurrentDemo;

public class ProducerAndConsumer {
    private static Integer count = 0;
    private final Integer FULL = 10;
    private static String LOCK = "LOCK";

    class Producer implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while(count .equals(FULL)){
                        try{
                            LOCK.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    count ++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }
    class Consumer implements  Runnable{


        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while(count==0){
                        try{
                            LOCK.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer r = new ProducerAndConsumer();
        new Thread(r.new Producer()).start();
        new Thread(r.new Consumer()).start();
        new Thread(r.new Producer()).start();
        new Thread(r.new Consumer()).start();
        new Thread(r.new Producer()).start();
        new Thread(r.new Consumer()).start();
        new Thread(r.new Producer()).start();
        new Thread(r.new Consumer()).start();
    }
}
