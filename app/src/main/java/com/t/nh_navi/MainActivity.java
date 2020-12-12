package com.t.nh_navi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNV;
    private FragmentHome fragmentHome;
    private FragmentDonate_1 fragmentDonate1;
    private FragmentPay_1 fragmentPay1;
    private FragmentCommunity_1 fragmentCommunity1;
    private FragmentMypage_1 fragmentMypage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mBottomNV = findViewById(R.id.bottomNavigationView);

        fragmentHome = new FragmentHome();
        fragmentDonate1 = new FragmentDonate_1();
        fragmentPay1 = new FragmentPay_1();
        fragmentCommunity1 = new FragmentCommunity_1();
        fragmentMypage1 = new FragmentMypage_1();


        //제일 처음 띄워줄 view
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragmentHome).commitAllowingStateLoss();

        //아이콘 선택했을 때 fragment 띄워지도록
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                onStop();
                switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_layout, fragmentHome).commitAllowingStateLoss();
                            return true;
                        case R.id.bottom_donate:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_layout, fragmentDonate1).commitAllowingStateLoss();
                            return true;
                        case R.id.bottom_pay:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_layout, fragmentPay1).commitAllowingStateLoss();
                            return true;
                        case R.id.bottom_community:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_layout, fragmentCommunity1).commitAllowingStateLoss();
                            return true;
                        case R.id.bottom_mypage:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_layout, fragmentMypage1).commitAllowingStateLoss();
                            return true;
                    }
                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment).commitAllowingStateLoss();  //Fragment로 사용할 MainActivity 내의 layout 공간 선택ㄱ
    }

}