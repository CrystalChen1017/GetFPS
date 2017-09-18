package com.crystal.getfps.model;

import android.util.Log;

import com.crystal.getfps.utils.FPSUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

/**
 * Parse the fps information from dumpsys SurfaceFlinger result
 */

public class SurfaceFlingerInfo {

    private long deviceRate;
    private List<FrameTime> frameTimeList;

    public SurfaceFlingerInfo(String deviceRate, ArrayList<FrameTime> list) {
        this.deviceRate = Long.parseLong(deviceRate);
        this.frameTimeList = list;
    }

    public ArrayList<FPSInfo> parseFps() {
        ArrayList<FPSInfo> key_frame_list = new ArrayList<>();
        long device_ftime = this.deviceRate / 1000000;
        double last_ftime = 0.0;  // last frame time
        int fcounts = 0;  // the number of total frames
        double total_time = 0.0;
        int dropped_counts = 0;
        double max_ftime = device_ftime; // max frame time
        int over_t_ftime_counts = 0; // the number of frames over 16.7ms
        double fps = 0.0;
        for (FrameTime per_frame_time : this.frameTimeList) {
            if (last_ftime == 0) {
                fcounts = 0;
                last_ftime = per_frame_time.t2;
                total_time = 0;
                dropped_counts = 0;
                max_ftime = device_ftime;
                over_t_ftime_counts = 0;
            } else {
                double ftime = (per_frame_time.t2 - last_ftime) / 1000000;
                if (ftime > 500) {//if time > 500ms we need to calculate fps and print it on screen
                    if (fcounts > 0) {
                        fps = round(fcounts * 1000 / total_time);
                        String time = FPSUtils.getTimeByFormat();
                        FPSInfo key_frame = new FPSInfo(time, fps, fcounts, dropped_counts, max_ftime, over_t_ftime_counts, device_ftime);
                        key_frame_list.add(key_frame);
                        Log.d("autotest_middle", "SurfaceFlingerInfo_parseFps: " + key_frame.toString());
                    }
                    // init the values again
                    fcounts = 0;
                    last_ftime = per_frame_time.t2;
                    total_time = 0;
                    dropped_counts = 0;
                    max_ftime = device_ftime;
                    over_t_ftime_counts = 0;
                } else { // if time < 500ms，keep on collecting
                    fcounts += 1;
                    if (ftime > device_ftime) {
                        total_time += ftime;
                        if (ftime > device_ftime)
                            over_t_ftime_counts += 1;
                        if (ftime >= max_ftime)
                            max_ftime = ftime;
                        if ((per_frame_time.t3 - per_frame_time.t1) / 1000000 > device_ftime) {
                            dropped_counts += 1;
                        }
                        last_ftime = per_frame_time.t2;
                    } else {
                        total_time += device_ftime;
                        last_ftime = round(last_ftime + device_ftime * 1000000);

                    }

                }
            }
        }
        if (fcounts > 0) {
            fps = round(fcounts * 1000 / total_time);
            String time = FPSUtils.getTimeByFormat();
            FPSInfo key_frame = new FPSInfo(time, fps, fcounts, dropped_counts, max_ftime, over_t_ftime_counts, device_ftime);
            key_frame_list.add(key_frame);
            Log.d("autotest_end", "SurfaceFlingerInfo_parseFps: " + key_frame.toString());
        }
        return key_frame_list;
    }
}
