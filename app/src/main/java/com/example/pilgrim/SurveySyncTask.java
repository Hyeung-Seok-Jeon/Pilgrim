package com.example.pilgrim;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SurveySyncTask extends AsyncTask<String, Void, String> {
    ArrayList<SurveyData> syncdataset = new ArrayList<>();
    private final String TAG = "디버그용";
    String data;
    String jsonFIle;

    @Override
    protected String doInBackground(String... params) {
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
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                String[] array = line.split("[/]");
                Log.d(TAG, "doInBackground: "+array.length);
                SurveyData data1 = new SurveyData();
                data1.setSurvey(array);
               syncdataset.add(data1);
            }


            bufferedReader.close();

            return sb.toString();

        } catch (Exception ignored) {


        }

        return null;
    }




    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}

