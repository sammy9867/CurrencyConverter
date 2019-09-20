package com.samuel.currencyconverter.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.samuel.currencyconverter.api.ExchangeRatesAPI;
import com.samuel.currencyconverter.api.ExchangeRatesHistoryResponse;
import com.samuel.currencyconverter.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateHistoryRepository {

    private static RateHistoryRepository instance;
    private ExchangeRatesAPI exchangeRatesAPI;

    public static RateHistoryRepository getInstance() {
        if (instance == null) {
            instance = new RateHistoryRepository();
        }
        return instance;
    }

    public RateHistoryRepository(){
        exchangeRatesAPI = Service.getExchangeRateApo();
    }

    public LiveData<ExchangeRatesHistoryResponse> getHistory(String base, String symbols, String start_at, String end_at){

        final MutableLiveData<ExchangeRatesHistoryResponse> liveData = new MutableLiveData<>();

        exchangeRatesAPI.getHistory(base, symbols, start_at, end_at).enqueue(new Callback<ExchangeRatesHistoryResponse>() {
            @Override
            public void onResponse(Call<ExchangeRatesHistoryResponse> call, Response<ExchangeRatesHistoryResponse> response) {
                if (response.isSuccessful()){
                    Log.i("getHistoryRates Repo ", "Success!");
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExchangeRatesHistoryResponse> call, Throwable t) {
                Log.i("getHistoryRates Repo ", "Failure!");
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
