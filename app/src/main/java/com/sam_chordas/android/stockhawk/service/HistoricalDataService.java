package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.util.Log;

import com.sam_chordas.android.stockhawk.callbacks.StockHistoryCallback;
import com.sam_chordas.android.stockhawk.data.HistoricalStockData;
import com.sam_chordas.android.stockhawk.data.HistoricalStockDataRanges;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class designated to retrieve historical stock data and give it back to the caller
 */
public class HistoricalDataService implements Callback {

    private static final String TAG = HistoricalDataService.class.getSimpleName();

    private StockHistoryCallback mCallback;

    private OkHttpClient client = new OkHttpClient();

    private Context mContext;

    private static final String BASE_URL = "http://chartapi.finance.yahoo.com/instrument/1.0/";
    private static final String TRAILING_URL = "/chartdata;type=quote;range=1y/json";

    public HistoricalDataService (Context context, StockHistoryCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void getHistoricalStockData(String symbol) {
        String url = BASE_URL + symbol + TRAILING_URL;

        Request request = new Request.Builder()
                .url(url)
                .build();

        // queue up the request to run on another thread
        Call call = client.newCall(request);
        call.enqueue(this);
    }

    private List<HistoricalStockData> getHistoricalDataArray(JSONArray historicalJsonData) {
        List<HistoricalStockData> stockDataList = new ArrayList<>();
        for (int i = 0; i < historicalJsonData.length(); i++) {
            try {
                JSONObject dataInstanceJson = historicalJsonData.getJSONObject(i);
                HistoricalStockData stockData = HistoricalStockData.fromJson(dataInstanceJson);
                stockDataList.add(stockData);
            } catch (JSONException e) {
                Log.e(TAG, "could not parse json data instance");
            }

        }

        return stockDataList;
    }


    // ===== OKHttp Callback Interface =====

    @Override
    public void onFailure(Request request, IOException e) {
        // handle failures
    }

    @Override
    public void onResponse(Response response) throws IOException {
        String jsonp = response.body().string();
        try {
            String json = jsonp.substring(jsonp.indexOf("(") + 1, jsonp.lastIndexOf(")"));
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray historicalData = jsonResponse.optJSONArray("series");
            JSONObject rangeData = jsonResponse.optJSONObject("ranges");
            mCallback.onStockHistorySuccess(getHistoricalDataArray(historicalData), HistoricalStockDataRanges.fromJson(rangeData));
        } catch (JSONException e) {
            Log.e(TAG, "could not parse JSON in historical data response");
        }
    }
}
