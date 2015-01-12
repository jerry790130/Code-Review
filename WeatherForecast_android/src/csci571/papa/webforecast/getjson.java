package csci571.papa.webforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;


public class getjson  extends AsyncTask<URL, Void, String>{

	@Override
	protected String doInBackground(URL... url) {
		// TODO Auto-generated method stub
		String inputLine = null;
        try {
        	URL networkUrl = url[0];//Load the first element
        	
        	Log.v("test","getJ_____"+networkUrl.toString());
        	
    		URLConnection yc = networkUrl.openConnection();
    		Log.v("test","getJ__1___"+networkUrl.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            Log.v("test","getJ___2__"+networkUrl.toString());
			while ((inputLine = in.readLine()) != null) 
				in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.v("test","getJ__3___"+inputLine);
		return inputLine;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
	}

	

}
