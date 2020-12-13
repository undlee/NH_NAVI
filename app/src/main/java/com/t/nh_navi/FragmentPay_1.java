package com.t.nh_navi;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.t.nh_navi.uses.DrawingTransfer;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPay_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPay_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewGroup viewGroup;
    TextView input_money;
    ImageView qrCreate;
    Bitmap bitmap;
    Button btn_pay2, btn_send2;
    String transAmount, Drawing, point;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPay_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPay.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPay_1 newInstance(String param1, String param2) {
        FragmentPay_1 fragment = new FragmentPay_1();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pay_1, container, false);
        input_money = (TextView) viewGroup.findViewById(R.id.input_money);
        qrCreate = (ImageView) viewGroup.findViewById(R.id.image_qr_create);
        btn_pay2 = (Button) viewGroup.findViewById(R.id.btn_pay2);
        btn_send2 = (Button) viewGroup.findViewById(R.id.btn_send2);

        CreateQR();


        //qr코드 확대
        btn_pay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전달할 데이터 Bundle
                Bundle bundle = new Bundle();
                bundle.putParcelable("qr", bitmap);
                //Fragment 연결
                Fragment fragment = new FragmentPay_2(); // Fragment 생성 B
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commitAllowingStateLoss();
            }
        });

        //결제하기
        btn_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "나비날다", Toast.LENGTH_SHORT).show();
                Draw();
            }
        });

        return viewGroup;
    }

    void Draw(){
//        String DractOtlt = "나비날다 기부";
        //pref 받아옴
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);

        String day = sharedPreferences.getString("day", null);
//        Log.d("test", day);
        String time = sharedPreferences.getString("time", null);
        String Iscd = sharedPreferences.getString("Iscd", null);
        String FintechApsno = sharedPreferences.getString("FintechApsno", null);
        String Istuno = sharedPreferences.getString("Istuno", null);
        String AccessToken = sharedPreferences.getString("AccessToken", null);
        String FinAcno = sharedPreferences.getString("finAcno", null);
        transAmount = input_money.getText().toString();
        String DractOtlt = "나비날다";
//        Log.d("test", day+","+time+","+","+Iscd+","+FintechApsno+","+Istuno+","+AccessToken+","+FinAcno);
        try {
            Drawing = new DrawingTransfer(day, time, Iscd, FintechApsno, Istuno + 3, AccessToken, FinAcno, transAmount, DractOtlt, "DrawingTransfer").getRgsnYmd();
            Log.d("task4", Drawing);
            point = String.valueOf((int) (Integer.parseInt(transAmount)*0.01));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!Drawing.equals("0")){
            SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
            //key, value 이름 바꾸기
            String donateResult = sharedPreferences.getString("donateResult", "124140");
            editor.putString("donateResult", String.valueOf(Integer.parseInt(donateResult)+Integer.parseInt(point))); // key,value 형식으로 저장
            editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

            Toast.makeText(getActivity(), "나비날다에서 " + transAmount + "원이 이체되었습니다. \n 지급 포인트는 " +point+"원 입니다." , Toast.LENGTH_LONG).show();

        }
    }

    void CreateQR(){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);
        String finAcno = sharedPreferences.getString("finAcno", null);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(finAcno, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCreate.setImageBitmap(bitmap);
        } catch (Exception e) {
        }
    }
}