package com.t.nh_navi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.t.nh_navi.uses.CheckOpenFinAccountDirect;
import com.t.nh_navi.uses.InquireBalance;
import com.t.nh_navi.uses.InquireDepositorAccountNumber;
import com.t.nh_navi.uses.PreferenceManager;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FragmentHome#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentHome extends Fragment {
        //NH API
        String day, time, Iscd, FintechApsno, Istuno, AccessToken, Rgno, BrdtBrno, Bncd, Acno;
        //원하는 값
        String FinAcno, AccountName, Balance, Drawing;

        int DonationAmount = 1000;

        private ScrollView mScrollView;
        ViewGroup viewGroup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        TextView title = (TextView) viewGroup.findViewById(R.id.user_title);
        TextView account_number1 = (TextView) viewGroup.findViewById(R.id.account_number1);
        TextView account_amount1 = (TextView) viewGroup.findViewById(R.id.account_amount1);
        TextView user_name2 = (TextView) viewGroup.findViewById(R.id.user_name2);

//        try {
//            NhApi();
//            title.setText(AccountName + "님의 통장");
//            account_number1.setText(Acno.substring(0, 3) + "-" + Acno.substring(3, 7) + "-" + Acno.substring(7, 11) + "-" + Acno.substring(11, 13));
//            account_amount1.setText(String.format("%,d", Integer.parseInt(Balance)) + "원");
//            user_name2.setText(AccountName);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return viewGroup;
    }
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        private void NhApi() throws InterruptedException, ExecutionException, JSONException, IOException {
            Date date = new Date();
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat hmsFormat = new SimpleDateFormat("HHmmss");
            day = ymdFormat.format(date);
            time = hmsFormat.format(date);
            Iscd = "000540";
            FintechApsno = "001";
            Istuno = "0000100001" + (int) (Math.random() * 1000000000);
            AccessToken = "9c772be64bd6bf8aef012e491fff94551607be583336bcd48e5339e65f30905d";
            Rgno = "20201206000000650";
            BrdtBrno = "19501201";
            Bncd = "011";
            Acno = "3020000002247";

//            //핀어카운트 넘버
//            FinAcno = new CheckOpenFinAccountDirect(day, time, Iscd, FintechApsno, Istuno + 0, AccessToken, Rgno, BrdtBrno).getFinAcno();
//            new PreferenceManager(this).put("finAcno", FinAcno);
//            Log.d("task", FinAcno);
//
//            //예금주조회
//            AccountName = new InquireDepositorAccountNumber(day, time, Iscd, FintechApsno, Istuno + 1, AccessToken, Bncd, Acno, "InquireDepositorAccountNumber").getName();
//            Log.d("task2", AccountName);
//
//            //잔액조회
//            Balance = new InquireBalance(day, time, Iscd, FintechApsno, Istuno + 2, AccessToken, FinAcno, "InquireBalance").getBalance();
//            Log.d("task3", Balance);
//
//        }
    }}