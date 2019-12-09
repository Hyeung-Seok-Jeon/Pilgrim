package com.example.pilgrim;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SurveyRegister extends AppCompatActivity {
    private List<QuestionRD> arrayList=new ArrayList<>();
    private int Q_Count=0;
    Button btn_Add_Question,btn_Delete_Question,btn_Survey_Register;
    EditText edit_title,edit_explain,edit_question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_register);

        RecyclerView recyclerView=findViewById(R.id.recycler_question);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        QuestionAdapter questionAdapter=new QuestionAdapter();
        recyclerView.setAdapter(questionAdapter);

        edit_title=findViewById(R.id.edit_survey_title);
        edit_explain=findViewById(R.id.edit_survey_explain);
        edit_question=findViewById(R.id.edit_question);
        btn_Add_Question=findViewById(R.id.btn_add_answer);
        btn_Delete_Question=findViewById(R.id.btn_delete_question);
        btn_Survey_Register=findViewById(R.id.btn_sur_register);

        btn_Add_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionRD questionRD=new QuestionRD();
                arrayList.add(questionRD);
            }
        });
    }
    class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context=parent.getContext();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.item_question,parent,false);
            QuestionAdapter.ViewHolder viewHolder=new QuestionAdapter.ViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_question;
            EditText edit_question;
            Button btn_add_answer,btn_delete_answer;
            ViewHolder(View itemView) {
                super(itemView);
                txt_question=itemView.findViewById(R.id.txt_question);
                edit_question=itemView.findViewById(R.id.edit_question);
                btn_add_answer=itemView.findViewById(R.id.btn_add_answer);
                btn_delete_answer=itemView.findViewById(R.id.btn_delete_answer);

            }

        }
    }


}
