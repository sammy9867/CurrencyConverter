package com.samuel.currencyconverter.util;

import com.samuel.currencyconverter.R;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String BASE_URL = "https://api.exchangeratesapi.io";

    public static final int CONNECTION_TIMEOUT = 10; // 10 seconds
    public static final int READ_TIMEOUT = 2; // 2 seconds
    public static final int WRITE_TIMEOUT = 2; // 2 seconds

    public static final List<Integer> CURRENCY_FLAGS = Arrays.asList(
    R.drawable.eur, R.drawable.cad, R.drawable.hkd, R.drawable.isk, R.drawable.php,
            R.drawable.dkk,R.drawable.huf, R.drawable.czk, R.drawable.aud, R.drawable.ron, R.drawable.sek,
            R.drawable.idr,R.drawable.inr,R.drawable.brl, R.drawable.rub, R.drawable.hrk, R.drawable.jpy,
            R.drawable.thb, R.drawable.chf, R.drawable.sgd, R.drawable.pln, R.drawable.bgn, R.drawable.trya,
            R.drawable.cny,  R.drawable.nok,  R.drawable.nzd, R.drawable.zar,  R.drawable.usd,  R.drawable.mxn,
            R.drawable.ils,  R.drawable.gbp,  R.drawable.krw,  R.drawable.myr);
}
