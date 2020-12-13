package com.t.nh_navi;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPay_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPay_2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewGroup viewGroup;
    ImageView qrCreate;
    int count;
    TextView time;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPay_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPay_2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPay_2 newInstance(String param1, String param2) {
        FragmentPay_2 fragment = new FragmentPay_2();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pay_2, container, false);
        qrCreate = (ImageView) viewGroup.findViewById(R.id.image_qr_create);
        time = (TextView)viewGroup.findViewById(R.id.time);

        Bundle bundle = getArguments();
        Bitmap bmp = (Bitmap) bundle.getParcelable("qr");
        qrCreate.setImageBitmap(bmp);

        //qr코드 시간 가게 하기
        timer();

        return viewGroup;
    }

    void timer(){
        count = 10;
        time.setText(String.format("%02d", 00)+"분"+String.format("%02d", count));
        Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask() {
            @Override
            public void run() {
                if(count>0){
                    count--;
                    time.setText(String.format("%02d", 00)+"분"+String.format("%02d", count));
                }

                else{
                    m_timer.cancel();
                    //Fragment 연결
                    Fragment fragment = new FragmentPay_3(); // Fragment 생성 B
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commitAllowingStateLoss();
                }
            }

        };
        m_timer.schedule(m_task, 1000, 1000);
    }//timer
}//fragment