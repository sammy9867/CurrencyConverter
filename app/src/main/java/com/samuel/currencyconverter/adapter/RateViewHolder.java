package com.samuel.currencyconverter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.samuel.currencyconverter.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    OnRateListener onRateListener;
    TextView currencySymbol, currencyName, currencyRate, currencyValue;
    CircleImageView currencyImage;

    public RateViewHolder(@NonNull View itemView, OnRateListener onRateListener) {

        super(itemView);

        this.onRateListener = onRateListener;
        currencyImage = itemView.findViewById(R.id.currency_image);
        currencySymbol = itemView.findViewById(R.id.currency_symbol);
        currencyName = itemView.findViewById(R.id.currency_name);
        currencyRate = itemView.findViewById(R.id.currency_converted);
        currencyValue = itemView.findViewById(R.id.currency_value);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onRateListener.onRateClick(getAdapterPosition());
    }
}