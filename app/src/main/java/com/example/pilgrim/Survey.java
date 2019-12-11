package com.example.pilgrim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Survey extends AppCompatActivity {

    SurveyAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView;
    Button call ;
    ArrayList<SurveyData> dataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        FINDID();
        SurveySyncTask task = new SurveySyncTask();


        String toto = null;
        try {
            toto = task.execute().get();
        } catch (ExecutionException | InterruptedException e) {
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
    }


