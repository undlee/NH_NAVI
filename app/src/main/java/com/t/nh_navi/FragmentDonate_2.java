package com.t.nh_navi;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDonate_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDonate_2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewGroup viewGroup;
    Button[] donate_choice1;
    int[] id_donate_choice1, selectInstitute;
    SharedPreferences sharedPreferences;
    String st;

    public FragmentDonate_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDonate_2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDonate_2 newInstance(String param1, String param2) {
        FragmentDonate_2 fragment = new FragmentDonate_2();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_donate_2, container, false);
        Button btn_recommend = viewGroup.findViewById(R.id.btn_recommend);


        //눌린 상태 유지
        id_donate_choice1 = new int[]{R.id.field1, R.id.field2, R.id.field3, R.id.field4, R.id.field5, R.id.field6};
//        for(int i=0; i<id_donate_choice1.length; i++){
//            Log.d("donate_2", String.valueOf(id_donate_choice1[i]));
//        }
        donate_choice1 = new Button[id_donate_choice1.length];

        //버튼
        selectInstitute = new int[6];
        pressed();

        btn_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st = Arrays.toString(selectInstitute);
                //pref 받아옴
                sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("selectInstitute", st); // key,value 형식으로 저장
                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                //Fragment 연결
                Fragment fragment = new FragmentDonate_1(); // Fragment 생성 B
                getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commitAllowingStateLoss();
            }
        });




        return viewGroup;
    }

    private void pressed(){
        for(int i = 0; i<id_donate_choice1.length; i++){
            donate_choice1[i]=(Button)viewGroup.findViewById(id_donate_choice1[i]);
            int para = i;
            donate_choice1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectInstitute[para]=1;
                    donate_choice1[para].setSelected(true);
                    donate_choice1[para].setTextColor(Color.WHITE);
                }
            });
        }
    }
}