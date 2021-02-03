package com.example.thirdlibstudy.robust;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdlibstudy.R;

public class RobustMain extends AppCompatActivity {


    Button btnBug;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_robust_main);


        btnBug = findViewById(R.id.bnt_bug_click);


        btnBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RobustMain.this,"这里有 Bug",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
