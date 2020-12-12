package com.t.nh_navi.uses;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class NaverApi {
    String result;
    Map<String, String> requestHeaders;
    String clientId = "6ngta1MgLEKNfqLnvgNN"; //애플리케이션 클라이언트 아이디값"
    String clientSecret = "jt56FlenK2"; //애플리케이션 클라이언트 시크릿값"
    String text = null;
    String institute;

    public NaverApi(String institute) throws ExecutionException, InterruptedException {
        this.institute = institute;

        try {
            text = URLEncoder.encode(institute, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/encyc.json?query=" + text +"&display=1";    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        PostExample task = new PostExample();
        AsyncTask<String, Void, String> at = task.execute(apiURL);
//        AsyncTask<String, Void, String> at = task.execute("https://developers.nonghyup.com/DrawingTransfer.nh", json);
//        String responseBody = get(apiURL,requestHeaders);
        result = at.get();
//        Log.d("naverapi", result);
//        RgsnYmd=new JSONObject(result).getString("RgsnYmd");

    }

    public String getJson(){
        return result;
    }

    class PostExample extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection con = connect(params[0]);
            Log.d("con", String.valueOf(con));
            try {
                con.setRequestMethod("GET");
                for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                    return readBody(con.getInputStream());
                } else { // 에러 발생
                    return readBody(con.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect();
            }
        }//doInBackground

    }//PostExample


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}