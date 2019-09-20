package com.samuel.currencyconverter.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.samuel.currencyconverter.R;
import com.samuel.currencyconverter.api.ExchangeRatesHistoryResponse;
import com.samuel.currencyconverter.viewmodel.RateHistoryViewModel;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrencyHistoryActivity extends AppCompatActivity {

    private RateHistoryViewModel rateHistoryViewModel;
    private ImageButton close_button;
    private RelativeLayout relativeLayout;

    private TextView textView1, textView2;
    private CircleImageView circleImageView1;
    private CircleImageView circleImageView2;

    List<String> date = new ArrayList<>();
    List<String> currencyValueList = new ArrayList<>();

    String baseCurrency = "";
    String symCurrency ="";
    Drawable b1, b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        relativeLayout = findViewById(R.id.layout_history);

        close_button = findViewById(R.id.close_history);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        textView1 = findViewById(R.id.currency_symbol_one);
        textView2 = findViewById(R.id.currency_symbol_two);
        circleImageView1 = findViewById(R.id.currency_flag_one);
        circleImageView2 = findViewById(R.id.currency_flag_two);

        rateHistoryViewModel = ViewModelProviders.of(this).get(RateHistoryViewModel.class);
        rateHistoryViewModel.init();

        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            baseCurrency = extras.getString("currencyBase");
            symCurrency = extras.getString("currencySymbol");
            b1 = getResources().getDrawable(getIntent().getIntExtra("baseImage",-1));
            b2 = getResources().getDrawable(getIntent().getIntExtra("convertedImage",-1));

            Log.i("Base: "+baseCurrency, "Sym: " +symCurrency);
        }


        textView1.setText(baseCurrency);
        textView2.setText(symCurrency);

        Glide.with(getApplicationContext())
                .load(b1)
                .apply(RequestOptions.circleCropTransform())
                .into(circleImageView1);

        Glide.with(getApplicationContext())
                .load(b2)
                .apply(RequestOptions.circleCropTransform())
                .into(circleImageView2);


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        rateHistoryViewModel.getLatestRates(baseCurrency, symCurrency,"1999-01-04",  dateFormat.format(currentDate)).observe(this, new Observer<ExchangeRatesHistoryResponse>() {
            @Override
            public void onChanged(@Nullable ExchangeRatesHistoryResponse exchangeRatesHistoryResponse) {

                if (exchangeRatesHistoryResponse!= null){

                    HashMap<String, HashMap<String, String>> hashMap = exchangeRatesHistoryResponse.getRates();
                    Map<String, HashMap<String, String>> map = new TreeMap<>(hashMap);

                    for (Map.Entry<String, HashMap<String, String>> entry : map.entrySet()) {

                        date.add(entry.getKey());
                        HashMap<String, String> h = entry.getValue();
                        currencyValueList.add(h.get(h.keySet().toArray()[0].toString()));
                    }

                    populateLineChart();
                }
            }
        });




    }

    private void populateLineChart(){

        LineChart chart = (LineChart) findViewById(R.id.lineChart);
        List<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < currencyValueList.size(); i++){
            float y = Float.parseFloat(currencyValueList.get(i));
            entries.add(new Entry(i, y));
        }

        LineDataSet dataset = new LineDataSet(entries, null);

        //cubic lines
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //To Fill area below line, disable displayed values:
        dataset.setDrawFilled(true);
        dataset.setDrawValues(false);

        //Set fill color and line color:
        if(Float.parseFloat(currencyValueList.get(0)) > Float.parseFloat(currencyValueList.get(currencyValueList.size() - 1))){
            dataset.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.light_red));
            dataset.setColor(ContextCompat.getColor(getApplicationContext(), R.color.cinnabar));
        }
        else{
            dataset.setFillColor(ContextCompat.getColor(getApplicationContext(),R.color.light_green));
            dataset.setColor(ContextCompat.getColor(getApplicationContext(),R.color.shamrock_green));
        }

        //Disable transparency (values range 0-255) and disable drawing circles on the main chart line:
        dataset.setFillAlpha(255);
        dataset.setDrawCircles(false);

        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(date));
        LineData lineData = new LineData(dataset);

        //To disable legend and hide description:
        chart.getDescription().setText("");
        chart.getLegend().setEnabled(false);

        //To remove horizontal grid lines:
        chart.getXAxis().setDrawGridLines(false);

        //To disable right axis:
        chart.getAxisRight().setEnabled(false);

        chart.setData(lineData);
        chart.invalidate();
    }

}
