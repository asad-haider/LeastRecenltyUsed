package com.leastrecenltyused;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    WebView framesWebView;
    TextView cacheHitTextView;
    TextView pageFaultTextView;
    final String mimeType = "text/html";
    final String encoding = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        framesWebView = (WebView) findViewById(R.id.framesWebView);
        cacheHitTextView = (TextView) findViewById(R.id.cacheHitTextView);
        pageFaultTextView = (TextView) findViewById(R.id.pageFaultTextView);

        Intent intent  = getIntent();
        String frames = intent.getStringExtra("Frames");
        int cacheHit = intent.getIntExtra("Cache Hit", 0);
        int pageFault = intent.getIntExtra("Page Fault", 0);

        framesWebView.loadDataWithBaseURL("", frames, mimeType, encoding, "");
        framesWebView.getSettings().setSupportZoom(true);
        framesWebView.getSettings().setUseWideViewPort(true);
        framesWebView.getSettings().setBuiltInZoomControls(true);
        framesWebView.getSettings().setLoadWithOverviewMode(true);

        framesWebView.setAlpha(0f);
        cacheHitTextView.setAlpha(0f);
        pageFaultTextView.setAlpha(0f);

        pageFaultTextView.animate().alpha(1f).setDuration(1000).start();
        cacheHitTextView.animate().alpha(1f).setDuration(1600).start();
        framesWebView.animate().alpha(1f).setDuration(2000).start();


        cacheHitTextView.setText("Cache Hits: " + cacheHit);
        pageFaultTextView.setText("Page Faults: " + pageFault);


    }
}
