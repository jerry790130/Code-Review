package csci571.papa.webforecast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

class forecast{
	int high_f;
	int high_c;
	int low_f;
	int low_c;
	String day;
	String text;
}

public class parseJson {
	public String link = null;
	public String condition_text = null;
	public int condition_temp_c = 0;
	public int condition_temp_f = 0;
	public String location_region = null;
	public String location_country = null;
	public String location_city = null;
	public String img = null;
	public String feed_c = null;
	public String feed_f = null;
	
	public forecast forecasts_0=new forecast();
	public forecast forecasts_1=new forecast();
	public forecast forecasts_2=new forecast();
	public forecast forecasts_3=new forecast();
	public forecast forecasts_4=new forecast();
	
	public parseJson(JSONObject json){
		try {
			 JSONObject jj = json.getJSONObject("weather");
			 link=getLink(jj);
			 condition_text=getCondition_text(jj);
			 condition_temp_c=getCondition_temp_c(jj);
			 condition_temp_f=getCondition_temp_f(jj);
			 location_region=getLocation_region(jj);
			 location_country=getLocation_country(jj);
			 location_city=getLocation_city(jj);
			 img=getImg(jj);
			 feed_c=getFeed_c(jj);	
			 feed_f=getFeed_f(jj);	
			 
			 forecasts_0=getForecast(jj,0);
			 forecasts_1=getForecast(jj,1);
			 forecasts_2=getForecast(jj,2);
			 forecasts_3=getForecast(jj,3);
			 forecasts_4=getForecast(jj,4);
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private forecast getForecast(JSONObject jj,int j) throws JSONException {
		forecast fff=new forecast();
		JSONArray jArray = jj.getJSONArray("forecast");

			Log.v("ptest","parseJson_0_"+j);
			 try {
				 JSONObject oneObject = jArray.getJSONObject(j);
				 Log.v("ptest","parseJson_1_"+oneObject);
				 fff.day=oneObject.getString("day");
				 Log.v("ptest","parseJson_2_"+ fff.day);
				 fff.high_c=oneObject.getInt("high_c");
				 Log.v("ptest","parseJson_3_"+fff.high_c);
				 fff.high_f=oneObject.getInt("high_f");
				 Log.v("ptest","arseJson_4_"+fff.high_f);
				 fff.low_c=oneObject.getInt("low_c");
				 Log.v("ptest","parseJson_5_"+fff.low_c);
				 fff.low_f=oneObject.getInt("low_f");
				 Log.v("ptest","parseJson_6_"+fff.low_f);
				 fff.text=oneObject.getString("text");
				 Log.v("ptest","parseJson_7_"+fff.text);
				 
			 } catch (JSONException e) {
			        // Oops
				 Log.v("ptest",e.getMessage());
				 
			 }
		return fff;
	}

	private String getLink(JSONObject json) throws JSONException {
			return json.getString("link");
	}
	private String getCondition_text(JSONObject json) throws JSONException{	
		return json.getJSONObject("condition").getString("text");
	}
	private int getCondition_temp_f(JSONObject json) throws JSONException{	
		return json.getJSONObject("condition").getInt("temp_f");
	}
	private int getCondition_temp_c(JSONObject json) throws JSONException{	
		return json.getJSONObject("condition").getInt("temp_c");
	}
	private String getLocation_region(JSONObject json) throws JSONException{	
		return json.getJSONObject("location").getString("region");
	}
	private String getLocation_country(JSONObject json) throws JSONException{	
		return json.getJSONObject("location").getString("country");
	}
	private String getLocation_city(JSONObject json) throws JSONException{	
		return json.getJSONObject("location").getString("city");
	}
	private String getImg(JSONObject json) throws JSONException{	
		return json.getString("img");
	}
	private String getFeed_c(JSONObject json) throws JSONException{	
		return json.getString("feed_c");
	}
	private String getFeed_f(JSONObject json) throws JSONException{	
		return json.getString("feed_f");
	}
}
