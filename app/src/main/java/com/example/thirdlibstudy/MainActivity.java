package com.example.thirdlibstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.butterknife_annotations.TestAnotation;
import com.example.thirdlibstudy.butterknife.ButterknifeMainActivity;
import com.example.thirdlibstudy.eventbus.EventBusOneActivity;
import com.example.thirdlibstudy.glide.GlideMainActivity;


public class MainActivity extends AppCompatActivity {

    Button btnButterKnife;
    Button btnEventBus;
    Button btnGlide;
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


        btnEventBus = findViewById(R.id.btn_event_bus);
        btnEventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventBusOneActivity.class));
            }
        });


        btnGlide = findViewById(R.id.btn_glide_main);
        btnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlideMainActivity.class));

            }
        });
    }

}
