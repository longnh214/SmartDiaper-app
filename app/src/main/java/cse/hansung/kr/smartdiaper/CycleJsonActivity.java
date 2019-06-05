package cse.hansung.kr.smartdiaper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class CycleJsonActivity extends JobService{


    private static String ipAddress;

    public static void setIpAddress(String ip){
        ipAddress = ip;
    }

    public static String getIpAddress(){
        return ipAddress;
    }

    public boolean onStartJob(JobParameters job) {
        Log.d("ggggg","cycleRequest");
        //ConnectServer.requestGet("http://" + ipAddress + ":8080/smartDiaper/cycle","key");
        ConnectServer.requestGet("http://192.168.0.2:8080/smartDiaper/cycle","key");
            handler.sendEmptyMessage(0);

        return false;
    }



    public boolean onStopJob(JobParameters job) {
        return true; // Answers the question: "Should this job be retried?"
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
}
