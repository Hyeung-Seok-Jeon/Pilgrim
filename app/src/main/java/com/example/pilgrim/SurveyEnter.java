package com.example.pilgrim;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilgrim.Listener_Event.OnTouch;
import com.example.pilgrim.Timerpack.TimerHandler;
import com.example.pilgrim.Timerpack.TimerThread;
import com.example.pilgrim.UtilClass.MBoxFunUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SurveyEnter extends AppCompatActivity {
    private List<HeadRD> headList = new ArrayList<>();
    private List<QuestionRD> questionList = new ArrayList<>();

    private FirebaseDatabase database1, database2;
    TimerThread timerThread;

    private DatabaseReference myRef;
    TextView txt_title,txt_explain;
    Button btn_survey_complete;
    String str="전형석";
    SBPersonalResult sbPersonalResult;
    String[] choice=new String[5];
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_enter);
        database1 = FirebaseDatabase.getInstance();
        myRef=database1.getReference("PersonalResult");
        sbPersonalResult=new SBPersonalResult();
        sbPersonalResult.name=str;

        RecyclerView recyclerView=findViewById(R.id.recycier_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final QuestionAdapter questionAdapter=new QuestionAdapter();
        recyclerView.setAdapter(questionAdapter);

        txt_title=findViewById(R.id.sb_text_title);
        txt_explain=findViewById(R.id.sb_text_explain);
        btn_survey_complete=findViewById(R.id.btn_survey_complete);

        onSurvey_Start();

        btn_survey_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SBPersonalResult sbPersonalResult=new SBPersonalResult();
                sbPersonalResult.name=str;
                switch (count){
                    case 1:sbPersonalResult.my_choice1=choice[0];
                            break;
                    case 2:sbPersonalResult.my_choice1=choice[0];
                            sbPersonalResult.my_choice2=choice[1];
                            break;
                    case 3:sbPersonalResult.my_choice1=choice[0];
                            sbPersonalResult.my_choice2=choice[1];
                            sbPersonalResult.my_choice3=choice[2];
                            break;
                    case 4:sbPersonalResult.my_choice1=choice[0];
                            sbPersonalResult.my_choice2=choice[1];
                            sbPersonalResult.my_choice3=choice[2];
                            sbPersonalResult.my_choice4=choice[3];
                             break;
                    case 5:sbPersonalResult.my_choice1=choice[0];
                            sbPersonalResult.my_choice2=choice[1];
                           sbPersonalResult.my_choice3=choice[2];
                           sbPersonalResult.my_choice4=choice[3];
                           sbPersonalResult.my_choice5=choice[4];
                            break;
                }

                myRef.push().setValue(sbPersonalResult);
                onSurvey_Enrollment();
            }
        });


        database1.getReference("Survey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                headList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HeadRD headRD = snapshot.getValue(HeadRD.class);
                    headList.add(headRD);
                }
                txt_title.setText(headList.get((headList.size())-1).title);

                txt_explain.setText(headList.get((headList.size())-1).explain);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        database2 = FirebaseDatabase.getInstance();
        database2.getReference("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                questionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    QuestionRD questionRD = snapshot.getValue(QuestionRD.class);
                    questionList.add(questionRD);
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
        RadioButton radioButton;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_question, parent, false);
            QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.text_question.setText((position+1)+"."+questionList.get(position).question);
            holder.radioButton1.setText(questionList.get(position).choice1);
            holder.radioButton2.setText(questionList.get(position).choice2);
            holder.radioButton3.setText(questionList.get(position).choice3);
            holder.radioButton4.setText(questionList.get(position).choice4);
            holder.radioButton5.setText(questionList.get(position).choice5);

            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radioButton=group.findViewById(checkedId);
                        choice[count]=radioButton.getText().toString();
                        count++;

                }
            });
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_question;
            private RadioGroup radioGroup;
            private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

            ViewHolder(View view) {
                super(view);
                text_question = view.findViewById(R.id.text_questions);
                radioGroup=view.findViewById(R.id.rg_questions);
                radioGroup = view.findViewById(R.id.rg_questions);
                radioButton1 = view.findViewById(R.id.r_btn_choice1);
                radioButton2 = view.findViewById(R.id.r_btn_choice2);
                radioButton3 = view.findViewById(R.id.r_btn_choice3);
                radioButton4 = view.findViewById(R.id.r_btn_choice4);
                radioButton5 = view.findViewById(R.id.r_btn_choice5);
            }
        }
    }

    public void onSurvey_Enrollment() {
        if (timerThread != null) {
            timerThread.interrupt();
            TextView timeResultText = findViewById(R.id.Time_OutputTextView_test);
            ImageView clock_img = findViewById(R.id.watch_img_test);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.effect_rotate_right);
            clock_img.startAnimation(animation);
            animation = AnimationUtils.loadAnimation(this, R.anim.effect_alpha_sparkle);
            timeResultText.startAnimation(animation);

            String parse_date = timeResultText.getText() + "";
            String parsed_data[] = parse_date.split("[:]");
            // 문자열 파싱
            //        String parsed_date = parse_date.replaceAll("[^0-9]","");
//        myApplication.setmTime(parsed_date[0]);
//        myApplication.setsTime(parsed_date[1]);
            timeResultText.setText(parsed_data[0] + "분," + parsed_data[1] + " 초");
            new MBoxFunUtil(this, parsed_data);
        } else {
            Toast.makeText(this, "진행중인 설문조사가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSurvey_Start() {
        ImageView clock_img = findViewById(R.id.watch_img_test);
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.infinitewhile_scale_clock);
        clock_img.startAnimation(animation);

        TextView time_OutputTextView = findViewById(R.id.Time_OutputTextView_test);
        time_OutputTextView.setOnTouchListener(new OnTouch(this));

        TimerHandler timerHandler = new TimerHandler(time_OutputTextView);
        timerThread = new TimerThread(timerHandler);
        timerThread.start();
    }
}
