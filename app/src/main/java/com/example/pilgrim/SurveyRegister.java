package com.example.pilgrim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyRegister extends AppCompatActivity {

    Button btn_Question_add,btn_Survey_Register;
    EditText edit_title,edit_explain,edit_question;
    EditText[] edit_choice=new EditText[5];
    TextView text_Questions;
    FirebaseDatabase database;
    private List<TokenDTD> tokenList=new ArrayList<>();
    private DatabaseReference databaseReference,databaseReference2;
    int text_count=1;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_register);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Survey");
        databaseReference2=database.getReference("Question");
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        database.getReference("token").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tokenList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TokenDTD tokenRD=snapshot.getValue(TokenDTD.class);
                    tokenList.add(tokenRD);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        text_Questions=findViewById(R.id.txt_question);
        edit_title=findViewById(R.id.edit_survey_title);
        edit_explain=findViewById(R.id.edit_survey_explain);
        edit_question=findViewById(R.id.edit_question);
        btn_Question_add=findViewById(R.id.btn_question_add);
        btn_Survey_Register=findViewById(R.id.btn_sur_register);
        edit_choice[0]=findViewById(R.id.edit_Q_choice);
        edit_choice[1]=findViewById(R.id.edit_Q_choice2);
        edit_choice[2]=findViewById(R.id.edit_Q_choice3);
        edit_choice[3]=findViewById(R.id.edit_Q_choice4);
        edit_choice[4]=findViewById(R.id.edit_Q_choice5);
        text_Questions.setText("질문"+text_count);
        btn_Question_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_count++;
                QuestionRD questionRD=new QuestionRD();
                questionRD.question=edit_question.getText().toString();
                questionRD.choice1= edit_choice[0].getText().toString();
                questionRD.choice2= edit_choice[1].getText().toString();
                questionRD.choice3= edit_choice[2].getText().toString();
                questionRD.choice4= edit_choice[3].getText().toString();
                questionRD.choice5= edit_choice[4].getText().toString();

                databaseReference2.push().setValue(questionRD);
                text_Questions.setText("질문"+text_count);
                edit_question.setText("");
                edit_choice[0].setText("");
                edit_choice[1].setText("");
                edit_choice[2].setText("");
                edit_choice[3].setText("");
                edit_choice[4].setText("");
                Toast.makeText(SurveyRegister.this, "질문이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_Survey_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeadRD headRD=new HeadRD();
                headRD.title=edit_title.getText().toString();
                headRD.explain=edit_explain.getText().toString();
                databaseReference.push().setValue(headRD);
                send("empty",edit_title.getText().toString());
            }
        });
    }
    public void send(String header,String body) {
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();

            dataObj.put("head",header);
            dataObj.put("contents", body);

            requestData.put("data", dataObj);
            JSONArray idArray = new JSONArray();
            for(int i=0;i<tokenList.size();i++) {
                idArray.put(i, tokenList.get(i).token);
            }
//            idArray.put(1,regId);
            requestData.put("registration_ids", idArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendData(requestData, new SendResponseListener() {
            @Override
            public void onRequestCompleted() {
            }
            @Override
            public void onRequestStarted() {
            }
            @Override
            public void onRequestWithError(VolleyError error) {

            }
        });
    }

    public interface SendResponseListener {
        public void onRequestStarted();

        public void onRequestCompleted();

        public void onRequestWithError(VolleyError error);
    }
    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.POST, "https://fcm.googleapis.com/fcm/send", requestData,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestWithError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAAuXf4zYQ:APA91bFYyUCPvFiZ7mpUHMXitr4oQbMQ8clJCMWmJfv9m68JUvjhii2uiS8JOyhoxFG8uk5WaFQJnJootZ072Gt8mQjykCYO4sNX83ixxlNHp0x8TECTdnOSyqfYYZkMVy6ANUpyRYcM");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        requestQueue.add(request);
    }





}
