package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jasonko.movietime.R;

/**
 * Created by kolichung on 8/31/15.
 */
public class CreditCardDetailFragment extends Fragment {
    public static final String ARG_CARD = "CARD_TYPE_ID";

    private int mCardTypeID;
    private WebView webView;
    private ProgressBar progress;

    public static CreditCardDetailFragment newInstance(int card_type_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_CARD, card_type_id);
        CreditCardDetailFragment fragment = new CreditCardDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardTypeID = getArguments().getInt(ARG_CARD);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_detail, container, false);
        webView = (WebView) view.findViewById(R.id.news_webview);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
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

        String mUrl = "";
        if (mCardTypeID == 0){
            mUrl = "http://creditcardmovie.blogspot.tw/2015/08/2015.html";
        }else {
            mUrl = "http://creditcardmovie.blogspot.tw/2015/08/2015_31.html";
        }

        webView.loadUrl(mUrl);

        return view;
    }

}
