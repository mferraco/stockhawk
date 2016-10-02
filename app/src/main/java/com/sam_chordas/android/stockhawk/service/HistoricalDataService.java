package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.util.Log;

import com.sam_chordas.android.stockhawk.callbacks.StockHistoryCallback;
import com.sam_chordas.android.stockhawk.data.HistoricalStockData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * A class designated to retrieve historical stock data and give it back to the caller
 */
public class HistoricalDataService implements Callback {

    private StockHistoryCallback mCallback;

    private OkHttpClient client = new OkHttpClient();

    private Context mContext;

    private static HistoricalDataService mService;

    private static final String BASE_URL = "http://chartapi.finance.yahoo.com/instrument/1.0/";
    private static final String TRAILING_URL = "/chartdata;type=quote;range=1y/json";

    private HistoricalDataService(Context context, StockHistoryCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public static HistoricalDataService getInstance(Context context, StockHistoryCallback callback) {
        if (mService == null) {
            mService = new HistoricalDataService(context, callback);
        }

        return mService;
    }

    public void getHistoricalStockData(String symbol) {
        String url = BASE_URL + symbol + TRAILING_URL;

        Request request = new Request.Builder()
                .url(url)
                .build();

        // queue up the request to run on another thread
        client.newCall(request).enqueue(this);
    }

    private List<HistoricalStockData> getHistoricalDataArray(JSONArray historicalJsonData) {
        // format each json object in hte array into a HistoricalStockData object and return the list
        return null;
    }


    // ===== OKHttp Callback Interface =====

    @Override
    public void onFailure(Request request, IOException e) {
        // handle failures
    }

    @Override
    public void onResponse(Response response) throws IOException {
        Log.d("HistoricalDataService", response.body().string());
    }
}
