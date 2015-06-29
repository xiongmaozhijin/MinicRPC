package com.example.minicrpc.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.App;
import com.example.minicrpc.R;
import com.example.minicrpc.db.DBManager;
import com.example.minicrpc.entity.LuckyPeople;
import com.example.minicrpc.utils.MinicBiz;
import com.example.minicrpc.utils.MinicUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 生日界面，listview的适配器
 * @author michael.chen
 *
 */
public class AdapterBirthday extends BaseAdapter {

	/**
	 * 是否处理可以编辑的状态，即是否可以删除
	 */
	private boolean isEditable = false;
	
	/** 所有的数据 */
	private List<LuckyPeople> mData;
	private Context mContext;
	
	private static final int MAN = 0;
	
	private ImageLoader mImageLoader;
	
	/**
	 * 图片加载参数
	 */
	private DisplayImageOptions mDisplayImageOptions;
	
	public AdapterBirthday(List<LuckyPeople> data, Context context) {
		mData = data;
		mContext = context;
		
		App app = (App) mContext.getApplicationContext();
		mImageLoader = app.getImageLoader();
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
	
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
		notifyDataSetChanged();
	}
	/**
	 * 返回是否可以编辑状态
	 * @return
	 */
	public boolean getEditable() {
		return isEditable;
	}
	
	/**
	 * 更新数据
	 */
	public void updateData() {
		mData = MinicBiz.getAllData(mContext);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder;
		if (view != null) {
			viewHolder = (ViewHolder) view.getTag();
		} else {
			view = View.inflate(mContext, R.layout.f_birthday_listview_item, null);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		LuckyPeople luckyPeople = mData.get(position);
		//从path中加载图片，这里先省略
		if (luckyPeople.getSex() == MAN) {
			viewHolder.timeHint.setText(mContext.getResources().getString(R.string.birthday_time_hint_man));
			mDisplayImageOptions = new DisplayImageOptions.Builder()
			.showImageOnFail(R.drawable.defaultboy)
			.showImageOnLoading(R.drawable.defaultboy)
			.cacheInMemory(true)
			.cacheOnDisk(false)
			.build();
		} else {
			viewHolder.timeHint.setText(mContext.getResources().getString(R.string.birthday_time_hint_feman));
			mDisplayImageOptions = new DisplayImageOptions.Builder()
			.showImageOnFail(R.drawable.defaultgirl)
			.showImageOnLoading(R.drawable.defaultgirl)
			.cacheInMemory(true)
			.cacheOnDisk(false)
			.build();
		}
		mImageLoader.displayImage(MinicUtils.wrapperToImageload(luckyPeople.getPortraitPath()), viewHolder.photo, mDisplayImageOptions);
		
		viewHolder.name.setText(luckyPeople.getName());
		//下面两项都是要进一步计算的
		Date date = luckyPeople.getBirthdayDate();
		int days = MinicUtils.calculateBetweenDays(date);
		SimpleDateFormat sd = new SimpleDateFormat(mContext.getResources().getString(R.string.birthday_format), Locale.getDefault());
		viewHolder.birthday.setText(sd.format(date));
		viewHolder.days.setText(days+"");
		
		if (days == 0) {	//生日当天
			viewHolder.timeHint.setText(mContext.getResources().getString(R.string.birthday_time_hint_hit));
			viewHolder.birthdayShowIcon.setVisibility(View.VISIBLE);
			viewHolder.days.setVisibility(View.INVISIBLE);
			viewHolder.daysChar.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.birthdayShowIcon.setVisibility(View.INVISIBLE);
			viewHolder.days.setVisibility(View.VISIBLE);
			viewHolder.daysChar.setVisibility(View.VISIBLE);
		}
		
		//处理删除按钮
		if (isEditable) {
			viewHolder.btnDelete.setVisibility(View.VISIBLE);
			viewHolder.btnDelete.setOnClickListener(new OnDeleteListerner(this, luckyPeople, mData));
		} else {
			viewHolder.btnDelete.setVisibility(View.INVISIBLE);
		}
		
		return view;
	}

	static class ViewHolder {
		@InjectView(R.id.photo) ImageView photo;
		@InjectView(R.id.name) TextView name;
		@InjectView(R.id.solor_icon) ImageView solorIcon;
		@InjectView(R.id.birthday) TextView birthday;
		@InjectView(R.id.time_hint) TextView timeHint;
		@InjectView(R.id.days) TextView days;
		@InjectView(R.id.days_char) TextView daysChar;
		@InjectView(R.id.birthday_show_icon) ImageView birthdayShowIcon;
		@InjectView(R.id.delete) Button btnDelete;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}
	
	/**
	 * 删除一项纪录
	 * @author michael.chen
	 *
	 */
	private class OnDeleteListerner implements View.OnClickListener {

		private List<LuckyPeople> listdata;
		private LuckyPeople luckyPeople;
		private BaseAdapter adapter;

		public OnDeleteListerner(final BaseAdapter adapter, LuckyPeople luckyPeople, List<LuckyPeople> listdata) {
			
			this.adapter = adapter;
			this.luckyPeople = luckyPeople;
			this.listdata = listdata;
		} 
		
		@Override
		public void onClick(View v) {
			listdata.remove(luckyPeople);
			DBManager.getInstance(mContext).deleteLuckDog(luckyPeople.getId());
			adapter.notifyDataSetChanged();
		}
		
	}
	
	
}
