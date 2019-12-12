package com.example.pilgrim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class NotiRegister extends AppCompatActivity {
    static RequestQueue requestQueue;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<TokenDTD> tokenList=new ArrayList<>();
    Button btn_noti_register;
    EditText edit_title,edit_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_register);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("main");
        database= FirebaseDatabase.getInstance();
        btn_noti_register=findViewById(R.id.btn_noti_register);
        edit_title=findViewById(R.id.edit_title);
        edit_message=findViewById(R.id.edit_body_text);
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
        btn_noti_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=edit_title.getText().toString();
                String message=edit_message.getText().toString();
                mainNotiRD mainNotiRD=new mainNotiRD();
                mainNotiRD.title=title;
                mainNotiRD.body=message;
                myRef.setValue(mainNotiRD);
                send(title,message);
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

