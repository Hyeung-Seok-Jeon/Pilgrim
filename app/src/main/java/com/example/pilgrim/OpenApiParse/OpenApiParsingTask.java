package com.example.pilgrim.OpenApiParse;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pilgrim.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OpenApiParsingTask extends AsyncTask<String, Void, String> {
    private String str, receiveMsg;
    private Context context;
    private TextView textView;

    public OpenApiParsingTask(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
//            url = new URL("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?" +
//                    "serviceKey=yIEAackyA9sqolvWEyLp8CPxOr9%2F1cq%2Bk2w%2FnD77dR6b%2BNjsg5mKzVI6X6KYPf0kRx8y5RvRGSXCe%2Ft6xqSwmA%3D%3D" +
//                    "&sidoName=%EC%9D%B8%EC%B2%9C&_returnType=json");

            url = new URL("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=yIEAackyA9sqolvWEyLp8CPxOr9%2F1cq%2Bk2w%2FnD77dR6b%2BNjsg5mKzVI6X6KYPf0kRx8y5RvRGSXCe%2Ft6xqSwmA%3D%3D" +
                    "&numOfRows=1&pageNo=8&sidoName=%EC%9D%B8%EC%B2%9C&searchCondition=HOUR&_returnType=json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder buffer = new StringBuilder();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                    Log.d("conn", buffer + "");
                }

                receiveMsg = buffer.toString();
                reader.close();
            } else {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String resultText) {
        textView.setText(resultText);
        String[] str = get_Parse(resultText); //[0] = 지역 [1] = 시간 [2] = 수치(값)
        String dustLevel;
        if (Integer.parseInt(str[2]) < 30) {
            textView.setTextColor(Color.GREEN);
            dustLevel = "좋음";
        } else if (Integer.parseInt(str[2]) < 80) {
            textView.setTextColor(Color.BLUE);
            dustLevel = "보통";
        } else if (Integer.parseInt(str[2]) < 150) {
            textView.setTextColor(Color.RED);
            dustLevel = "나쁨";
        } else {
            textView.setTextColor(Color.RED);
            dustLevel = "매우나쁨";
        }

        textView.setText("미세먼지수치 : " + str[2] + " 수준 : " + dustLevel);
        Toast.makeText(context, "미세먼지 수치는 부정확할 수 있음\n" +
                "기준 - " + str[0] + " : " + str[1] + "\n" + "           정보제공 : 에어코리아 ", Toast.LENGTH_SHORT).show();
    }

    public static String[] get_Parse(String jsonString) {

        String[] arraysum = new String[3];
        String addr;
        String time;
        String value;

        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("list");
            for (int i = 0; i < 1;/*jarray.length();*/ i++) { //맨처음 (연수) 하나만 받음

                JSONObject jObject = jarray.getJSONObject(i);

                time = jObject.optString("dataTime");
                addr = jObject.optString("cityName");
                value = jObject.optString("pm10Value");

                arraysum[0] = addr;
                arraysum[1] = time;
                arraysum[2] = value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }

}