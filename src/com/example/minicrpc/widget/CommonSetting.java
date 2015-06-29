package com.example.minicrpc.widget;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.minicrpc.R;
import com.example.minicrpc.utils.MinicRPCLog;

/**
 * 设置通用组件
 * @author michael.chen
 *
 */
public class CommonSetting extends LinearLayout {

	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	private static final String TAG = "CommonSetting";
	private static final int TEXT_HINT = 0;
	private static final int TOGGLE_BUTTON = 1;
	
	/** 图标提示项的文件名，存放在asserts文件夹中 */
	private String[] mFirstIconFilename;
	
	/** 设置项的子标题 */
	private String[] mSubTitles;
	
	/** 提示项的类型，是文本还是开关，0表示文本提示，1表示开关操作  */
	private int[] mHintType;
	
	/** 如果是文本项，则表示的是提示项的文本信息 */
	private String[] mHintStr;
	
	/** 设置项的主标题 */
	private String mTitle;
	
	/**
	 * 存放togglebutton
	 */
	private List<ToggleButton> mListToggleButton = new ArrayList<ToggleButton>();
	

	public CommonSetting(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "CommonSetting-3");
		}
		
		
		
	}
	
	
	
	public CommonSetting(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "CommonSetting-2");
		}
		
		initValue(context, attrs);
		initValueTest();
		loadViewandInite(context);
	}




	public CommonSetting(Context context) {
		super(context);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "CommonSetting-1");
		}
	}




	/**
	 * 测试是否获得到了数据
	 */
	private void initValueTest() {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "mTitle:%s; mFirstIconId:%s; mHintStr:%s", mTitle, mFirstIconFilename.toString(), mHintStr.toString());
		}
	}

	/**
	 * 加载组件View和初始化组件
	 */
	private void loadViewandInite(Context context) {
		
		View view0 = View.inflate(getContext(), R.layout.widget_common_setting, this);
		TextView title = (TextView) view0.findViewById(R.id.title);
		LinearLayout settingContent = (LinearLayout) view0.findViewById(R.id.setting_content);
		
		// adapter widget
		if (mTitle != null) {
			title.setText(mTitle);
		}
		
		
		for (int i=0; i<mHintStr.length; i++) {
			//init view item
			View view = View.inflate(getContext(), R.layout.widget_common_setting_item, null);
			LinearLayout llItem = (LinearLayout) view.findViewById(R.id.item);
			ImageView hintIcon = (ImageView) view.findViewById(R.id.first_hint_icon);
			TextView subname = (TextView) view.findViewById(R.id.subsetting_name);
			TextView hintStr = (TextView) view.findViewById(R.id.hint_string);
			ToggleButton toggleBtn = (ToggleButton) view.findViewById(R.id.toggle_btn);
			ImageView arrow = (ImageView) view.findViewById(R.id.arrow);
			
			//分割线
			View view1 = View.inflate(getContext(), R.layout.widget_common_setting_split, null);
			ImageView split = (ImageView) view1.findViewById(R.id.split);
			
			AssetManager am = context.getAssets();
			String fileName = mFirstIconFilename[i];
			try {
				if (DEBUG) {
					MinicRPCLog.d(TAG, "filename: %s", fileName);
				}
				
				InputStream is = am.open(fileName);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				if (bitmap != null) {
					hintIcon.setImageBitmap(bitmap);	
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				MinicRPCLog.e(TAG, "error : %s", e.toString());
			}
		
			String subtitle = mSubTitles[i];
			subname.setText(subtitle);
			
			int type = mHintType[i];
			
			if (DEBUG) {
				MinicRPCLog.d(TAG, "type:%d", type);
			}
			
			if (type == TEXT_HINT) {	//wen ben
				llItem.setClickable(true);
				toggleBtn.setVisibility(View.GONE);
				hintStr.setVisibility(View.VISIBLE);
				arrow.setVisibility(View.VISIBLE);
				String hinttext = mHintStr[i];
				hintStr.setText(hinttext);
				mListenerView.add(llItem);
			} else if (type == TOGGLE_BUTTON) {	//toggle button
				llItem.setClickable(false);
				hintStr.setVisibility(View.GONE);
				arrow.setVisibility(View.GONE);
				toggleBtn.setVisibility(View.VISIBLE);
				mListenerView.add(toggleBtn);
				mListToggleButton.add(toggleBtn);
			}

			settingContent.addView(llItem);
			
			if (i != mHintStr.length-1) {
				settingContent.addView(view1);
			}
			
		} //end for 
		
		
		
	}

	
	
	
	/**
	 * 获取自定义的值
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	private void initValue(Context context, AttributeSet attrs) {
		TypedArray ta = null;
		try {
			ta = context.obtainStyledAttributes(attrs, R.styleable.CommonSetting);
			int fhiaRefId = ta.getResourceId(R.styleable.CommonSetting_first_hint_icons_arrays, -1);
			int subTitlesRefId = ta.getResourceId(R.styleable.CommonSetting_subsettings_names_arrays, -1);
			int hintTypeRefId = ta.getResourceId(R.styleable.CommonSetting_hint_type_arrays, -1);
			int hintStrRefId = ta.getResourceId(R.styleable.CommonSetting_hint_string_arrays, -1);
			
			mTitle = ta.getString(R.styleable.CommonSetting_setting_title);
			
			if (fhiaRefId != -1) {
				mFirstIconFilename = context.getResources().getStringArray(fhiaRefId);
			}
			if (subTitlesRefId != -1) {
				mSubTitles = context.getResources().getStringArray(subTitlesRefId);
				
			}
			if (hintTypeRefId != -1) {
				mHintType = context.getResources().getIntArray(hintTypeRefId);
			}
			if (hintStrRefId != -1) {
				mHintStr = context.getResources().getStringArray(hintStrRefId);
			}
			
		} finally {
			if (ta != null) {
				ta.recycle();
			}
		}
	
	}
	
	
	/**
	 * 返回togglebutton
	 * @param index，按顺序添加的序号
	 * @return
	 */
	public ToggleButton getTgBtn(int index) {
		return mListToggleButton.get(index);
		
	}
	
	
	
	
	private List<View> mListenerView = new ArrayList<View>();
	
	/**
	 * 要注意控件排的顺序
	 * @param listener
	 * @param index
	 */
	public void setOnClickListener(View.OnClickListener listener, int index) {
		mListenerView.get(index).setOnClickListener(listener);
	} 
	

}
