package com.samuel.currencyconverter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.samuel.currencyconverter.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] currency_symbols;
    private List<Integer> currency_flags;

    public CustomSpinnerAdapter(Context context, String[] currency_symbols,
                          List<Integer> currency_flags) {

            super(context, R.layout.layout_spinner_items, currency_symbols);

            this.context = context;
            this.currency_symbols = currency_symbols;
            this.currency_flags = currency_flags;
        }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row  = inflater.inflate(R.layout.layout_spinner_items, null);
        TextView textView = (TextView)row.findViewById(R.id.spinner_symbol);
        CircleImageView  imageView = (CircleImageView)row.findViewById(R.id.spinner_flag);

        textView.setText(currency_symbols[position]);

        Glide.with(context)
                .load(currency_flags.get(position))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

        return row;
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row  = inflater.inflate(R.layout.layout_spinner_items, null);
        TextView textView = (TextView)row.findViewById(R.id.spinner_symbol);
        ImageView imageView = (ImageView)row.findViewById(R.id.spinner_flag);

        textView.setText(currency_symbols[position]);

        Glide.with(context)
                .load(currency_flags.get(position))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

        return row;
    }



}
