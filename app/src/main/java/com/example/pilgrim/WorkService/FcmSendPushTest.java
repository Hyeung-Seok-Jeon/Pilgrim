package com.example.pilgrim.WorkService;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pilgrim.R;
import com.example.pilgrim.TokenDTD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FcmSendPushTest extends AppCompatActivity {
    EditText editText;
    TextView textView;
    FirebaseInstanceId firebaseInstanceId;
    JSONArray idArray;
    private List<TokenDTD> tokenList=new ArrayList<>();
    static RequestQueue requestQueue;
    private FirebaseDatabase database_token,database;
    private DatabaseReference myRef;
    static String regId = "dFYLtHrZu3w:APA91bH3ApOmCH7gcgyHjLrGbr-gdJ4IfgDXwGTi1X9NXjKI6LrpM_fkSoLb2ldR63UlO0UUsiLobIZTu5vALkxwkDSpx_RsBMNAGcCAOSRbL9CxrhSKohizZVcact4uF2gHSP2quici";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_send_push_test);
        firebaseInstanceId=FirebaseInstanceId.getInstance();
        editText = findViewById(R.id.Fcm_send_edit);
        textView = findViewById(R.id.Fcm_send_text);

        database=FirebaseDatabase.getInstance();
        database_token=FirebaseDatabase.getInstance();
        myRef=database_token.getReference("token");
        Button button = findViewById(R.id.Fcm_send_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                send("empty",input);

            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        firebaseInstanceId.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("FIREBASE", "getInstanceId failed", task.getException());
                    return;
                }

                TokenDTD tokenDT=new TokenDTD();
                tokenDT.token= task.getResult().getToken();
                myRef.push().setValue(tokenDT);

// Log and toast
//String msg = getString(R.string.msg_token_fmt, token);

            }


        });
        database.getReference("token").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tokenList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TokenDTD tokenDTD=snapshot.getValue(TokenDTD.class);
                    tokenList.add(tokenDTD);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void send(String header,String input) {
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();
            dataObj.put("head", header);
            dataObj.put("contents", input);

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