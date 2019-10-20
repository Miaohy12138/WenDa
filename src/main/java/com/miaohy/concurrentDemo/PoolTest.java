/**
 * Author : MIAOHY
 * Time :2019/9/21 15:26
 * Beauty is better than ugly!
 */
package com.miaohy.concurrentDemo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class PoolTest {
    static volatile int ticket = 100;
    public static void main(String[] args) {
        /*
        * public ThreadPoolExecutor(int corePoolSize //核心线程数量
        *
        *
                              int maximumPoolSize  //最大线程数量


                              long keepAliveTime //回收空闲线程时间


                              TimeUnit unit //对应上一个时间的单位


                              BlockingQueue<Runnable> workQueue//队列



                              ThreadFactory threadFactory  //自定义线程 threadFactory类型



                              RejectedExecutionHandler handler)  //拒绝策略
                                                                 //任务拒绝策略 ，当任务队列满后，所执行的策略
                                                                 //ThreadPoolExecutor.AbortPolicy()：被拒绝后抛出RejectedExecutionException异常
                                                                 //ThreadPoolExecutor.CallerRunsPolicy()：被拒绝后给调用线程池的线程处理
                                                                 //ThreadPoolExecutor.DiscardOldestPolicy1()：被拒绝后放弃队列中最旧的未处理的任务
                                                                 //ThreadPoolExecutor.DiscardPolicy()：被拒绝后放弃被拒绝的任务(当前新添加的任务)






                                                                 */

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(5, 10,
                2000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 50; i++) {
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "  " +ticket);
                    ticket--;
                }
            });
        }
        singleThreadPool.shutdown();




        ExecutorService executorService1  = Executors.newFixedThreadPool(10);//阻塞队列大小为整数最大值 请求过多会导致oom
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        ExecutorService executorService3 = Executors.newCachedThreadPool();//线程池默认大小为最大值，会不断创建导致oom
        ExecutorService executorService4  = Executors.newScheduledThreadPool(10);
    }
}
