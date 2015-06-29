package com.example.minicrpc.fragments.smsbless;

import java.util.List;
import java.util.Map;

import com.example.minicrpc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	
	public static final String IMAGEVIEW = "imageview";
	public static final String CATEGORY = "category";
	private Context mContext;
	private List<Map<String, String>> mData;
	
	public GridViewAdapter(Context context, List<Map<String, String>> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view!=null) {
			holder = (ViewHolder) view.getTag();
		} else {
			view = View.inflate(mContext, R.layout.fragment_sms_gridview_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		Map<String, String> item = mData.get(position);
		int imgid = Integer.parseInt(item.get(IMAGEVIEW));
		String str = item.get(CATEGORY);
		holder.imageview.setImageResource(imgid);
		holder.category.setText(str);
		
		return view;
	}

	
	static class ViewHolder {
		
		@InjectView(R.id.imageview) ImageView imageview;
		@InjectView(R.id.category) TextView category;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			//
		}
		
		
	}
	
	
	
}
