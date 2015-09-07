package com.jurassic.iwork.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jurassic.iwork.R;

public class MailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail);
		WebView myWebView = (WebView) findViewById(R.id.mailqWebview);
		WebSettings webSettings = myWebView.getSettings();
		myWebView.setWebViewClient(new WebViewClient());
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl("http://mail.soufun.com/mobile/?q=login");
	}

}