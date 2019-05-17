package cse.hansung.kr.smartdiaper;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }

    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());



        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());




            if (true) {

            } else {

                handleNow();

            }

        }

        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification());

        }

    }

    private void handleNow() {

        Log.d(TAG, "Short lived task is done.");

    }



    private void sendNotification(RemoteMessage.Notification notification) {

        NotificationManager notificationManager;

      /*  PendingIntent intent;*/


/*        intent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(), MainActivity.class),

                PendingIntent.FLAG_UPDATE_CURRENT);*/







        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle(
                new Notification.Builder(MyFirebaseMessagingService.this));

        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_stat_older : R.mipmap.ic_launcher;
        Notification.Builder builder = new Notification.Builder(this)

                .setSmallIcon(icon) // 아이콘 설정하지 않으면 오류남

                .setStyle(bigTextStyle)

                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.diaper))

                .setDefaults(Notification.DEFAULT_ALL)

                .setContentTitle(notification.getTitle()) // 제목 설정

                .setContentText(notification.getBody()) // 내용 설정

                .setTicker("한줄 출력") // 상태바에 표시될 한줄 출력

                .setAutoCancel(true)

                .setContentIntent(resultPendingIntent);

        builder.setContentIntent(resultPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        notificationManager.notify(0, builder.build());
    }
}