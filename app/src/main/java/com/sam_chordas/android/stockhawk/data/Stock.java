package com.sam_chordas.android.stockhawk.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An object representing a Stock
 */
public class Stock implements Parcelable {

    private static final String TAG = Stock.class.getSimpleName();

    private String symbol;
    private Double ask;
    private Double bid;

    public Stock() {
        // No-op
    }

    public Stock(Cursor cursor) {
        /*posterPath = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.POSTER_PATH));
        adult = (cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.ADULT)) != 0);
        overview = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.OVERVIEW));
        releaseDate = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.RELEASE_DATE));
        genreIds = new ArrayList<>(); // currently not storing genreIds in the DB
        id = cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry._ID));
        originalTitle = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_TITLE));
        originalLanguage = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_LANGUAGE));
        title = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.TITLE));
        backdropPath = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.BACKDROP_PATH));
        popularity = cursor.getDouble(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.POPULARITY));
        voteCount = cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_COUNT));
        video = (cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.VIDEO)) != 0);
        voteAverage = cursor.getDouble(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_AVERAGE));*/
    }

    public static Stock fromJson(JSONObject stockDataJson) {
        Stock stock = new Stock();

        stock.setSymbol(stockDataJson.optString("symbol"));
        stock.setAsk(stockDataJson.optDouble("ask"));
        stock.setBid(stockDataJson.optDouble("big"));

        return stock;
    }

    public static Stock fromString(String stockDataString) {
        try {
            return Stock.fromJson(new JSONObject(stockDataString));
        } catch (JSONException e) {
            Log.d(TAG, "Error: " + e);
            e.printStackTrace();
        }

        return new Stock();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    /* Parcelable Interface */

    public Stock(Parcel in) {
        this.symbol = in.readString();
        this.ask = in.readDouble();
        this.bid = in.readDouble();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeDouble(ask);
        dest.writeDouble(bid);
    }

    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {

        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

}
