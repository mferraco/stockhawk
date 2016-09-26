package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

        // get cursor here and set it
        mCursor = mContext.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL},
                null,
                null,
                null);
    }

    @Override
    public void onCreate() {
        // No-op
    }

    @Override
    public void onDestroy() {
        // No-op
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
        // No-op
    }
}
