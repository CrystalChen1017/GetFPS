package com.crystal.getfps.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.crystal.getfps.model.FrameTime;
import com.crystal.getfps.model.SurfaceFlingerInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FPSUtils {

    private static String getCurrentView() {
        String cmd = "dumpsys window | grep mFocusedWindow";
        CMDUtils.CMD_Result rs = CMDUtils.runCMD(cmd, false, true);
        if (TextUtils.isEmpty(rs.success)) {
            return "SurfaceView";
        }
        String pattern = ".*mFocusedWindow=Window\\{.* (.*)\\}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(rs.success);
        if (m.find()) {
            return m.group(1);
        } else {
            return "SurfaceView";
        }
    }


    @Nullable
    public static SurfaceFlingerInfo dumpSurfaceFlinger() {
        String cmd1 = "dumpsys SurfaceFlinger --latency clear";
        CMDUtils.runCMD(cmd1,true,false);


        String cmd = "dumpsys SurfaceFlinger --latency " + FPSUtils.getCurrentView();
        CMDUtils.CMD_Result rs = CMDUtils.runCMD(cmd, true, true);
        if (TextUtils.isEmpty(rs.success)) {
            return null;
        }
        String[] lines = rs.success.split("\n");
        String deviceRate = lines[0].trim();
        ArrayList<FrameTime> list = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] t = lines[i].split("\t");
            String t1 = t[0].trim();
            String t2 = t[1].trim();
            String t3 = t[2].trim();
            //去掉无效数据
            if (t2.equals("0") || t2.equals("9223372036854775807"))
                continue;
            FrameTime ft = new FrameTime(t1, t2, t3);
            list.add(ft);
        }
        return new SurfaceFlingerInfo(deviceRate, list);
    }


    public static String getTimeByFormat() {
        String format = "yyyy-MM-dd   HH:mm:ss";
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                format, Locale.getDefault());
        return sDateFormat.format(new java.util.Date());
    }
}
