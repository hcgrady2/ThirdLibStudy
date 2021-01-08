package com.example.thirdlibstudy.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdlibstudy.R;
import com.example.thirdlibstudy.retrofit.api.GitHubClient;
import com.example.thirdlibstudy.retrofit.model.Repo;
import com.example.thirdlibstudy.retrofit.model.RepoSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.thirdlibstudy.retrofit.ConstantConfig.IN_QUALIFIER;
import static com.example.thirdlibstudy.retrofit.ConstantConfig.NETWORK_PAGE_SIZE;

public class RetrofitMainActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitMainActivity";
    String query = "Android";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retrofi_main);
    }

    public void netRequest(View view) {


        Log.i(TAG, "netRequest: ");


        String apiQuery = query + IN_QUALIFIER;

        GitHubClient.getInstance()
                .getApi()
                .searchRepos(apiQuery, 0, 10)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                        Log.i(TAG, "onResponse: ");
                        if (response.isSuccessful()) {
                            List<Repo> repos = response.body().items;
                            Log.i(TAG, "onResponse:  surccess :" + repos.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<RepoSearchResponse> call, Throwable t) {


                        Log.i(TAG, "onFailure: ");
                    }
                });




    }
}
