package cse.hansung.kr.smartdiaper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private final static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmBroadcastReceiver", "onReceive");
        Intent intent1 = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent1);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        NOTIFICATION_ID,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        //NotificationManager notificationManager = NotificationManager.from(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background) //알람 아이콘
                .setContentTitle("SmartDiaper")  //알람 제목
                .setContentText("기저귀 교체해야하는데.") //알람 내용
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //알람 중요도


        notificationManager.notify(NOTIFICATION_ID, builder.build()); //알람 생성
    }
}
