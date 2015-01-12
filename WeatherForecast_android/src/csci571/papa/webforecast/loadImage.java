package csci571.papa.webforecast;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class loadImage extends AsyncTask<URL, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		 String urldisplay = urls[0].toString();
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            Log.v("test", "LoadImage___1__");
	            mIcon11 = BitmapFactory.decodeStream(in);
	            Log.v("test", "LoadImage___2__");
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	}

}
