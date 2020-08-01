package com.example.navigation2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.navigation2.ui.home.HomeFragment;

public class WebActivity extends AppCompatActivity {
private WebView webView;
Intent myintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
       webView=findViewById(R.id.webview);
       webView.setWebViewClient(new WebViewClient());
       webView.getSettings().setBuiltInZoomControls(true);
       webView.getSettings().setJavaScriptEnabled(true);
      webView.getSettings().setUseWideViewPort(true);
       webView.getSettings().setLoadWithOverviewMode(true);
       myintent=getIntent();
       String url1=myintent.getStringExtra("url1");

       webView.loadUrl(url1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(WebActivity.this,MainActivity.class);
        startActivity(i);
    }
}
