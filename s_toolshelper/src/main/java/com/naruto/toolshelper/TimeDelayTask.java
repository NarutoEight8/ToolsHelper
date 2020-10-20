package com.naruto.toolshelper;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/16.
 */

public class TimeDelayTask {
    //创建基本线程池
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,6,3000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(60));
    private OnTimeEndListener onTimeEndListener;
    private long timeDelay;

    public interface OnTimeEndListener {
        void onTimeEnd();
    }
    public void runTask(long timeMs, OnTimeEndListener listener) {
        onTimeEndListener = listener;
        timeDelay = timeMs;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeDelay);
                    if (onTimeEndListener != null) onTimeEndListener.onTimeEnd();
                }catch (Exception e){
                    Log.e("ExceptionTimeTask",e.toString());
                    return;
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

    //time计时器老是报错
//    TimerTask task = new TimerTask() {
//        public void run() {
//            if (onTimeEndListener != null) onTimeEndListener.onTimeEnd();
//        }
//    };
//    timer = new Timer();
//                    timer.schedule(task, timeDelay);
}
