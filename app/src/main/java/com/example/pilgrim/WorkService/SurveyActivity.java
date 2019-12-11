//package com.example.pilgrim.WorkService;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.example.pilgrim.Listener_Event.OnTouch;
//import com.example.pilgrim.R;
//import com.example.pilgrim.Timerpack.TimerHandler;
//import com.example.pilgrim.Timerpack.TimerThread;
//import com.example.pilgrim.UtilClass.MBoxFunUtil;
//import com.example.pilgrim.UtilClass.NotiUtil;
//
//public class SurveyActivity extends AppCompatActivity {
//
//    TimerThread timerThread;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_survey);
//
//        removeNoti(NotiUtil.CHANNEL_ID);
//
//    }
//
//    /***************************************OnClick || Call_Back Line*****************************************/
//
//    public void onSurvey_Start(View v) {
//        ImageView clock_img = findViewById(R.id.watch_img);
//        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.infinitewhile_scale_clock);
//        clock_img.startAnimation(animation);
//
//        TextView time_OutputTextView = findViewById(R.id.Time_OutputTextView);
//        time_OutputTextView.setOnTouchListener(new OnTouch(this));
//
//        TimerHandler timerHandler = new TimerHandler(time_OutputTextView);
//        timerThread = new TimerThread(timerHandler);
//        timerThread.start();
//    }
//
//    public void onSurvey_Enrollment(View v) {
//        if (timerThread != null) {
//            timerThread.interrupt();
//            TextView timeResultText = findViewById(R.id.Time_OutputTextView);
//            ImageView clock_img = findViewById(R.id.watch_img);
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.effect_rotate_right);
//            clock_img.startAnimation(animation);
//            animation = AnimationUtils.loadAnimation(this, R.anim.effect_alpha_sparkle);
//            timeResultText.startAnimation(animation);
//
//            String parse_date = timeResultText.getText() + "";
//            String parsed_data[] = parse_date.split("[:]");
//            // 문자열 파싱
//            //        String parsed_date = parse_date.replaceAll("[^0-9]","");
////        myApplication.setmTime(parsed_date[0]);
////        myApplication.setsTime(parsed_date[1]);
//            timeResultText.setText(parsed_data[0] + "분," + parsed_data[1] + " 초");
//            new MBoxFunUtil(this, parsed_data);
//        } else {
//            Toast.makeText(this, "진행중인 설문조사가 없습니다.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void onCreateSurvey(View v) {
//        new NotiUtil(this, "getText");
//        new NotiUtil(this, "getHead", "getBody", NotiUtil.SOUND_AND_VIBRATE);
//    }
//
//    /***************************************OnClick || Call_Back Line*****************************************/
//
//    /***************************************fun || method Line*****************************************/
//
//    protected void removeNoti(int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManagerCompat.from(this).cancel(NotiUtil.CHANNEL_ID);
//        }
//    }
//
//    /***************************************fun || method Line*****************************************/
//
//}