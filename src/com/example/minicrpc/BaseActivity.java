package com.example.minicrpc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class BaseActivity extends FragmentActivity {
	
	private long mEndTime = 0;
	private static final long DURATION_MS = 2000;
	private Toast mToastQuit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mToastQuit = Toast.makeText(this, getResources().getString(R.string.baseactivity_quit_hint), Toast.LENGTH_SHORT);
	}
	

	@Override
	public void onBackPressed() {
		//再按一次退出实现
		if (System.currentTimeMillis() - mEndTime >= DURATION_MS) {
			mEndTime = System.currentTimeMillis();
			mToastQuit.show();
		} else {
			mToastQuit.cancel();
			finish();
		}
	}
	
	
}
