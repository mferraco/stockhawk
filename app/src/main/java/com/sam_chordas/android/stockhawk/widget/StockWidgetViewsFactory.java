package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Sets data in rows on ListView of widget
 */
public class StockWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private Cursor mCursor;

    public StockWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        // get cursor here and set it
        mCursor = mContext.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                new String[]{"Distinct " + QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.PERCENT_CHANGE},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);

        boolean movedToPosition = mCursor.moveToPosition(position);
        if (movedToPosition) {
            row.setTextViewText(R.id.stock_symbol, mCursor.getString(mCursor.getColumnIndex("symbol")));
            row.setTextViewText(R.id.bid_price, mCursor.getString(mCursor.getColumnIndex("bid_price")));
            row.setTextViewText(R.id.change, mCursor.getString(mCursor.getColumnIndex("percent_change")));

            // TODO: determine which detail view to open from widget here
            Intent intent = new Intent(mContext, MyStocksActivity.class);
            row.setOnClickFillInIntent(R.id.stock_symbol, intent);
        }

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        // TODO: figure out how to get this to update the data in the widget...

        final long token = Binder.clearCallingIdentity();
        try {
            mCursor = mContext.getContentResolver().query(
                    QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{"Distinct " + QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.PERCENT_CHANGE},
                    QuoteColumns.ISCURRENT + " = ?",
                    new String[]{"1"},
                    null);
        } finally {
            Binder.restoreCallingIdentity(token);
        }

    }
}
