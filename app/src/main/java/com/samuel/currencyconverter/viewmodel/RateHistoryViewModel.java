package com.samuel.currencyconverter.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.samuel.currencyconverter.api.ExchangeRatesHistoryResponse;
import com.samuel.currencyconverter.repository.RateHistoryRepository;

public class RateHistoryViewModel extends ViewModel {

    private RateHistoryRepository rateHistoryRepository;
    private LiveData<ExchangeRatesHistoryResponse> mutableLiveData;

    public void init(){
        rateHistoryRepository = RateHistoryRepository.getInstance();
    }

    public LiveData<ExchangeRatesHistoryResponse> getLatestRates(String base, String symbol, String start_at, String end_at){
        mutableLiveData = rateHistoryRepository.getHistory(base, symbol, start_at, end_at);
        return mutableLiveData;
    }
}
