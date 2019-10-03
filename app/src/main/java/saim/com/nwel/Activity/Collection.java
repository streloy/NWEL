package saim.com.nwel.Activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.msebera.android.httpclient.util.EncodingUtils;
import saim.com.nwel.R;

public class Collection extends AppCompatActivity {

    WebView mWebView;
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout layoutProgress;
    TextView layoutProgressText;


    String mainUrl = "http://prs.navanawelding.com/apps/login/authentication";
    String postData = "user_name=saim&password=123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeFull);
        setContentView(R.layout.activity_collection);

        init();
    }

    private void init() {
        layoutProgress = (LinearLayout) findViewById(R.id.layoutProgress);
        layoutProgressText = (TextView) findViewById(R.id.layoutProgressText);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setSupportMultipleWindows(true);

        renderWebPage(mainUrl);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //renderWebPage(mainUrl);
                mWebView.reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*String url = "http://prs.navanawelding.com/apps/login/authentication";
        String postData = "user_name=saim&password=123";
        mWebView.postUrl(url, EncodingUtils.getBytes(postData, "base64"));*/
    }

    protected void renderWebPage(String urlToRender){
        mWebView.setWebViewClient(new MyBrowser());
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                layoutProgressText.setText(newProgress + "%");
                mWebView.setEnabled(false);
                if (newProgress == 100) {
                    mWebView.setEnabled(true);
                    layoutProgress.setVisibility(View.GONE);
                }
            }
        });
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl(urlToRender);
        mWebView.postUrl(urlToRender, EncodingUtils.getBytes(postData, "BASE64"));
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            view.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(String.valueOf(request.getUrl()));
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            layoutProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        ExitDialog();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public void ExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Collection.this);
        alertDialogBuilder.setTitle("Exit!");

        alertDialogBuilder.setMessage("Are you sure want to close?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
