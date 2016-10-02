package com.sam_chordas.android.stockhawk.callbacks;

import com.sam_chordas.android.stockhawk.data.HistoricalStockData;

import java.util.List;

/**
 * Defines the callback interface for when the stock data is retrieved
 */
public interface StockHistoryCallback {

    void onStockHistorySuccess(List<HistoricalStockData> stockHistoryData);

    void onStockHistoryFailure();

}
