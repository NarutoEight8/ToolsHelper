package com.naruto.toolshelper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * new OSingleThreadPool().putRunnable();
 */

public class OSingleThreadPool {
    //创建基本线程池
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(60));
    public OSingleThreadPool(){
    }
    public void putRunnable(Runnable runnable) {
        if(threadPool.getQueue().size()>30)return;
        threadPool.execute(runnable);
    }
    public int getPoolSize(){
        return threadPool.getQueue().size();
    }

}
