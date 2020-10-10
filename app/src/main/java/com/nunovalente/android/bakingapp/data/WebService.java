package com.nunovalente.android.bakingapp.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<String> getRecipes();
}
