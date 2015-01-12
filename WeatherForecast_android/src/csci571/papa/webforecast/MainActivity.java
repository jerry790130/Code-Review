package csci571.papa.webforecast;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class MainActivity extends Activity {

	private Facebook facebook = new Facebook("1415079235393695");
	parseJson weather;
	String unit_tmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Button b = (Button) findViewById(R.id.button1);

		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				EditText input_text = (EditText) findViewById(R.id.editText1);
				String input_string = input_text.getText().toString();
				String location_type = check_locationType(input_string);

				clean_Main();

				if (location_type != "ZIP_code" && location_type != "City") {
				Toast toast = Toast.makeText(MainActivity.this, location_type,
						Toast.LENGTH_LONG);
				toast.show();
				}
				input_string = URLEncoder.encode(input_text.getText()
						.toString());

				unit_tmp = "F";
				RadioButton c = (RadioButton) findViewById(R.id.RadioButton01);
				if (c.isChecked())
					unit_tmp = "C";

				// check input_type
				if (location_type == "ZIP_code" || location_type == "City") {
					// getWoeid
					String woeid = null;
					try {
						Log.v("test", "Main_woeid_click");
						URL onLineURL = new URL(
								"http://cs-server.usc.edu:37960/hw8/servlet/getWoeid?location_type="
										+ location_type + "&location="
										+ input_string);
						Log.v("test", onLineURL.toString());
						woeid = new getjson().execute(onLineURL).get();
						Log.v("FFFFFF", "woeid:" + woeid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (woeid.equals("Error")) {
						TextView text_region = (TextView) findViewById(R.id.textView_city);
						text_region
								.setText("Weather Information cannot be found");
					} else {
					// getJson
					String page;
					try {
						Log.v("test", "table");

						Log.v("test", "Main_Json_click");
						URL onLineurl = new URL(
								"http://cs-server.usc.edu:37960/hw8/servlet/SimpleResponse?woeid="
										+ woeid);
						page = new getjson().execute(onLineurl).get();

							if (page.equals("Error")) {
								TextView text_region = (TextView) findViewById(R.id.textView_city);
								text_region
										.setText("Weather Information cannot be found");
							} else {
						// Log.v("test", "Main_Json___"+page);
						JSONObject jsonObject = new JSONObject(page);
						// Log.v("test","Main_Json___String___"+jsonObject.toString())
						// ;

						weather = new parseJson(jsonObject);

						show_data(weather, unit_tmp);

								InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(v.getWindowToken(),
										0);

						URL url_img = new URL(weather.img);
						Bitmap bmp = new loadImage().execute(url_img).get();
						ImageView imageView = (ImageView) findViewById(R.id.imageView1);
						imageView.setImageBitmap(bmp);

						Log.v("test", "Main_Json_click__Feed:" + weather.feed_c);

						ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
						mylist = getForecastList(weather, unit_tmp);

						myAdapter adapter = new myAdapter(MainActivity.this,
								mylist, R.layout.table, new String[] { "Day",
										"Weather", "High", "Low" }, new int[] {
										R.id.listDay, R.id.listWeather,
										R.id.listHigh, R.id.listLow });

						ListView lv = (ListView) findViewById(R.id.webView);
						lv.setAdapter(adapter);


						TextView weather_current = (TextView) findViewById(R.id.weather_current);
						weather_current.setVisibility(TextView.VISIBLE);
						weather_current
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
												final Dialog dialog = new Dialog(
														MainActivity.this);
												dialog.setContentView(R.layout.fb_popup_dialog);
												dialog.setTitle("Post To Facebook");
												Button dialogButtonPost = (Button) dialog
														.findViewById(R.id.dPOST);
												Button dialogButtonCancel = (Button) dialog
														.findViewById(R.id.dCancel);

												dialogButtonPost
														.setOnClickListener(new OnClickListener() {
															@Override
															public void onClick(
																	View v) {
																Bundle parameters = getParameter(weather);
																facebook.dialog(
																		MainActivity.this,
																		"feed",
																		parameters,
																		new DialogListener() {
																			@Override
																			public void onComplete(
																					Bundle values) {
																				dialog.dismiss();
																				if (values
																						.getString("post_id") != null) {
																				Toast toast = Toast
																						.makeText(
																								MainActivity.this,
																								"Posting Success, id:"
																										+ values.getString("post_id"),
																								Toast.LENGTH_LONG);
																				toast.show();
																				} else {
																					Toast toast = Toast
																							.makeText(
																									MainActivity.this,
																									"Post is canceled",
																									Toast.LENGTH_LONG);
																					toast.show();
																				}
																			}

																			@Override
																			public void onFacebookError(
																					FacebookError error) {
																				dialog.dismiss();
																			}

																			@Override
																			public void onError(
																					DialogError e) {
																				dialog.dismiss();
																			}

																			@Override
																			public void onCancel() {
																				dialog.dismiss();
																			}
																		});

															}
														});

												dialogButtonCancel
														.setOnClickListener(new OnClickListener() {

															@Override
															public void onClick(
																	View v) {
																// TODO
																dialog.dismiss();
															}
													
												});

												dialog.show();
									}

									private Bundle getParameter(
											parseJson weather) {
										// TODO Auto-generated method stub

										Bundle params = new Bundle();

										params.putString(
												"name",
												weather.location_city
														+ ", "
														+ weather.location_region
														+ ", "
														+ weather.location_country);
										params.putString("link", weather.link);
										params.putString("picture", weather.img);
										params.putString(
												"caption",
												"The current condition for "
														+ weather.location_city
														+ " is "
														+ weather.condition_text);
										params.putString("description",
												getCurrentdescription(weather));
										params.putString("properties",
												getProperties(weather));
										params.putString("message", "HW9");

										return params;
									}

									private String getProperties(
											parseJson weather) {
										// TODO Auto-generated method stub
										JSONObject property = new JSONObject();
										JSONObject properties = new JSONObject();
										try {
											property.put("text", "here");
											property.put("href", weather.link);
											properties.put("Look at details",
													property);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										return properties.toString();
									}

									private String getCurrentdescription(
											parseJson weather) {
										// TODO Auto-generated method stub
										String ans = null;
										if (unit_tmp == "F")
											ans = "Temperature is current "
													+ weather.condition_temp_f
													+ "¢X" + unit_tmp;
										else
											ans = "Temperature is current "
													+ weather.condition_temp_c
													+ "¢X" + unit_tmp;
										return ans;
									}
								});
						TextView weather_forecast = (TextView) findViewById(R.id.weather_forecast);
						weather_forecast.setVisibility(TextView.VISIBLE);
						weather_forecast
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
												final Dialog dialog = new Dialog(
														MainActivity.this);
												dialog.setContentView(R.layout.fb_popup_dialog);
												dialog.setTitle("Post To Facebook");
												Button dialogButtonPost = (Button) dialog
														.findViewById(R.id.dPOST);
												dialogButtonPost
														.setText("Post Weather Forecast");
												Button dialogButtonCancel = (Button) dialog
														.findViewById(R.id.dCancel);
												dialogButtonPost
												.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(
															View v) {
																Bundle parameters = getParameter(weather);
																facebook.dialog(
																		MainActivity.this,
																		"feed",
																		parameters,
																		new DialogListener() {
																			@Override
																			public void onComplete(
																					Bundle values) {
																				dialog.dismiss();
																				if (values
																						.getString("post_id") != null) {
																				Toast toast = Toast
																						.makeText(
																								MainActivity.this,
																								"Posting Success, id:"
																										+ values.getString("post_id"),
																								Toast.LENGTH_LONG);
																				toast.show();
																				} else {
																					Toast toast = Toast
																							.makeText(
																									MainActivity.this,
																									"Post is canceled",
																									Toast.LENGTH_LONG);
																					toast.show();
																				}
																			}

																			@Override
																			public void onFacebookError(
																					FacebookError error) {
																				dialog.dismiss();
																			}

																			@Override
																			public void onError(
																					DialogError e) {
																				dialog.dismiss();
																			}

																			@Override
																			public void onCancel() {
																				dialog.dismiss();
																			}
																		});
															}
														});

												dialogButtonCancel
														.setOnClickListener(new OnClickListener() {

															@Override
															public void onClick(
																	View v) {
																// TODO
																dialog.dismiss();
															}

														});

												dialog.show();
									}


									private Bundle getParameter(
											parseJson weather) {
										// TODO Auto-generated method stub

										Bundle params = new Bundle();

										params.putString(
												"name",
												weather.location_city
														+ ", "
														+ weather.location_region
														+ ", "
														+ weather.location_country);
										params.putString("link", weather.link);
										params.putString("picture",
												"http://scf.usc.edu/~csci571/2013Fall/hw8/weather.jpg");
										params.putString("caption",
												"Weather Forecast for "
														+ weather.location_city);
										params.putString("description",
												getdescription(weather));
										params.putString("properties",
												getProperties(weather));
										params.putString("message", "HW9");

										return params;
									}

									private String getProperties(
											parseJson weather) {
										// TODO Auto-generated method stub
										JSONObject property = new JSONObject();
										JSONObject properties = new JSONObject();
										try {
											property.put("text", "here");
											property.put("href", weather.link);
											properties.put("Look at details",
													property);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										return properties.toString();
									}

									private String getdescription(
											parseJson weather) {
										// TODO Auto-generated method stub
										String ans = null;
										if (unit_tmp == "F") {
											ans = weather.forecasts_0.day
													+ ": "
													+ weather.forecasts_0.text
													+ ", "
													+ weather.forecasts_0.high_f
													+ "/"
													+ weather.forecasts_0.low_f
													+ "¢XF; ";
											ans += weather.forecasts_1.day
													+ ": "
													+ weather.forecasts_1.text
													+ ", "
													+ weather.forecasts_1.high_f
													+ "/"
													+ weather.forecasts_1.low_f
													+ "¢XF; ";
											ans += weather.forecasts_2.day
													+ ": "
													+ weather.forecasts_2.text
													+ ", "
													+ weather.forecasts_2.high_f
													+ "/"
													+ weather.forecasts_2.low_f
													+ "¢XF; ";
											ans += weather.forecasts_3.day
													+ ": "
													+ weather.forecasts_3.text
													+ ", "
													+ weather.forecasts_3.high_f
													+ "/"
													+ weather.forecasts_3.low_f
													+ "¢XF; ";
											ans += weather.forecasts_4.day
													+ ": "
													+ weather.forecasts_4.text
													+ ", "
													+ weather.forecasts_4.high_f
													+ "/"
													+ weather.forecasts_4.low_f
													+ "¢XF.";
										} else {
											ans = weather.forecasts_0.day
													+ ": "
													+ weather.forecasts_0.text
													+ ", "
													+ weather.forecasts_0.high_c
													+ "/"
													+ weather.forecasts_0.low_c
													+ "¢XC; ";
											ans += weather.forecasts_1.day
													+ ": "
													+ weather.forecasts_1.text
													+ ", "
													+ weather.forecasts_1.high_c
													+ "/"
													+ weather.forecasts_1.low_c
													+ "¢XC; ";
											ans += weather.forecasts_2.day
													+ ": "
													+ weather.forecasts_2.text
													+ ", "
													+ weather.forecasts_2.high_c
													+ "/"
													+ weather.forecasts_2.low_c
													+ "¢XC; ";
											ans += weather.forecasts_3.day
													+ ": "
													+ weather.forecasts_3.text
													+ ", "
													+ weather.forecasts_3.high_c
													+ "/"
													+ weather.forecasts_3.low_c
													+ "¢XC; ";
											ans += weather.forecasts_4.day
													+ ": "
													+ weather.forecasts_4.text
													+ ", "
													+ weather.forecasts_4.high_c
													+ "/"
													+ weather.forecasts_4.low_c
													+ "¢XC.";
										}
										return ans;
									}
								});
							}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				}

			}

			private ArrayList<HashMap<String, String>> getForecastList(
					parseJson w, String u) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Day", "Day");
				map.put("Weather", "Weather");
				map.put("High", "High");
				map.put("Low", "Low");
				list.add(map);

				if (u == "F") {
				HashMap<String, String> map0 = new HashMap<String, String>();
				map0.put("Day", w.forecasts_0.day);
				map0.put("Weather", w.forecasts_0.text);
				map0.put("High", Integer.toString(w.forecasts_0.high_f) + "¢XF");
				map0.put("Low", Integer.toString(w.forecasts_0.low_f) + "¢XF");
				list.add(map0);

				HashMap<String, String> map1 = new HashMap<String, String>();
				map1.put("Day", w.forecasts_1.day);
				map1.put("Weather", w.forecasts_1.text);
				map1.put("High", Integer.toString(w.forecasts_1.high_f) + "¢XF");
				map1.put("Low", Integer.toString(w.forecasts_1.low_f) + "¢XF");
				list.add(map1);

				HashMap<String, String> map2 = new HashMap<String, String>();
				map2.put("Day", w.forecasts_2.day);
				map2.put("Weather", w.forecasts_2.text);
				map2.put("High", Integer.toString(w.forecasts_2.high_f) + "¢XF");
				map2.put("Low", Integer.toString(w.forecasts_2.low_f) + "¢XF");
				list.add(map2);

				HashMap<String, String> map3 = new HashMap<String, String>();
				map3.put("Day", w.forecasts_3.day);
				map3.put("Weather", w.forecasts_3.text);
				map3.put("High", Integer.toString(w.forecasts_3.high_f) + "¢XF");
				map3.put("Low", Integer.toString(w.forecasts_3.low_f) + "¢XF");
				list.add(map3);

				HashMap<String, String> map4 = new HashMap<String, String>();
				map4.put("Day", w.forecasts_4.day);
				map4.put("Weather", w.forecasts_4.text);
				map4.put("High", Integer.toString(w.forecasts_4.high_f) + "¢XF");
				map4.put("Low", Integer.toString(w.forecasts_4.low_f) + "¢XF");
				list.add(map4);
				} else {
					HashMap<String, String> map0 = new HashMap<String, String>();
					map0.put("Day", w.forecasts_0.day);
					map0.put("Weather", w.forecasts_0.text);
					map0.put("High", Integer.toString(w.forecasts_0.high_c)
							+ "¢XC");
					map0.put("Low", Integer.toString(w.forecasts_0.low_c)
							+ "¢XC");
					list.add(map0);

					HashMap<String, String> map1 = new HashMap<String, String>();
					map1.put("Day", w.forecasts_1.day);
					map1.put("Weather", w.forecasts_1.text);
					map1.put("High", Integer.toString(w.forecasts_1.high_c)
							+ "¢XC");
					map1.put("Low", Integer.toString(w.forecasts_1.low_c)
							+ "¢XC");
					list.add(map1);

					HashMap<String, String> map2 = new HashMap<String, String>();
					map2.put("Day", w.forecasts_2.day);
					map2.put("Weather", w.forecasts_2.text);
					map2.put("High", Integer.toString(w.forecasts_2.high_c)
							+ "¢XC");
					map2.put("Low", Integer.toString(w.forecasts_2.low_c)
							+ "¢XC");
					list.add(map2);

					HashMap<String, String> map3 = new HashMap<String, String>();
					map3.put("Day", w.forecasts_3.day);
					map3.put("Weather", w.forecasts_3.text);
					map3.put("High", Integer.toString(w.forecasts_3.high_c)
							+ "¢XC");
					map3.put("Low", Integer.toString(w.forecasts_3.low_c)
							+ "¢XC");
					list.add(map3);

					HashMap<String, String> map4 = new HashMap<String, String>();
					map4.put("Day", w.forecasts_4.day);
					map4.put("Weather", w.forecasts_4.text);
					map4.put("High", Integer.toString(w.forecasts_4.high_c)
							+ "¢XC");
					map4.put("Low", Integer.toString(w.forecasts_4.low_c)
							+ "¢XC");
					list.add(map4);
				}

				return list;
			}
		});

	}

	protected void clean_Main() {
		// TODO Auto-generated method stub
		TextView text_region = (TextView) findViewById(R.id.textView_region);
		text_region.setText(" ");
		TextView text_city = (TextView) findViewById(R.id.textView_city);
		text_city.setText(" ");
		TextView text_text = (TextView) findViewById(R.id.textView_text);
		text_text.setText(" ");
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(null);
		TextView text_temp = (TextView) findViewById(R.id.textView_tmp);
		text_temp.setText(" ");
		TextView weather_current = (TextView) findViewById(R.id.weather_current);
		weather_current.setVisibility(TextView.INVISIBLE);
		TextView weather_forecast = (TextView) findViewById(R.id.weather_forecast);
		weather_forecast.setVisibility(TextView.INVISIBLE);
		TextView weather_fff = (TextView) findViewById(R.id.textView_fff);
		weather_fff.setVisibility(TextView.INVISIBLE);
		ListView lv = (ListView) findViewById(R.id.webView);
		lv.setAdapter(null);
		return;

	}

	protected String check_locationType(String input_string) {
		// TODO Auto-generated method stub

		String reg_zipcode = "^\\d{5}$";
		String reg_zipcode_d = "^\\d*$";
		String reg_city = "^\\s*\\w.+,\\s*\\w.+[,\\s*\\w.+]*$";
		Log.v("PPP", input_string);
		if (input_string.matches(reg_zipcode)) {
			return "ZIP_code";
		} else if (input_string.matches(reg_city)) {
			return "City";
		} else {
			if (input_string.equals("")) {
				return ("Please input query");
			} else if (!input_string.matches(reg_city)
					&& !input_string.matches(reg_zipcode_d)) {
				return "Invalid location: must include state or country separate by comma. Example: Los Angeles, CA";
			} else if (!input_string.matches(reg_zipcode)
					&& input_string.matches(reg_zipcode_d)) {
				return ("Invalid zipcode: must be five digits. Example:90089");
			} else {
				return ("Error input");
			}
		}
	}

	protected Void show_data(parseJson weather, String unit) {
		TextView text_region = (TextView) findViewById(R.id.textView_region);
		text_region.setText(weather.location_region + ", "
				+ weather.location_country);
		Log.v("test", "Main_Json_click__Feed:" + weather.location_region);
		TextView text_city = (TextView) findViewById(R.id.textView_city);
		text_city.setText(weather.location_city);
		Log.v("test", "Main_Json_click__LocCity:" + weather.location_city);
		TextView text_text = (TextView) findViewById(R.id.textView_text);
		text_text.setText(weather.condition_text);
		Log.v("test", "Main_Json_click__Text:" + weather.condition_text);
		TextView weather_fff = (TextView) findViewById(R.id.textView_fff);
		weather_fff.setVisibility(TextView.VISIBLE);
		TextView text_temp = (TextView) findViewById(R.id.textView_tmp);

		if (unit == "F")
			text_temp
					.setText(Integer.toString(weather.condition_temp_f) + "¢XF");
		else
			text_temp
					.setText(Integer.toString(weather.condition_temp_c) + "¢XC");
		Log.v("test",
				"Main_Json_click__Temp:"
						+ Integer.toString(weather.condition_temp_f) + "¢XF");
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
