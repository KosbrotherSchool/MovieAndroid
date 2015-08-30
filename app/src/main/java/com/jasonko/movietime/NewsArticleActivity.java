package com.jasonko.movietime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by kolichung on 8/26/15.
 */
public class NewsArticleActivity extends Activity {

    private WebView webView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);
        String mUrl = getIntent().getStringExtra("news_link");

        webView = (WebView) findViewById (R.id.news_webview);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress_value) {
                progress.setProgress(progress_value);
            }

        });

        webView.loadUrl(mUrl);
    }
}
