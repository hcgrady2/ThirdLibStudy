package com.example.thirdlibstudy.butterknife;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.butterknife_annotations.BindView;
import com.example.thirdlibstudy.R;
import com.hc.butterknife.ButterKnife;
import com.hc.butterknife.Unbinder;

public class ButterknifeMainActivity extends AppCompatActivity {
    @BindView(R.id.tv_apt_one)
    TextView one;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife_main);
        unbinder = ButterKnife.bind(this);
        one.setText("apt SUCCESS!a ");
        //

    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }



}
