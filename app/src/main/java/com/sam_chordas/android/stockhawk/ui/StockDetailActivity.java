package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.callbacks.StockHistoryCallback;
import com.sam_chordas.android.stockhawk.data.HistoricalStockData;
import com.sam_chordas.android.stockhawk.service.HistoricalDataService;

import java.util.List;

/**
 * Activity managing the detail view which includes the history for a stock on a line graph
 */
public class StockDetailActivity extends AppCompatActivity implements StockHistoryCallback {

    private static final String TAG = StockDetailActivity.class.getSimpleName();

    String mSymbol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        mSymbol = getIntent().getStringExtra(MyStocksActivity.SYMBOL_ARGUMENT);

        if (mSymbol != null) {
            // tell historical data service to get the data
            HistoricalDataService.getInstance(this, this).getHistoricalStockData(mSymbol);
        }
    }

    @Override
    public void onStockHistorySuccess(List<HistoricalStockData> stockHistoryData) {
        Log.d(TAG, stockHistoryData.toString());
    }

    @Override
    public void onStockHistoryFailure() {
        Log.d(TAG, "FAILED");
    }
}
