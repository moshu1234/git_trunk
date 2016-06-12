package com.example.andrewliu.fatbaby.SlidMenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.andrewliu.fatbaby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liut1 on 6/10/16.
 */
public class TabWebview extends Fragment {
    private View view;
    private WebView webView;
    private String url = "http://www.youku.com/";

    /** 历史记录 */
    private List<String> mHistory = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.tab03_webview, container, false);
        webView = (WebView)view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        mHistory.clear();
        mHistory.add(url);
        webView.setWebViewClient(new HelloWebViewClient());

        /** 处理后退键 */
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView != null
                        && webView.canGoBack()) {
                    if (mHistory.size() > 1) {
                        mHistory.remove(mHistory.size()-1);

                        Log.e("tabwebview","current url:"+mHistory.get(0));
                        webView.loadUrl(mHistory.get(0));
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }
    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mHistory.add(url);
            Log.e("tabwebview","url:"+url);
            Log.e("tabwebview","size:"+mHistory.size());
            for(int i=0;i<mHistory.size();i++){

                Log.e("tabwebview","mHistory:"+mHistory.get(i));
            }
            return true;
        }
    }
}
