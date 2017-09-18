package com.crystal.getfps.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import com.crystal.getfps.model.FPSInfo;
import com.crystal.getfps.model.SurfaceFlingerInfo;
import com.crystal.getfps.utils.FPSUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Float window
 */
public class FloatInfoView extends Button {
    static final int WARN_FPS = 50;

    static final int WARN_MSG = 2;
    static final int WARN_COLOR = 0x77ff3333;

    static final int NORMAL_MSG = 3;
    static final int NORMAL_COLOR = 0x7700a600;

    Timer timer = new Timer();
    InfoTimerTask infoTask = new InfoTimerTask();
    static final long reflesh_period = 1000;
    static String info = "wait...";
    ViewHandler mHandler;

    static class ViewHandler extends Handler {
        WeakReference<FloatInfoView> weakView;

        ViewHandler(FloatInfoView view) {
            weakView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            FloatInfoView view = weakView.get();
            if (view == null)
                return;
            view.setText(info);
            switch (msg.what) {
                case WARN_MSG:
                    view.setBackgroundColor(WARN_COLOR);
                    break;
                case NORMAL_MSG:
                    view.setBackgroundColor(NORMAL_COLOR);
                    break;
            }
        }
    }

    public FloatInfoView(Context context) {
        this(context, null);
        mHandler = new ViewHandler(this);
    }


    public FloatInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mHandler = new ViewHandler(this);
    }


    public FloatInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new ViewHandler(this);
        setBackgroundColor(NORMAL_COLOR);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timer.schedule(infoTask, 1000, reflesh_period);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timer != null) {
            timer.cancel();
        }
    }


    /**
     * 定期刷新UI任务
     */

    class InfoTimerTask extends TimerTask {
        @Override
        public void run() {
            info = "";
            SurfaceFlingerInfo sf = FPSUtils.dumpSurfaceFlinger();
            ArrayList<FPSInfo> list = sf.parseFps();
            if (list == null || list.size() < 0) {
                info = "wait...";
                mHandler.sendEmptyMessage(NORMAL_MSG);
                return;
            }

            for (FPSInfo frame : list) {
                info = "";
                info += "fps:" + frame.getFps() + "\n";
                info += "jank:" + frame.getJank() + "\n";
                info += "vsync:" + frame.getMaxVsyncCount();
                if (frame.getFps() < WARN_FPS) {
                    mHandler.sendEmptyMessage(WARN_MSG);
                } else {
                    mHandler.sendEmptyMessage(NORMAL_MSG);
                }
            }
        }
    }


}
