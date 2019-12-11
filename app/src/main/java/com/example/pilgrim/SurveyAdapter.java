package com.example.pilgrim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    private List<SurveyData> data;

    public SurveyAdapter(ArrayList<SurveyData> data) {
        this.data=data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView choice;
        RadioGroup radioGroup;
        RadioButton ans1, ans2, ans3, ans4, ans5;

        ViewHolder(View itemView) {
            super(itemView);
            radioGroup = itemView.findViewById(R.id.radiogroup);
            choice = itemView.findViewById(R.id.choice);
            ans1 = itemView.findViewById(R.id.ans1);
            ans2 = itemView.findViewById(R.id.ans2);
            ans3 = itemView.findViewById(R.id.ans3);
            ans4 = itemView.findViewById(R.id.ans4);
            ans5 = itemView.findViewById(R.id.ans5);


        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;


        View view = inflater.inflate(R.layout.choice_survey, parent, false) ;

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SurveyData surveyData = data.get(position);
        holder.choice.setText(surveyData.getSurveyMore(0));
        holder.ans1.setText(surveyData.getSurveyMore(1));
        holder.ans2.setText(surveyData.getSurveyMore(2));
        holder.ans3.setText(surveyData.getSurveyMore(3));
        holder.ans4.setText(surveyData.getSurveyMore(4));
        holder.ans5.setText(surveyData.getSurveyMore(5));


    }


    @Override
    public int getItemCount() {
        return data.size();
    }


}

