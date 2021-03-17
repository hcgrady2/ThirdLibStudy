package com.example.myretrofit.use;

import java.util.ArrayList;
import java.util.List;

public class RepoSearchResponse {

    public int total_count = 0;

    public List<Repo> items = new ArrayList<>();

    public boolean incomplete_results = false;
}
