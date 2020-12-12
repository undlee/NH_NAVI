package com.t.nh_navi.uses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class InquireDepositorAccountNumber {
    //header
    String day, time, Iscd, FintechApsno, Istuno, AccessToken, apiNm;
    //body
    String Bncd, Acno;
    //얻고 싶은 값
    String AccountName;

    public InquireDepositorAccountNumber(String day, String time, String Iscd, String FintechApsno, String Istuno, String AccessToken, String Bncd, String Acno, String apiNm) throws InterruptedException, ExecutionException, IOException, JSONException {
        this.day = day;
        this.time = time;
        this.Iscd = Iscd;
        this.FintechApsno = FintechApsno;
        this.Istuno = Istuno;
        this.AccessToken = AccessToken;
        this.Bncd = Bncd;
        this.Acno = Acno;
        this.apiNm = apiNm;

        String json = bowlingJson();
        PostExample task = new PostExample();

        AsyncTask<String, Void, String> at = task.execute("https://developers.nonghyup.com/InquireDepositorAccountNumber.nh", json);

        String result = at.get();
        AccountName=new JSONObject(result).getString("Dpnm");
    } //생성자

    public String getName() {
        return AccountName;
    }

    String bowlingJson() {
        return "{\r\n    \"Header\": {\r\n        " +
                "\"ApiNm\": \"" + apiNm + "\",\r\n        " +
                "\"Tsymd\": \"" + day + "\",\r\n        " +
                "\"Trtm\": \"" + time + "\",\r\n        " +
                "\"Iscd\": \"" + Iscd + "\",\r\n        " +
                "\"FintechApsno\": \"" + FintechApsno + "\",\r\n        " +
                "\"ApiSvcCd\": \"DrawingTransferA\",\r\n        " +
                "\"IsTuno\": \"" + Istuno + "\",\r\n        " +
                "\"AccessToken\": \"" + AccessToken + "\"\r\n    },\r\n    " +
                "\"Bncd\": \"" + Bncd + "\",\r\n    " +
                "\"Acno\": \"" + Acno + "\"\r\n}";
    } //bowlingJson

    class PostExample extends AsyncTask<String, Void, String> {
        ResponseBody response;
        URL url;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, params[1]);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
//                response = client.newCall(request).execute().body();
//                Log.d("log", response.string()); //정상출력
//                return response.string();

                response = client.newCall(request).execute().body();

//                Log.d("log", response.string()); //정상출력
                return response.string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
//            try {
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
        }//doInBackground

    }//PostExample

}
