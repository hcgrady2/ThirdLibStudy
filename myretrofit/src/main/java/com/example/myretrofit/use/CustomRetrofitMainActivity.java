package com.example.myretrofit.use;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myretrofit.R;
import com.example.myretrofit.core.Call;
import com.example.myretrofit.core.Callback;

public class CustomRetrofitMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main_use);



    }

    public void netRequest(View view) {

        RetrofitClient.getServiceApi().userLogin("Darren", "940223")
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        Log.e("hcTag",response.body.toString());
                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {

                    }
                });

    }
}
