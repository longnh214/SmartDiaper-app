package cse.hansung.kr.smartdiaper;

import android.os.Handler;
import android.os.Message;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class CycleJsonActivity extends JobService{


    public boolean onStartJob(JobParameters job) {

        ConnectServer.requestGet("http://192.168.0.2:8080/smartDiaper/cycle","key");

            handler.sendEmptyMessage(0);

        return false;
    }



    public boolean onStopJob(JobParameters job) {
        return false; // Answers the question: "Should this job be retried?"
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
}
