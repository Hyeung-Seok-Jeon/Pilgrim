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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Survey extends AppCompatActivity {
    ArrayList<SurveyData> dataset = new ArrayList<>();
    SurveyAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private final String TAG = "디버그용";
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Button call ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        FINDID();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);              //레이아웃매니저 만들기
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SurveyAdapter();
        recyclerView.setAdapter(adapter);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveyRefreshTask task = new SurveyRefreshTask();
                task.execute();

            }
        });





    }

    @SuppressLint("StaticFieldLeak")
    class SurveyRefreshTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            String postParameters = "dkdlrh";
            try {

                String serverURL = "http://www.next-table.com/pilgrimproject/call_survey.php";
                URL url = new URL(serverURL);   // 만든 serverURL로 URL 객체를 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();  //url을 토대로 http와 연결
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");  //
                httpURLConnection.connect();     //연결
                Log.d(TAG, "여기는 된다 오바! http 커넥션구역이다.");
                OutputStream outputStream = httpURLConnection.getOutputStream();  //데이터를 내보낼거기대문에 아웃풋스트림.
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();  //데이터를 밀어준다
                outputStream.close();  //다 보냈으면 끊어준다.

                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();

                } else {
                    inputStream = httpURLConnection.getErrorStream();

                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                Log.d(TAG, sb + "가 생성되엇따!");
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    String[] array = line.split("[/]");
                    SurveyData data1 = new SurveyData(array);
                    dataset.add(data1);
                }


                bufferedReader.close();


            } catch (Exception ignored) {


            }

            return null;
        }
    }



        public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {


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
        public SurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View view = inflater.inflate(R.layout.choice_survey, parent, false);

            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull SurveyAdapter.ViewHolder holder, int position) {

            SurveyData surveyData = dataset.get(position);
            holder.choice.setText(surveyData.getSurveyMore(0));
            holder.ans1.setText(surveyData.getSurveyMore(1));
            holder.ans2.setText(surveyData.getSurveyMore(2));
            holder.ans3.setText(surveyData.getSurveyMore(3));
            holder.ans4.setText(surveyData.getSurveyMore(4));
            holder.ans5.setText(surveyData.getSurveyMore(5));
        }


        @Override
        public int getItemCount() {
            return dataset.size();
        }
    }
        public void FINDID() {
            call = findViewById(R.id.callSurvey);

            recyclerView = findViewById(R.id.survey_recycler);

        }


    }


