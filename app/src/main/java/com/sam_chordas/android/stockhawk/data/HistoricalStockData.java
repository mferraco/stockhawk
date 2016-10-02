package com.sam_chordas.android.stockhawk.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An object representing one instance of historical stock data
 */
public class HistoricalStockData implements Parcelable {

    private static final String TAG = HistoricalStockData.class.getSimpleName();

    private String symbol;
    private String date;
    private Double close;

    public HistoricalStockData() {
        // No-op
    }

    public static HistoricalStockData fromJson(JSONObject stockDataJson) {
        HistoricalStockData stock = new HistoricalStockData();

        stock.setSymbol(stockDataJson.optString("symbol"));
        stock.setDate(stockDataJson.optString("date"));
        stock.setClose(stockDataJson.optDouble("close"));

        return stock;
    }

    public static HistoricalStockData fromString(String stockDataString) {
        try {
            return HistoricalStockData.fromJson(new JSONObject(stockDataString));
        } catch (JSONException e) {
            Log.d(TAG, "Error: " + e);
            e.printStackTrace();
        }

        return new HistoricalStockData();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    /* Parcelable Interface */

    public HistoricalStockData(Parcel in) {
        this.symbol = in.readString();
        this.date = in.readString();
        this.close = in.readDouble();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeString(date);
        dest.writeDouble(close);
    }

    public static final Parcelable.Creator<HistoricalStockData> CREATOR = new Parcelable.Creator<HistoricalStockData>() {

        public HistoricalStockData createFromParcel(Parcel in) {
            return new HistoricalStockData(in);
        }

        public HistoricalStockData[] newArray(int size) {
            return new HistoricalStockData[size];
        }
    };

}
