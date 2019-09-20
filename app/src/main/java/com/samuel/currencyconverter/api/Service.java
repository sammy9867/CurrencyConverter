package com.samuel.currencyconverter.api;

import com.samuel.currencyconverter.util.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samuel.currencyconverter.util.Constants.CONNECTION_TIMEOUT;
import static com.samuel.currencyconverter.util.Constants.READ_TIMEOUT;
import static com.samuel.currencyconverter.util.Constants.WRITE_TIMEOUT;


public class Service {

    private static OkHttpClient client = new OkHttpClient.Builder()

            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(new LoggingInterceptor())
            .build();


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ExchangeRatesAPI exchangeRatesAPI = retrofit.create(ExchangeRatesAPI.class);

    public static ExchangeRatesAPI getExchangeRateApo(){
        return exchangeRatesAPI;
    }
}

