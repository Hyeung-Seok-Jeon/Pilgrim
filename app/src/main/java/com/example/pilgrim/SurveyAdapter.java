package com.example.pilgrim;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class SurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SurveyData> data = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
