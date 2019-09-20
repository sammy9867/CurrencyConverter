package com.samuel.currencyconverter.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.samuel.currencyconverter.api.ExchangeRatesResponse;
import com.samuel.currencyconverter.repository.LatestRateRepository;

public class LatestRateViewModel extends ViewModel {

    private LatestRateRepository rateRepository;
    private LiveData<ExchangeRatesResponse> mutableLiveData;

    public void init(){
        rateRepository = LatestRateRepository.getInstance();
    }

    public LiveData<ExchangeRatesResponse> getLatestRates(String base){
        mutableLiveData = rateRepository.getLatestRates(base);
        return mutableLiveData;
    }

}
