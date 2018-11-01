package org.gradle.epicture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        WebView imgurWebView = (WebView) findViewById(R.id.LoginWebView);
        imgurWebView.setBackgroundColor(Color.TRANSPARENT);
        imgurWebView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=8be4c924d7a92be&response_type=token");
        imgurWebView.getSettings().setJavaScriptEnabled(true);

        imgurWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("")) {
                    //splitUrl(url, view);
                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        });
    }

    private void splitUrl(String url, WebView view) {
        String[] outerSplit = url.split("\\#")[1].split("\\&");
        String username = null;
        String accessToken = null;
        String refreshToken = null;

        int index = 0;

        for (String s : outerSplit) {
            String[] innerSplit = s.split("\\=");

            switch (index) {
                // Access Token
                case 0:
                    accessToken = innerSplit[1];
                    break;

                // Refresh Token
                case 3:
                    refreshToken = innerSplit[1];
                    break;

                // Username
                case 4:
                    username = innerSplit[1];
                    break;
                default:

            }
            index++;
        }
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}