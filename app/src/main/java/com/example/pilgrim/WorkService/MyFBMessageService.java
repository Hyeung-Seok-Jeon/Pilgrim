package com.example.pilgrim.WorkService;

import android.util.Log;

import com.example.pilgrim.UtilClass.NotiUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.os.Build;
//import android.os.IBinder;
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;

public class MyFBMessageService extends FirebaseMessagingService {

//    static final String FCM = "FCM_TEST";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("fcm"+"", "onMessageReceived");

        Map<String, String> data = remoteMessage.getData(); //키, 값 자료구조 사용, 제네릭 : String
        String contents = data.get("contents");



        if(data.get("head").equals("empty"))
        {
            new NotiUtil(getApplicationContext(),contents);
        }else{
            new NotiUtil(getApplicationContext(),"head","contents",3);
        }


    }


//    @Override
//    public void onNewToken(@NonNull final String newToken) {
//        super.onNewToken(newToken);
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((Executor) this, new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                Log.d("FCM_TEST","onNewToken : "+newToken);
//            }
//        });
//    }
}









//title = remoteMessage.getNotification().getTitle();
//        msg = remoteMessage.getNotification().getBody();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        NotificationChannel notificationChannel = new NotificationChannel("alarm_channel_id", "공지사항", NotificationManager.IMPORTANCE_DEFAULT);
//        notificationManager.createNotificationChannel(notificationChannel);
//        builder = new NotificationCompat.Builder(this, notificationChannel.getId());
//        } else {
//        builder = new NotificationCompat.Builder(this);
//        }
//        builder
//        .setSmallIcon(R.drawable.prilgrimicon)
//        .setContentTitle(title)
//        .setContentText(msg)
//        .setAutoCancel(true)
//        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//        .setVibrate(new long[]{1, 1000});
//
//        notificationManager.notify(1, builder.build());