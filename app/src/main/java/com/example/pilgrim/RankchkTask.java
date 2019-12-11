package com.example.pilgrim;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RankchkTask extends AsyncTask<String,String,String> {
    private String chk_id;

    public RankchkTask() {
        super();
    }

    public RankchkTask(String id) {
        this.chk_id = id;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = null;
        String param = "u_id=" + chk_id + "";
        Log.e("POST", param);
        try {
            /* 서버연결 */
            String ipAddress = "http://www.next-table.com/pilgrimproject/rank_check.php";
            URL url = new URL(ipAddress);
            Log.e("POST", ipAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();

            /* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes(StandardCharsets.UTF_8));
            outs.flush();
            outs.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
            InputStream is;
            BufferedReader in;


            is = conn.getInputStream();
            in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            data = stringBuilder.toString().trim();

            /* 서버에서 응답 */


            if (data != null) {
                Log.e("RESULT", "성공적으로 처리되었습니다!");
                //  return null;
            } else {
                Log.e("RESULT", "에러 발생! ERRCODE = " + data);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data ;
    }
}
