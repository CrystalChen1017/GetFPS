package com.crystal.getfps.utils;



import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CMDUtils {

    public static class CMD_Result {
        public int resultCode;
        public String error;
        public String success;

        public CMD_Result(int resultCode, String error, String success) {
            this.resultCode = resultCode;
            this.error = error;
            this.success = success;
        }

    }

    public static CMD_Result runCMD(String cmd, boolean isShowCommand, boolean isNeedResultMsg) {
        String[] command = {"/system/bin/sh", "-c", cmd};
        if (isShowCommand)
            Log.d("autotest", "CMDUtils_runCMD: "+cmd);
        CMD_Result cmdRsult = null;
        int result;
        try {
            Process process = Runtime.getRuntime().exec(command);
            result = process.waitFor();
            if (isNeedResultMsg) {
                StringBuilder successMsg = new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s).append("\n");
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s).append("\n");
                }
                cmdRsult = new CMD_Result(result, errorMsg.toString(),
                        successMsg.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmdRsult;
    }




}
