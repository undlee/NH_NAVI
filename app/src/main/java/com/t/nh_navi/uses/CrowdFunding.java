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

public class CrowdFunding {
    String numOfRows, pageNo, resultType, day, item;
    String serviceKey = "QPkt7YDAGR%2FNQ%2F%2BEN9tUQ%2B%2Bwz4EnTj0V1rX5ujBZNuXBYhyVrMxFHs7oPJh%2Ft8GS5NjHbLezwzBkueVYLllShg%3D%3D";

    public CrowdFunding(String day) throws InterruptedException, ExecutionException, JSONException {
        this.numOfRows = "20";
        this.pageNo = "1";
        this.resultType = "json";
        this.day=day;

        PostExample task = new PostExample();
        String url ="http://apis.data.go.kr/1160100/service/GetFundInfoService/getFundIssuCompInfo?numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&resultType=" + resultType + "&serviceKey=" + serviceKey;

        AsyncTask<String, Void, String> at = task.execute(url);

        String result = at.get();
        Log.d("CrowdFunding", result);
        item=new JSONObject(result).getJSONObject("response").getJSONObject("body").getJSONObject("items").getString("item");
        Log.d("CrowdFunding", item);
    } //생성자

    public String Item() {
        return item;
    }


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
//            RequestBody body = RequestBody.create(mediaType, params[1]);
            Request request = new Request.Builder()
                    .url(url)
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

