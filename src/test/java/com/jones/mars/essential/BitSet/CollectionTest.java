package com.jones.mars.essential.BitSet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Created by jones on 19-4-26.
 */
public class CollectionTest {
    AtomicInteger count = new AtomicInteger(0);
    List<String> strList = createList();
    Spliterator spliterator = strList.spliterator();
    CountDownLatch countDownLatch = new CountDownLatch(4);
    CyclicBarrier barrier = new CyclicBarrier(4);

    /**
     * 多线程计算list中数值的和
     * 测试spliterator遍历
     */
    @Test
    public void mytest(){
        long start = System.currentTimeMillis();
        MyThread thread = new MyThread(countDownLatch, barrier);
        List<Runnable> taskList = new ArrayList<>();
        taskList.add(thread);
        taskList.add(thread);
        taskList.add(thread);
        taskList.add(thread);
        Executor executor = Executors.newFixedThreadPool(taskList.size());
        for(Runnable task: taskList) {
            executor.execute(task);
        }
        try {
//            barrier.await();
            countDownLatch.await();
//            Thread.sleep(15000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("结果为：" + count + ", 用时：" + (System.currentTimeMillis() - start));
    }

    class MyThread implements Runnable{
        CountDownLatch countdown;
        CyclicBarrier cyclicBarrier;
        MyThread(CountDownLatch countdown,CyclicBarrier cyclicBarrier){
            this.countdown = countdown;
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程"+threadName+"开始运行-----");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            spliterator.trySplit().forEachRemaining(new Consumer() {
                @Override
                public void accept(Object o) {
                    if(isInteger((String)o)){
                        int num = Integer.parseInt(o +"");
                        count.addAndGet(num);

                        System.out.println("数值："+num+"------"+threadName);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            countdown.countDown();

            System.out.println("线程"+threadName+"运行结束-----");
        }
    }

    private List<String> createList(){
        List<String> result = new ArrayList<>();
        for(int i=0; i<100; i++){
            if(i % 10 == 0){
                result.add(i+"");
            }else{
                result.add("aaa");
            }
        }
        System.out.println(result.size());
        return result;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
