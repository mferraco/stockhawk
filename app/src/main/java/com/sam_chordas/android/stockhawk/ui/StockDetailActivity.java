package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Activity managing the detail view which includes the history for a stock on a line graph
 */
public class StockDetailActivity extends AppCompatActivity {

    private static final String TAG = StockDetailActivity.class.getSimpleName();

    String mSymbol;
    Cursor mCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        mSymbol = getIntent().getStringExtra(MyStocksActivity.SYMBOL_ARGUMENT);

        if (mSymbol != null) {

            // get all bid prices for this specific stock
            mCursor = getContentResolver().query(
                    QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE},
                    QuoteColumns.SYMBOL + " = ?",
                    new String[]{mSymbol},
                    null);


            Log.d(TAG, Integer.toString(mCursor.getCount()));
        }
    }
}
