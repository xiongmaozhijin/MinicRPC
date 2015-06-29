package com.example.minicrpc.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.MainActivity;
import com.example.minicrpc.R;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.widget.CommonSetting;
import com.example.minicrpc.widget.CommonTitleBar;

public class FragmentMore extends BaseFragment {
	
	protected static final String TAG = "FragmentMore";

	private static final int NOTIFICATION_ID = 0;

	private static final String SETTING_CONFIG = "setting_config";

	private static final String NOTIFICATION_LONG = "notification_long";
	
	@InjectView(R.id.titlebar) CommonTitleBar mTitlebar;
	@InjectView(R.id.base_setting) CommonSetting mBaseSetting;	 
	@InjectView(R.id.function_setting) CommonSetting mFunctionSetting;

	private SharedPreferences mSp;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.activity_more, null);
		
		ButterKnife.inject(this, view);
		
		initConfig(getActivity());
		
		initListener();
		
		
		return view;
	}

	/**
	 * 初始化监听
	 */
	protected void initListener() {
		mBaseSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MinicRPCLog.d(TAG, "1");
			}
		}, 0);
		
		mBaseSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MinicRPCLog.d(TAG, "2");
			}
		}, 1);
		
		mBaseSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MinicRPCLog.d(TAG, "%d, %s", 3, "togglebutton");
				if (v instanceof CompoundButton) {
					boolean checked = ((CompoundButton)v).isChecked();
					MinicRPCLog.d(TAG, "%s", checked+"");
				}
			}
		}, 2);
		
		
		//通知栏常驻开关
		mFunctionSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (v instanceof CompoundButton) {
					boolean b = ((CompoundButton)v).isChecked();
					mSp.edit().putBoolean(NOTIFICATION_LONG, b).commit();
					if (!b) {
						cancelNotification(getActivity(), NOTIFICATION_ID);
					} else {
						createNotification(getActivity());
					}
				}
			}
		}, 0);
	}
	
	/**
	 * 配置信息
	 */
	private void initConfig(Context context) {
		mSp = context.getSharedPreferences(SETTING_CONFIG, Context.MODE_PRIVATE);
		boolean b1 = mSp.getBoolean(NOTIFICATION_LONG, false);
		mFunctionSetting.getTgBtn(0).setChecked(b1);
		
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}
	
	
	
	/**
	 * 添加常驻通知
	 * @param context
	 */
	private void createNotification(Context context) {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent resultIntent = new Intent(context, MainActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(context, 0, resultIntent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
												.setContentTitle(context.getResources().getString(R.string.notification_title))
												.setContentText(context.getResources().getString(R.string.notification_text))
												.setSmallIcon(R.drawable.birthaft)
												.setOngoing(true)
												.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
												.setContentIntent(pi);
		
		manager.notify(NOTIFICATION_ID, builder.build());
	}
	
	/**
	 * 取消常驻通知栏
	 * @param context
	 * @param notifiId
	 */
	private void cancelNotification(Context context, int notifiId) {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(notifiId);
	}
	
	
}
