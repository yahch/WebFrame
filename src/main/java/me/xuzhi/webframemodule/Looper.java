package me.xuzhi.webframemodule;

import android.os.Handler;

/**
 * Created by xuzhi on 2017/9/4.
 */

public class Looper {

    private Handler handler = null;

    private Runnable runnable = null;

    private int seconds = 0;

    public interface LooperCallback {
        void onTicked(int seconds);
    }

    public Looper(final LooperCallback looperCallback) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                looperCallback.onTicked(seconds);
                handler.postDelayed(this, 1000);
            }
        };
    }

    public void start() {
        handler.postDelayed(runnable, 1000);
    }

    public void reset() {
        seconds = 0;
    }
}
