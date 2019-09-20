package com.samuel.currencyconverter.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.samuel.currencyconverter.api.ExchangeRatesAPI;
import com.samuel.currencyconverter.api.ExchangeRatesResponse;
import com.samuel.currencyconverter.api.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestRateRepository {

    private static LatestRateRepository instance;
    private ExchangeRatesAPI exchangeRatesAPI;

    public static LatestRateRepository getInstance() {
        if (instance == null) {
            instance = new LatestRateRepository();
        }
        return instance;
    }

    public LatestRateRepository(){
        exchangeRatesAPI = Service.getExchangeRateApo();
    }

    public LiveData<ExchangeRatesResponse> getLatestRates(String base) {

        final MutableLiveData<ExchangeRatesResponse> liveData = new MutableLiveData<>();

        exchangeRatesAPI.getLatest(base).enqueue(new Callback<ExchangeRatesResponse>() {

            @Override
            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                if (response.isSuccessful()){
                    Log.i("getLatestRates Repo ", "Success!");
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
                Log.i("getLatestRates Repo ", "Failure!");
                liveData.setValue(null);
            }
        });

        return liveData;
    }

}
