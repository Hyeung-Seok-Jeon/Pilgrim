package com.example.pilgrim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pilgrim.Listener_Event.OnTouch;
import com.example.pilgrim.Timerpack.TimerHandler;
import com.example.pilgrim.Timerpack.TimerThread;
import com.example.pilgrim.UtilClass.MBoxFunUtil;
import com.example.pilgrim.UtilClass.NotiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Survey extends AppCompatActivity {

    TimerThread timerThread;
    SurveyAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private final String TAG = "디버그용";
    RecyclerView recyclerView;
    Button call;
    ArrayList<SurveyData> dataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);

//        onSurvey_Start();
//        removeNoti(NotiUtil.CHANNEL_ID);

        FINDID();
        SurveySyncTask task = new SurveySyncTask();


        String toto = null;
        try {
            toto = task.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;
        //jsonObject.getJSONArray("response");
        try {
            assert toto != null;

            JSONArray jarray = new JSONObject(toto).getJSONArray("response");
            int count = 0;
            while (count < jarray.length()) {
                JSONObject object = jarray.getJSONObject(count);
                String[] array = {object.getString("s_Choice"), object.getString("R_One"), object.getString("R_two"), object.getString("R_three"), object.getString("R_four"), object.getString("R_five")};
                //값들을 User클래스에 묶어줍니다
                SurveyData data = new SurveyData();
                data.setSurvey(array);
                dataset.add(data);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SurveyAdapter(dataset);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);              //레이아웃매니저 만들기
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void FINDID() {
        call = findViewById(R.id.callSurvey);
        recyclerView = findViewById(R.id.survey_recycler);
    }

//    public void onSurvey_Start() {
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
//    public void complete(View v){
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
//    /***************************************fun || method Line*****************************************/
//
//    protected void removeNoti(int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManagerCompat.from(this).cancel(NotiUtil.CHANNEL_ID);
//        }
//    }
//
//    /***************************************fun || method Line*****************************************/


}


