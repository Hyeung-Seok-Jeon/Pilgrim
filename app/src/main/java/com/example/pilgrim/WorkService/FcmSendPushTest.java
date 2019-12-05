package com.example.pilgrim.WorkService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pilgrim.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmSendPushTest extends AppCompatActivity {
    EditText editText;
    TextView textView;

    static RequestQueue requestQueue;
    static String regId = "dFYLtHrZu3w:APA91bH3ApOmCH7gcgyHjLrGbr-gdJ4IfgDXwGTi1X9NXjKI6LrpM_fkSoLb2ldR63UlO0UUsiLobIZTu5vALkxwkDSpx_RsBMNAGcCAOSRbL9CxrhSKohizZVcact4uF2gHSP2quici";

    //hy="fdH8EQXcZGA:APA91bGLXgcss3gr7cAQNgNE54BsYT-UMYHR8ZjMZXw8G9W5zrk6biGInr_afK8VLW7Li4TzpSQZZQy-QwTZGiWliam5Mng_LCN4UnwEoQK1vDoEb09npiU4fvd4EP6_bXc3zJtnxIro";
    //my="d_AOlbdIHq4:APA91bHWm4gAR99PcBLhSMM2mLxW6pajjkbX0Ab0C-tyEOVRn0bnWOF14afpBqg4D38rmrr8Vj8sFrrgSJs4czrFG9ZxsaJSA8nKFdGQjq3wZJzuyFwUeAC0-bpbIxleuRQEuoDB4ERH";
    //dFYLtHrZu3w:APA91bH3ApOmCH7gcgyHjLrGbr-gdJ4IfgDXwGTi1X9NXjKI6LrpM_fkSoLb2ldR63UlO0UUsiLobIZTu5vALkxwkDSpx_RsBMNAGcCAOSRbL9CxrhSKohizZVcact4uF2gHSP2quici

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_send_push_test);

        editText = findViewById(R.id.Fcm_send_edit);
        textView = findViewById(R.id.Fcm_send_text);

        Button button = findViewById(R.id.Fcm_send_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                send(input);
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void send(String input) {
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();

            dataObj.put("contents", input);
            requestData.put("data", dataObj);
            JSONArray idArray = new JSONArray();

            idArray.put(0, regId);
//            idArray.put(1,regId);
            requestData.put("registration_ids", idArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendData(requestData, new SendResponseListener() {

            @Override
            public void onRequestCompleted() {
                println("onRequestCompleted() 호출됨.");
            }

            @Override
            public void onRequestStarted() {
                println("onRequestStarted() 호출됨.");
            }

            @Override
            public void onRequestWithError(VolleyError error) {
                println("onRequestWithError() 호출됨.");
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


    public void println(String data) {
        textView.append(data + "\n");
    }
}