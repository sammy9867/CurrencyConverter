package com.samuel.currencyconverter.api;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ExchangeRatesResponse {


    @SerializedName("rates")
    @Expose()
    private HashMap<String, String> rates;

    @SerializedName("base")
    @Expose()
    private String base;

    @SerializedName("date")
    @Expose()
    private String date;

    @SerializedName("error")
    @Expose()
    private String error;

    public String getError() {
        return error;
    }

    @Nullable
    public String getBase(){
        return base;
    }

    @Nullable
    public String getDate(){
        return date;
    }

    @Nullable
    public HashMap<String, String> getRates(){
        return rates;
    }
}

