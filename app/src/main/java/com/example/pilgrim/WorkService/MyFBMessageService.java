package com.example.pilgrim.WorkService;

import android.util.Log;

import com.example.pilgrim.UtilClass.NotiUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFBMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("fcm" + "", "onMessageReceived");

        Map<String, String> data = remoteMessage.getData(); //키, 값 자료구조 사용, 제네릭 : String
        String contents = data.get("contents");
        String head = data.get("head");

        new NotiUtil(getApplicationContext(), head, contents, 3);

    }
}