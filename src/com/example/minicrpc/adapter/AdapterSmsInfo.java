package com.example.minicrpc.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.R;
import com.example.minicrpc.databasedao.SmsContent;
import com.example.minicrpc.utils.MinicRPCLog;

public class AdapterSmsInfo extends BaseAdapter {

	
	private static final String TAG = "AdapterSmsInfo";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	private Context mContext;
	private List<SmsContent> mData;

	public AdapterSmsInfo(Context context, List<SmsContent> data) {
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "data.size is %d", data.size());
		}
		
		this.mContext = context;
		this.mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {

		if (DEBUG) {
			MinicRPCLog.d(TAG, "getView-postion:%d", arg0);
		}
		
		ViewHolder viewHolder;
		if (view == null) {
			view = View.inflate(mContext, R.layout.activity_sms_listview_item, null);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) view.getTag();
		//
		SmsContent item = mData.get(arg0);
		String content = item.getContent();
		int hots = item.getHots();
		viewHolder.content.setText(content);
		viewHolder.hots.setText(hots+"");
		viewHolder.toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					//加入收藏
					
				} else {
					//取消收藏
					
				}
			}
		});
		
		return view;
	}

	
	static class ViewHolder {
		@InjectView(R.id.content) TextView content;
		@InjectView(R.id.hots) TextView hots;
		@InjectView(R.id.tg) ToggleButton toggleButton;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}
	
	
	
	
	
}
