package csci571.papa.webforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.R.color;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class getTable  extends AsyncTask<URL, Void, Void>{

	@Override
	protected Void doInBackground(URL... url) {
		// TODO Auto-generated method stub
		String inputLine = null;
        URL networkUrl = url[0];//Load the first element
		
		Log.v("test","getJ_____"+networkUrl.toString());
		
		WebView myWebView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = myWebView.getSettings();
		myWebView.setBackgroundColor(color.transparent);
		myWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl(networkUrl.toString());
        Log.v("test","getJ__3___"+inputLine);
		return null;
		
	}
	
	private WebView findViewById(int webview) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
