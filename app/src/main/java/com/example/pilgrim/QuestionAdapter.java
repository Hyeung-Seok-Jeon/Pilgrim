package com.example.pilgrim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    List<QuestionRD> questions=new ArrayList<>();
    int count=1;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_question.setText("질문");
    }
    @Override
    public int getItemCount() {
        return questions.size();
    }
}
