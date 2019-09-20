package com.samuel.currencyconverter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.samuel.currencyconverter.R;
import com.samuel.currencyconverter.adapter.CustomSpinnerAdapter;
import com.shashank.sony.fancytoastlib.FancyToast;


import static com.samuel.currencyconverter.util.Constants.CURRENCY_FLAGS;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    Spinner spinner;
    CustomSpinnerAdapter customSpinnerAdapter;
    String[] currency_symbols;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);

        currency_symbols = getApplicationContext().getResources().getStringArray(R.array.currency_symbol_array);
        customSpinnerAdapter = new CustomSpinnerAdapter(this, currency_symbols, CURRENCY_FLAGS);

        spinner.setAdapter(customSpinnerAdapter);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserInput();
            }
        });
    }

    void checkUserInput(){
        //Regular Expression to accept only numbers with an optional decimal
        String regex = "^[0-9]\\d*(\\.\\d+)?$";

        if(!editText.getText().toString().matches(regex)){
            FancyToast.makeText(this, "Invalid Input", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }else{
            Intent intent = new Intent(getBaseContext(), RateListActivity.class);
            intent.putExtra("currencyBase", spinner.getSelectedItem().toString());
            intent.putExtra("userInputAmount", editText.getText().toString());
            startActivity(intent);
        }
    }

}