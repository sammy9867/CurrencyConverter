package com.samuel.currencyconverter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.samuel.currencyconverter.R;

import java.util.List;


public class RateRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private OnRateListener onRateListener;

    private String baseCurrency;
    private List<String> currencySymbolList, currencyNameList, currencyRateList, currencyValueList;
    private List<Integer> currencyImageList;

    public RateRecyclerAdapter(Context context, OnRateListener onRateListener, String baseCurrency, List<String> currencySymbolList, List<String> currencyNameList,
            List<String> currencyRateList, List<String> currencyValueList, List<Integer> currencyImageList){

        this.context = context;
        this.onRateListener = onRateListener;
        this.baseCurrency = baseCurrency;
        this.currencySymbolList = currencySymbolList;
        this.currencyNameList = currencyNameList;
        this.currencyRateList = currencyRateList;
        this.currencyValueList = currencyValueList;
        this.currencyImageList = currencyImageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_currency_list_item, viewGroup, false);
        return new RateViewHolder(view, onRateListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Glide.with(context)
                .load(currencyImageList.get(i))
                .apply(RequestOptions.circleCropTransform())
                .into( ((RateViewHolder)viewHolder).currencyImage);

        ((RateViewHolder)viewHolder).currencySymbol.setText(currencySymbolList.get(i));
        ((RateViewHolder)viewHolder).currencyName.setText(currencyNameList.get(i));
        ((RateViewHolder)viewHolder).currencyRate.setText(currencyRateList.get(i));
        ((RateViewHolder)viewHolder).currencyValue.
                setText( context.getResources().getString(R.string.currency_rate, baseCurrency, currencyValueList.get(i),currencySymbolList.get(i)));
    }

    @Override
    public int getItemCount() {
      return currencySymbolList.size();
    }
}
