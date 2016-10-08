package com.sam_chordas.android.stockhawk.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Holds data about a specific range of stock information.
 */
public class HistoricalStockDataRanges implements Parcelable {

    private static final String TAG = HistoricalStockDataRanges.class.getSimpleName();

    private float closeMin;
    private float closeMax;
    private float highMin;
    private float highMax;
    private float lowMin;
    private float lowMax;
    private float openMin;
    private float openMax;

    public HistoricalStockDataRanges() {
        // No-op
    }

    public static HistoricalStockDataRanges fromJson(JSONObject rangeDataJson) {
        HistoricalStockDataRanges stock = new HistoricalStockDataRanges();

        stock.setCloseMin((float)rangeDataJson.optJSONObject("close").optDouble("min"));
        stock.setCloseMax((float)rangeDataJson.optJSONObject("close").optDouble("max"));
        stock.setHighMin((float)rangeDataJson.optJSONObject("high").optDouble("min"));
        stock.setHighMax((float)rangeDataJson.optJSONObject("high").optDouble("max"));
        stock.setLowMin((float)rangeDataJson.optJSONObject("low").optDouble("min"));
        stock.setLowMax((float)rangeDataJson.optJSONObject("low").optDouble("max"));
        stock.setOpenMin((float)rangeDataJson.optJSONObject("open").optDouble("min"));
        stock.setOpenMax((float)rangeDataJson.optJSONObject("open").optDouble("max"));

        return stock;
    }

    public static HistoricalStockDataRanges fromString(String rangeDatString) {
        try {
            return HistoricalStockDataRanges.fromJson(new JSONObject(rangeDatString));
        } catch (JSONException e) {
            Log.d(TAG, "Error: " + e);
            e.printStackTrace();
        }

        return new HistoricalStockDataRanges();
    }

    public float getCloseMin() {
        return closeMin;
    }

    public void setCloseMin(float closeMin) {
        this.closeMin = closeMin;
    }

    public float getCloseMax() {
        return closeMax;
    }

    public void setCloseMax(float closeMax) {
        this.closeMax = closeMax;
    }

    public float getHighMin() {
        return highMin;
    }

    public void setHighMin(float highMin) {
        this.highMin = highMin;
    }

    public float getHighMax() {
        return highMax;
    }

    public void setHighMax(float highMax) {
        this.highMax = highMax;
    }

    public float getLowMin() {
        return lowMin;
    }

    public void setLowMin(float lowMin) {
        this.lowMin = lowMin;
    }

    public float getLowMax() {
        return lowMax;
    }

    public void setLowMax(float lowMax) {
        this.lowMax = lowMax;
    }

    public float getOpenMin() {
        return openMin;
    }

    public void setOpenMin(float openMin) {
        this.openMin = openMin;
    }

    public float getOpenMax() {
        return openMax;
    }

    public void setOpenMax(float openMax) {
        this.openMax = openMax;
    }

     /* Parcelable Interface */

    public HistoricalStockDataRanges(Parcel in) {
        this.closeMin = in.readFloat();
        this.closeMax = in.readFloat();
        this.highMin = in.readFloat();
        this.highMax = in.readFloat();
        this.lowMin = in.readFloat();
        this.lowMax = in.readFloat();
        this.openMin = in.readFloat();
        this.openMax = in.readFloat();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(closeMin);
        dest.writeFloat(closeMax);
        dest.writeFloat(highMin);
        dest.writeFloat(highMax);
        dest.writeFloat(lowMin);
        dest.writeFloat(lowMax);
        dest.writeFloat(openMin);
        dest.writeFloat(openMax);
    }

    public static final Parcelable.Creator<HistoricalStockDataRanges> CREATOR = new Parcelable.Creator<HistoricalStockDataRanges>() {

        public HistoricalStockDataRanges createFromParcel(Parcel in) {
            return new HistoricalStockDataRanges(in);
        }

        public HistoricalStockDataRanges[] newArray(int size) {
            return new HistoricalStockDataRanges[size];
        }
    };
}
