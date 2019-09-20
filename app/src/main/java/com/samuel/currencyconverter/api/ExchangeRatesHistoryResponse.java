package com.samuel.currencyconverter.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ExchangeRatesHistoryResponse {

    @SerializedName("rates")
    @Expose()
    private HashMap<String, HashMap<String, String>> rates;

    @SerializedName("error")
    @Expose()
    private String error;

    public String getError() {
        return error;
    }

    public HashMap<String, HashMap<String, String>> getRates(){return rates;}
}
