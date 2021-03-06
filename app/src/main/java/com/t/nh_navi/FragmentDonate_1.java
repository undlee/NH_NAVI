package com.t.nh_navi;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDonate_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDonate_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewGroup viewGroup;
    //Excel
    ListView list_excel;
    ArrayAdapter<String> arrayAdapter;
    String[] instituteName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDonate_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDonate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDonate_1 newInstance(String param1, String param2) {
        FragmentDonate_1 fragment = new FragmentDonate_1();
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

        //R.layout 이름바꾸기
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_donate_1, container, false);
        //Excel
        list_excel = (ListView) viewGroup.findViewById(R.id.listview1);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        Button recommend = (Button) viewGroup.findViewById(R.id.btn_recommend);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment 연결
                Fragment fragment = new FragmentDonate_2(); // Fragment 생성 B
                getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commitAllowingStateLoss();

            }
        });

//        Log.d("result", String.valueOf(resultArray));
//        //정렬
//        Collections.sort(resultArray);
//        Log.d("result", String.valueOf(resultArray));
//
//        Log.d("result", String.valueOf(resultArray.size()));

        //버튼 클릭시 - 다른 액티비티에도 적용

        ArrayList resultArr = Excel();

        list_excel.setAdapter(arrayAdapter);
        list_excel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Log.d("arrayList", (String) resultArr.get(position));
                //어댑터 위치 받아 시설명 출력 다음페이지 전환

                Fragment fragment = new FragmentDonate_6(); // Fragment 생성 B
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) resultArr.get(position));
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commitAllowingStateLoss();

            }
        });

        return viewGroup;
    }

    public ArrayList Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        ArrayList resultArray = new ArrayList();
        String[] arr_selectInstitute = new String[6];
        ArrayList<String> find = new ArrayList();
        try {
            InputStream inputStream = getActivity().getResources().getAssets().open("한국사회복지협의회_사회복지자원봉사 관리센터 정보_20200701.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0); //0번째 시트
            int rowCount = sheet.getRows();
            //pref 받아옴
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
            String st_selectInstitute = sharedPreferences.getString("selectInstitute", null);


            //문자열 배열로 바꾸기
            if (st_selectInstitute != null) {
                arr_selectInstitute = st_selectInstitute.replaceAll("\\[|\\]| ", "").split(",");
                Log.d("find", String.valueOf(arr_selectInstitute.length));
                Log.d("find", Arrays.toString(arr_selectInstitute));
                instituteName = new String[]{"아동", "장애인", "자연재해", "한부모", "노인", "유기견"};
                Log.d("find", Arrays.toString(instituteName));
                for (int i = 0; i < arr_selectInstitute.length; i++) {
                    Log.d("find", String.valueOf(i));
                    if (arr_selectInstitute[i].equals("1")) {
                        find.add(instituteName[i]);
                    }
                }
                for(int i=0; i<find.size(); i++){
                    Log.d("find", find.get(i));
                }

            }

            for (int i = 1; i < rowCount; i++) {
                Cell[] row = sheet.getRow(i);
                //instituteName 사용

                //1열 관리센터명
                //2열 센터유형
                //2열
                //아동 - 아동복지시설
                //어르신 - 노인복지시설
                //장애인 - 장애인복지시설
                //1열
                //한부모 - 한부모 들어감
                //자연재해, 유기견 데이터 없음
//                if()
//                resultArray.add(row[1].getContents());
//                arrayAdapter.add(row[1].getContents());
                if (find.size()>0) {
//                    for(int k=0; k<arr_selectInstitute.length; k++){
//                        Log.d("excel", arr_selectInstitute[k] + instituteName[k]);
//                    }
                    for (int j = 0; j < find.size(); j++) {
//                            Log.d("aa", instituteName[j]);
                        if (row[1].getContents().contains(find.get(j)) | row[2].getContents().contains(find.get(j))) {
                            resultArray.add(row[1].getContents());

                        }
                    }

                } else {
                    resultArray.add(row[1].getContents());

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
//            list_excel.setAdapter(arrayAdapter);
            workbook.close();
        }
        resultArray.add(0, "송파노인종합복지관");
        for(int i=0; i<resultArray.size(); i++){
            arrayAdapter.add((String) resultArray.get(i));
        }
        return resultArray;
    }// Excel

}