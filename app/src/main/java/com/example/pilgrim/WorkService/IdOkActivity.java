package com.example.pilgrim.WorkService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pilgrim.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class IdOkActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_ok);

        textView = findViewById(R.id.idCheckText1);
        textView2 = findViewById(R.id.idCheckText2);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult result) { // Intro onCreate에 동일 코드 있음, TAG명 FCM_TEST로 확인
                String newToken = result.getToken();

                println("등록 id : " + newToken);
                Log.d("FCM",newToken);
            }
        });

        Button button = findViewById(R.id.button_id_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instanceId = FirebaseInstanceId.getInstance().getId();

                println("확인된 인스턴스 id : " + instanceId);
//                Log.d(R.string.fcm_test+"",instanceId);
            }
        });
    }

    public void println(String data) {
        textView2.append(data + "\n");
        Log.d("FMS", data);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");
        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }

        String contents = intent.getStringExtra("contents");
        println("DATA : " + from + ", " + contents);
        textView.setText("[" + from + "]로부터 수신한 데이터 : " + contents);
    }

}
