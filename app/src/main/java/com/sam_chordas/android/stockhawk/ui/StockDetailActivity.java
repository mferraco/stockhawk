package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.callbacks.StockHistoryCallback;
import com.sam_chordas.android.stockhawk.data.HistoricalStockData;
import com.sam_chordas.android.stockhawk.data.HistoricalStockDataRanges;
import com.sam_chordas.android.stockhawk.service.HistoricalDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity managing the detail view which includes the history for a stock on a line graph
 */
public class StockDetailActivity extends AppCompatActivity implements StockHistoryCallback {

    private static final String TAG = StockDetailActivity.class.getSimpleName();

    private String mSymbol;
    private LineChart chart;
    private HistoricalDataService mService;

    private TextView yearlyHighTextView;
    private TextView yearlyLowTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        chart = (LineChart) findViewById(R.id.linechart);

        mService = new HistoricalDataService(this, this);

        mSymbol = getIntent().getStringExtra(MyStocksActivity.SYMBOL_ARGUMENT);

        if (mSymbol != null) {
            TextView titleTextView = (TextView) findViewById(R.id.stock_title_textview);
            titleTextView.setText(mSymbol);

            // tell historical data service to get the data
            mService.getHistoricalStockData(mSymbol);
        }
    }

    @Override
    public void onStockHistorySuccess(final List<HistoricalStockData> stockHistoryData, final HistoricalStockDataRanges rangeData) {

        runOnUiThread(new Runnable() {
            public void run() {

                StringBuilder stringBuilder = new StringBuilder();

                List<Entry> entries = new ArrayList<>();
                List<String> xLabels = new ArrayList<>();
                for (int i = 0; i < stockHistoryData.size(); i++) {
                    String xLabel = stockHistoryData.get(i).getDate();
                    xLabels.add(xLabel);

                    Float value = stockHistoryData.get(i).getClose().floatValue();
                    entries.add(new Entry(value, i));

                    stringBuilder.append(getString(R.string.chart_content_description, xLabel, value));
                }

                chart.setContentDescription(stringBuilder.toString());

                LineDataSet dataSet = new LineDataSet(entries, getString(R.string.chart_label));
                dataSet.setDrawCircles(false);

                final LineData lineData = new LineData(xLabels, dataSet);
                lineData.setDrawValues(false);

                chart.setData(lineData);

                // set up parameters for the chart here

                XAxis xAxis = chart.getXAxis();
                xAxis.setEnabled(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setDrawLabels(true);

                YAxis yAxisLeft = chart.getAxisLeft();
                yAxisLeft.setTextColor(Color.WHITE);

                YAxis yAxisRight = chart.getAxisRight();
                yAxisRight.setEnabled(false);

                chart.setDescription("");
                chart.invalidate();

                // Update other UI values
                yearlyHighTextView = (TextView) findViewById(R.id.yearly_high_textview);
                yearlyHighTextView.setText(getString(R.string.yearly_high, rangeData.getHighMax()));
                yearlyLowTextView = (TextView) findViewById(R.id.yearly_low_textview);
                yearlyLowTextView.setText(getString(R.string.yearly_low, rangeData.getLowMin()));
            }
        });
    }

    @Override
    public void onStockHistoryFailure() {
        Log.d(TAG, "FAILED");
    }
}
