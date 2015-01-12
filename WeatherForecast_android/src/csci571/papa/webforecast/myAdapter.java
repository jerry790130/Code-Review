package csci571.papa.webforecast;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class myAdapter extends SimpleAdapter {

	public myAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = super .getView(position, convertView, parent);
		if(position==0){
			view.setBackgroundResource(R.drawable.colorful);
			TextView textView = (TextView) view.findViewById(R.id.listHigh);
			textView.setTextColor(Color.BLACK);
			textView = (TextView) view.findViewById(R.id.listLow);
			textView.setTextColor(Color.BLACK);
		}
		else{
			int colorPos = position % colors.length;
			if (colorPos == 1)
				view.setBackgroundColor(Color.argb(250, 255, 255, 255));

			else
				view.setBackgroundColor(Color.argb(250, 224, 243, 250));

		}
		return view;
	}


}
