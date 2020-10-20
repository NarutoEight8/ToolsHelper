/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naruto.toolshelper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public abstract class CountDownTimerMy {
    private final long mMillisInFuture;
    private final long mCountdownInterval;
    private long mStopTimeInFuture;
    private int timeCount=0;
    private long mMillisInStart;
    private boolean mCancelled = false;
    public CountDownTimerMy(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }
    public synchronized final void cancel() {
        mCancelled = true;
        timeCount = 0;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountDownTimerMy start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        timeCount = 0;
        mMillisInStart = SystemClock.elapsedRealtime();
//        Log.e("Tick","elapsedRealtime START:"+mMillisInStart);
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }
    public abstract void onTick(long millisUntilFinished);
    public abstract void onFinish();


    private static final int MSG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountDownTimerMy.this) {
                if (mCancelled) {
                    return;
                }
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();//还有多久结束
                if (millisLeft <= 0) {//执行完成
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {//不够1秒,最后一次
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    onTick(millisLeft);//执行事件派发
                    timeCount ++;
                    long currentTime = mMillisInStart+ timeCount*mCountdownInterval;
                    long delay = currentTime-SystemClock.elapsedRealtime();
//                    Log.e("Tick","TIME:"+currentTime+" DELAY"+delay);
                    if(delay<=0)delay=20;
                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
