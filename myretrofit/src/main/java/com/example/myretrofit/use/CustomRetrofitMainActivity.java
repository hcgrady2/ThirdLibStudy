package com.example.myretrofit.use;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myretrofit.R;
import com.example.myretrofit.core.Call;
import com.example.myretrofit.core.Callback;

public class CustomRetrofitMainActivity extends AppCompatActivity {

    String query = "Android";
    public   String IN_QUALIFIER = "in:name,description";

    String apiQuery = query + IN_QUALIFIER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main_use);



    }

    public void netRequest(View view) {

        RetrofitClient.getServiceApi()
                .searchRepos(apiQuery, 0, 10)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, Response<Response> response) {


                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.i("hcTag", "onResponse: ");


                    }
                });

    }
}
