package com.samuel.currencyconverter.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRatesAPI {

    @GET("latest")
    Call<ExchangeRatesResponse> getLatest(
            @Query("base") String base
    );

    @GET("history")
    Call<ExchangeRatesHistoryResponse> getHistory(
            @Query("base") String base,
            @Query("symbols") String symbols,
            @Query("start_at") String start_at,
            @Query("end_at") String end_at
    );

}
