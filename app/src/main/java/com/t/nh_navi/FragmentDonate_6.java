package com.t.nh_navi;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.t.nh_navi.uses.NaverApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDonate_6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDonate_6 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewGroup viewGroup;
    Elements elements;
    ImageView img_place;
    TextView palce_info;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDonate_6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDonate_6.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDonate_6 newInstance(String param1, String param2) {
        FragmentDonate_6 fragment = new FragmentDonate_6();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //R.layout 이름바꾸기
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_donate_6, container, false);
        TextView place_name = (TextView)viewGroup.findViewById(R.id.place_name);
        palce_info = (TextView)viewGroup.findViewById(R.id.palce_info);
        img_place = (ImageView) viewGroup.findViewById(R.id.img_place);
//        CardView card_place = (CardView) viewGroup.findViewById(R.id.card_place);


        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        Log.d("name", name);
        place_name.setText(name);

        // 네이버 API에 검색
        String naverApi = null;
        try {
            naverApi = new NaverApi(name).getJson();
//            Log.d("naver", String.valueOf(naverApi));
            String json= new JSONObject(naverApi).getString("items");
            Log.d("total", json);
            JSONObject jSONObject = (JSONObject) new JSONArray(json).get(0);
//            String description= json.get(0);;
//            Log.d("total", String.valueOf(jSONObject));
//            String description=jSONObject.getString("description");
//            String thumbnail=jSONObject.getString("thumbnail");
            String link=jSONObject.getString("link");
            Log.d("total", link);
            new GetData().execute(link);
            //url로 이미지 출력
//            ivImage = findViewById(R.id.iv_image);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return viewGroup;
    }

    //institute image, content
    private class GetData extends AsyncTask<String, Void, String> {
        // String 으로 값을 전달받은 값을 처리하고, Boolean 으로 doInBackground 결과를 넘겨준다.
        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect(params[0]).get(); // Web에서 내용을 가져온다.
//                Log.d("docu", String.valueOf(document));
                elements = document.select("#size_ct > p");
//                Element targetElement1 = elements.get(0);
                Log.d("내용", String.valueOf(elements));
//                #size_ct > div > div > span > div > a
                String pagePicUrl = document.select("#size_ct > div > div > span > div > a").attr("href");
                Log.d("사진url", String.valueOf(pagePicUrl));
                String[] amp = pagePicUrl.split("imageUrl=");
                Log.d("amp", amp[1]);
                String imageUrl = URLDecoder.decode(amp[1], "UTF-8");
                Log.d("imageUrl", imageUrl);
//
                return imageUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            Glide.with(getActivity()).load(imageUrl).override(400, 400).into(img_place);
        }
    }
}