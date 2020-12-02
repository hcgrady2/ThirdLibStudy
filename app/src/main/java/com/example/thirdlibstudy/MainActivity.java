package com.example.thirdlibstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.butterknife_annotations.TestAnotation;
import com.example.thirdlibstudy.butterknife.ButterknifeMainActivity;


@TestAnotation
public class MainActivity extends AppCompatActivity {

    Button btnButterKnife;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnButterKnife = findViewById(R.id.btn_butter_knife);
        btnButterKnife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ButterknifeMainActivity.class));
            }
        });
    }

}
