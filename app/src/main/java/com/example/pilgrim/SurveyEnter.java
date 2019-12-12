package com.example.pilgrim;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
    RadioGroup[] radioGroup=new RadioGroup[5];
    RadioButton[] radioButton=new RadioButton[25];
    RadioButton SelectedRadioButton;
    TextView[] textView=new TextView[5];
    private FirebaseDatabase database1, database2;
    TimerThread timerThread;

    private DatabaseReference myRef;
    TextView txt_title,txt_explain;
    Button btn_survey_complete;
    String str="";
    SBPersonalResult sbPersonalResult;
    String[] choice=new String[5];
    int count=0;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_enter);
        prefs = getSharedPreferences("PrefName", Activity.MODE_PRIVATE);

        database1 = FirebaseDatabase.getInstance();
        myRef=database1.getReference("PersonalResult");
        str=prefs.getString("name","");
        sbPersonalResult=new SBPersonalResult();
        sbPersonalResult.name=str;

     /*   RecyclerView recyclerView=findViewById(R.id.recycier_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       final QuestionAdapter questionAdapter=new QuestionAdapter();
      recyclerView.setAdapter(questionAdapter);*/
        findId();
        txt_title=findViewById(R.id.sb_text_title);
        txt_explain=findViewById(R.id.sb_text_explain);
        btn_survey_complete=findViewById(R.id.btn_survey_complete);
        textView[0].setText("없음");
        textView[1].setText("없음");
        textView[2].setText("없음");
        textView[3].setText("없음");
        textView[4].setText("없음");
        radioButton[0].setText("없음");
        radioButton[1].setText("없음");
        radioButton[2].setText("없음");
        radioButton[3].setText("없음");
        radioButton[4].setText("없음");
        radioButton[5].setText("없음");
        radioButton[6].setText("없음");
        radioButton[7].setText("없음");
        radioButton[8].setText("없음");
        radioButton[9].setText("없음");
        radioButton[10].setText("없음");
        radioButton[11].setText("없음");
        radioButton[12].setText("없음");
        radioButton[13].setText("없음");
        radioButton[14].setText("없음");
        radioButton[15].setText("없음");
        radioButton[16].setText("없음");
        radioButton[17].setText("없음");
        radioButton[18].setText("없음");
        radioButton[19].setText("없음");
        radioButton[20].setText("없음");
        radioButton[21].setText("없음");
        radioButton[22].setText("없음");
        radioButton[23].setText("없음");
        radioButton[24].setText("없음");
        onSurvey_Start();

        btn_survey_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SBPersonalResult sbPersonalResult=new SBPersonalResult();
                sbPersonalResult.name=str;
                for(int i=0;i<choice.length;i++){
                    if(choice[i]==null)
                        count++;
                }
                if(count==0) {
                    sbPersonalResult.my_choice1 = choice[0];
                    sbPersonalResult.my_choice2 = choice[1];
                    sbPersonalResult.my_choice3 = choice[2];
                    sbPersonalResult.my_choice4 = choice[3];
                    sbPersonalResult.my_choice5 = choice[4];
                    myRef.push().setValue(sbPersonalResult);
                    onSurvey_Enrollment();
                }
                else{
                    Toast.makeText(SurveyEnter.this, "입력하지 않은 질문이 있습니다.", Toast.LENGTH_SHORT).show();
                    count=0;
                }
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
               // questionAdapter.notifyDataSetChanged();
                textView[0].setText("1."+questionList.get(0).question);
                textView[1].setText("2."+questionList.get(1).question);
                textView[2].setText("3."+questionList.get(2).question);
                textView[3].setText("4."+questionList.get(3).question);
                textView[4].setText("5."+questionList.get(4).question);
                radioButton[0].setText(questionList.get(0).choice1);
                radioButton[1].setText(questionList.get(0).choice2);
                radioButton[2].setText(questionList.get(0).choice3);
                radioButton[3].setText(questionList.get(0).choice4);
                radioButton[4].setText(questionList.get(0).choice5);
                radioButton[5].setText(questionList.get(1).choice1);
                radioButton[6].setText(questionList.get(1).choice2);
                radioButton[7].setText(questionList.get(1).choice3);
                radioButton[8].setText(questionList.get(1).choice4);
                radioButton[9].setText(questionList.get(1).choice5);
                radioButton[10].setText(questionList.get(2).choice1);
                radioButton[11].setText(questionList.get(2).choice2);
                radioButton[12].setText(questionList.get(2).choice3);
                radioButton[13].setText(questionList.get(2).choice4);
                radioButton[14].setText(questionList.get(2).choice5);
                radioButton[15].setText(questionList.get(3).choice1);
                radioButton[16].setText(questionList.get(3).choice2);
                radioButton[17].setText(questionList.get(3).choice3);
                radioButton[18].setText(questionList.get(3).choice4);
                radioButton[19].setText(questionList.get(3).choice5);
                radioButton[20].setText(questionList.get(4).choice1);
                radioButton[21].setText(questionList.get(4).choice2);
                radioButton[22].setText(questionList.get(4).choice3);
                radioButton[23].setText(questionList.get(4).choice4);
                radioButton[24].setText(questionList.get(4).choice5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });




        radioGroup[0].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SelectedRadioButton=group.findViewById(checkedId);
                choice[0]=SelectedRadioButton.getText().toString();
            }
        });
        radioGroup[1].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SelectedRadioButton=group.findViewById(checkedId);
                choice[1]=SelectedRadioButton.getText().toString();
            }
        });
        radioGroup[2].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SelectedRadioButton=group.findViewById(checkedId);
                choice[2]=SelectedRadioButton.getText().toString();
            }
        });
        radioGroup[3].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SelectedRadioButton=group.findViewById(checkedId);
                choice[3]=SelectedRadioButton.getText().toString();
            }
        });
        radioGroup[4].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SelectedRadioButton=group.findViewById(checkedId);
                choice[4]=SelectedRadioButton.getText().toString();
            }
        });

    }

   /* class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
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
    }*/

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
    public void findId(){
        textView[0]=findViewById(R.id.se_txt_question1);
        textView[1]=findViewById(R.id.se_txt_question2);
        textView[2]=findViewById(R.id.se_txt_question3);
        textView[3]=findViewById(R.id.se_txt_question4);
        textView[4]=findViewById(R.id.se_txt_question5);
        radioGroup[0]=findViewById(R.id.radiogroup1);
        radioGroup[1]=findViewById(R.id.radiogroup2);
        radioGroup[2]=findViewById(R.id.radiogroup3);
        radioGroup[3]=findViewById(R.id.radiogroup4);
        radioGroup[4]=findViewById(R.id.radiogroup5);
        radioButton[0]=findViewById(R.id.rg1_radiobutton);
        radioButton[1]=findViewById(R.id.rg1_radiobutton2);
        radioButton[2]=findViewById(R.id.rg1_radiobutton3);
        radioButton[3]=findViewById(R.id.rg1_radiobutton4);
        radioButton[4]=findViewById(R.id.rg1_radiobutton5);
        radioButton[5]=findViewById(R.id.rg2_radiobutton);
        radioButton[6]=findViewById(R.id.rg2_radiobutton2);
        radioButton[7]=findViewById(R.id.rg2_radiobutton3);
        radioButton[8]=findViewById(R.id.rg2_radiobutton4);
        radioButton[9]=findViewById(R.id.rg2_radiobutton5);
        radioButton[10]=findViewById(R.id.rg3_radiobutton);
        radioButton[11]=findViewById(R.id.rg3_radiobutton2);
        radioButton[12]=findViewById(R.id.rg3_radiobutton3);
        radioButton[13]=findViewById(R.id.rg3_radiobutton4);
        radioButton[14]=findViewById(R.id.rg3_radiobutton5);
        radioButton[15]=findViewById(R.id.rg4_radiobutton);
        radioButton[16]=findViewById(R.id.rg4_radiobutton2);
        radioButton[17]=findViewById(R.id.rg4_radiobutton3);
        radioButton[18]=findViewById(R.id.rg4_radiobutton4);
        radioButton[19]=findViewById(R.id.rg4_radiobutton5);
        radioButton[20]=findViewById(R.id.rg5_radiobutton);
        radioButton[21]=findViewById(R.id.rg5_radiobutton2);
        radioButton[22]=findViewById(R.id.rg5_radiobutton3);
        radioButton[23]=findViewById(R.id.rg5_radiobutton4);
        radioButton[24]=findViewById(R.id.rg5_radiobutton5);

    }
}
