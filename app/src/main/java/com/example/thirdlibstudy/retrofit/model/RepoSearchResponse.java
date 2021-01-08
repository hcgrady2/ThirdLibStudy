package com.example.thirdlibstudy.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RepoSearchResponse {

    @SerializedName("total_count")
    public int total_count = 0;

    @SerializedName("items")
    public List<Repo> items = new ArrayList<>();

    @SerializedName("incomplete_results")
    public boolean incomplete_results = false;
}
