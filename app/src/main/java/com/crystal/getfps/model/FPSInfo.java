package com.crystal.getfps.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Information about FPS, e.g, fps, the numbers of frames,how many times jank happens
 */

public class FPSInfo {
    double fps;
    int maxVsyncCount;
    String time;
    int frame_num;
    int jank;
    double maxTime;
    int overTargetFpsNum;
    double score;

    public FPSInfo(String time, double fps, int frame_num, int jank, double maxTime, int overTargetFpsNum, long deviceRate) {
        this.time = time;
        this.fps = fps;
        this.frame_num = frame_num;
        this.jank = jank;
        this.maxTime = maxTime;
        this.overTargetFpsNum = overTargetFpsNum;
        this.maxVsyncCount = (int) (maxTime / deviceRate);

    }

    /**
     * custom your score by here
     * @return
     */
    private double getScore() {
        return 0;
    }

    @Override
    public String toString() {
        JSONObject j = toJson();
        if (j != null)
            return toJson().toString();
        return "NULL";
    }

    public JSONObject toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("Time", time);
            json.put("fps", fps);
            json.put("frame_num", frame_num);
            json.put("jank", jank);
            json.put("max_time", maxTime);
            json.put("maxVsyncCount", maxVsyncCount);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public int getMaxVsyncCount() {
        return maxVsyncCount;
    }

    public void setMaxVsyncCount(int maxVsyncCount) {
        this.maxVsyncCount = maxVsyncCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFrame_num() {
        return frame_num;
    }

    public void setFrame_num(int frame_num) {
        this.frame_num = frame_num;
    }

    public int getJank() {
        return jank;
    }

    public void setJank(int jank) {
        this.jank = jank;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public int getOverTargetFpsNum() {
        return overTargetFpsNum;
    }

    public void setOverTargetFpsNum(int overTargetFpsNum) {
        this.overTargetFpsNum = overTargetFpsNum;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
