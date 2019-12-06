package com.example.pilgrim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class SignWelcom extends Activity {
    FirebaseInstanceId firebaseInstanceId;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database_token;
    private List<TokenDTD> tokenList=new ArrayList<>();
    Button btn_login_move;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_welcom);
        database_token=FirebaseDatabase.getInstance();
        databaseReference=database_token.getReference("token");
        firebaseInstanceId= FirebaseInstanceId.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//정방향 세로로 완전히 고정,회전불가
        btn_login_move=findViewById(R.id.btn_login_move);
        //회원가입 이후 SignWelcom Activity가 실행되면 토큰 값을 데이터베이스에 넣는다.
        firebaseInstanceId.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("FIREBASE", "getInstanceId failed", task.getException());
                    return;
                }
                TokenDTD tokenDTD=new TokenDTD();
                tokenDTD.token= task.getResult().getToken();
                databaseReference.push().setValue(tokenDTD);
            }

        });
        btn_login_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

    }
    @Override//background 클릭시 activity 종료 방지
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds=new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if(!dialogBounds.contains((int)ev.getX(),(int)ev.getY())){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
