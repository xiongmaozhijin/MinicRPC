package com.example.minicrpc;

import java.util.List;
import java.util.Map;

import com.example.minicrpc.db.UtilsDatabase;
import com.example.minicrpc.utils.MinicRPCLog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class LogoActivity extends Activity {

	private static final long DELAY_MS = 2000;
	
	private static final int ISLOADDATABASE = 0;

	private static final String TAG = "LogoActivity";

	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.arg1;
			if (flag == ISLOADDATABASE) {
				new Handler().postDelayed(new StartMainActivity(), DELAY_MS);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		
		initApp();
	}

	/**
	 * 可以做一些初始化的工作
	 * 或者做完初始化工作后跳转
	 */
	private void initApp() {
		initDatabase();
	}
	
	/**
	 * 初始化数据库数据
	 */
	private void initDatabase() {
		if (DEBUG) {
			MinicRPCLog.e(TAG, "初始化本地数据库");
		}
		new Thread(){
			@Override
			public void run() {
				
				UtilsDatabase.copyfileManager(LogoActivity.this);
				
				SharedPreferences sp = getSharedPreferences(UtilsDatabase.CONFIG, Context.MODE_PRIVATE);
				boolean iscopy = sp.getBoolean(UtilsDatabase.ISLOADTODATABASE, false);
				if (!iscopy) {
					List<Map<String,String>> data = UtilsDatabase.getLocalData(LogoActivity.this);
					//转移数据
					copyDataToDatabase(data);
					sp.edit().putBoolean(UtilsDatabase.ISLOADTODATABASE, true).commit();
				}
				
				
				Message msg = Message.obtain();
				msg.arg1 = ISLOADDATABASE;
				
				
				handler.sendMessage(msg);
			}

			/**
			 * 转移数据
			 */
			private void copyDataToDatabase(List<Map<String, String>> data) {
				UtilsDatabase.copyDataToDatabase(LogoActivity.this, data);
			}
			
		}.start();
	}

	/**
	 * 在logo activity 中不允许退出
	 */
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		return;
	}
	
	
	
	/**
	 * 跳转到主Activity
	 * @author michael.chen
	 *
	 */
	private class StartMainActivity implements Runnable {
		@Override
		public void run() {
			Intent i = new Intent();
			i.setClass(LogoActivity.this, MainActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	
}
