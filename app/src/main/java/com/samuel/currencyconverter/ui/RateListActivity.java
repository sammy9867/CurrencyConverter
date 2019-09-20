package com.samuel.currencyconverter.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.samuel.currencyconverter.R;
import com.samuel.currencyconverter.adapter.OnRateListener;
import com.samuel.currencyconverter.adapter.RateRecyclerAdapter;
import com.samuel.currencyconverter.api.ExchangeRatesResponse;
import com.samuel.currencyconverter.util.Constants;
import com.samuel.currencyconverter.viewmodel.LatestRateViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RateListActivity extends  AppCompatActivity implements OnRateListener {

    private RecyclerView mRecyclerView;
    private RateRecyclerAdapter rateRecylerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LatestRateViewModel latestRateViewModel;
    private ImageButton mBackArrow;

    List<String> currencySymbolList = new ArrayList<>();
    List<String> currencyNameList = new ArrayList<>();
    List<String> currencyRateList = new ArrayList<>();
    List<String> currencyValueList = new ArrayList<>();
    List<Integer> currencyImageList = new ArrayList<>();

    String baseCurrency = "";
    int baseImage = 0;
    String userInputAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converted);
        mRecyclerView = findViewById(R.id.currency_list);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mBackArrow = findViewById(R.id.back_arrow_list);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            baseCurrency = extras.getString("currencyBase");
            userInputAmount = extras.getString("userInputAmount");
        }

        //Since you cannot save a drawable as "try" as it's a java keyword, I had to rename it.
        for(Integer integer : Constants.CURRENCY_FLAGS){
            String base =  baseCurrency.toLowerCase();
            if(base.equals("try")) base = "trya";
            if(getResources().getIdentifier(base, "drawable", getPackageName()) == integer){
                baseImage = integer;
            }
        }

        latestRateViewModel = ViewModelProviders.of(this).get(LatestRateViewModel.class);
        latestRateViewModel.init();
        init();
    }

    public void init() {

        latestRateViewModel.getLatestRates(baseCurrency).observe(this, new Observer<ExchangeRatesResponse>() {
            @Override
            public void onChanged(@Nullable ExchangeRatesResponse exchangeRatesResponse) {

                if (exchangeRatesResponse != null) {
                    if (exchangeRatesResponse.getRates() != null) {
                        HashMap<String, String> ratesHashMap = exchangeRatesResponse.getRates();
                        populateRecyclerView(ratesHashMap);
                    }
                }
            }
        });
    }

    public void populateRecyclerView(HashMap<String, String> ratesHashMap){

        HashMap<String, String> symbol_and_name = new HashMap<>();
        String[] currency_symbol_array = getApplicationContext().getResources().getStringArray(R.array.currency_symbol_array);
        String[] currency_name_array = getApplicationContext().getResources().getStringArray(R.array.currency_name_array);

        for(int i = 0; i < currency_symbol_array.length; i++){
            symbol_and_name.put(currency_symbol_array[i], currency_name_array[i]);
        }

        TreeMap<String, String> sortedRatesMap = new TreeMap<>(ratesHashMap);
        for (Map.Entry<String, String> entry : sortedRatesMap.entrySet()){
            //The recyclerView shouldn't contain the base currency
            if(!entry.getKey().equals(baseCurrency)) {
                currencySymbolList.add(entry.getKey());
                currencyValueList.add(entry.getValue());

                if(symbol_and_name.containsKey(entry.getKey())){
                    currencyNameList.add(symbol_and_name.get(entry.getKey()));
                    String draw =  entry.getKey().toLowerCase();
                    if(draw.equals("try")) draw = "trya";
                    for(Integer integer: Constants.CURRENCY_FLAGS){
                        if(getResources().getIdentifier(draw, "drawable", getPackageName()) == integer){
                            currencyImageList.add(integer);
                        }
                    }

                }
            }

            double tempCurrencyValue = Double.parseDouble(entry.getValue());
            double tempUserInputAmount = Double.parseDouble(userInputAmount);

            double convertedValue = tempCurrencyValue * tempUserInputAmount;
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            currencyRateList.add(formatter.format(convertedValue));
        }

        rateRecylerAdapter = new RateRecyclerAdapter(this, this, baseCurrency, currencySymbolList, currencyNameList,
                currencyRateList, currencyValueList, currencyImageList);
        mRecyclerView.setAdapter(rateRecylerAdapter);
    }

    @Override
    public void onRateClick(int position) {

        currencySymbolList.get(position);

        Intent intent = new Intent(this, CurrencyHistoryActivity.class);
        intent.putExtra("currencyBase", baseCurrency);
        intent.putExtra("currencySymbol", currencySymbolList.get(position));
        intent.putExtra("baseImage", baseImage);
        intent.putExtra("convertedImage", currencyImageList.get(position));

        startActivity(intent);
    }
}