package com.sam_chordas.android.stockhawk.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Created by mferraco on 9/25/16.
 */
public class StockWidgetIntentService extends IntentService {

    public StockWidgetIntentService() {
        super("StockWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // retrieve all of the widget ids
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, StocksWidgetProvider.class));

        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent serviceIntent = new Intent(this, StockWidgetService.class);

            RemoteViews widget = new RemoteViews(getPackageName(), R.layout.widget_layout);
            widget.setRemoteAdapter(R.id.widget_list_view, serviceIntent);

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            widget.setOnClickPendingIntent(R.id.widget_id, pendingIntent);

            widgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

    }

}
