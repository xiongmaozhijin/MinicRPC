package com.example.minicrpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.minicrpc.fragments.FragmentBirthday;
import com.example.minicrpc.fragments.FragmentMore;
import com.example.minicrpc.fragments.FragmentMyself;
import com.example.minicrpc.fragments.FragmentSmsBless;
import com.example.minicrpc.utils.MinicRPCLog;

/**
 * 生日管家主界面
 * @author michael.chen
 *
 */
public class MainActivity extends BaseActivity {
	
	@InjectView(R.id.container) FrameLayout mContainer;
	@InjectView(R.id.rb_birthday) RadioButton mBtnBirthday;
	@InjectView(R.id.rb_sms_bless) RadioButton mBtnSmsBless;
	@InjectView(R.id.rb_myself) RadioButton mBtnMyself;
	@InjectView(R.id.rb_more) RadioButton mBtnMore;

	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	private static final String TAG = "MainActivity";
	
	private Fragment mFragmentBirthday;
	private Fragment mFragmentSmsBless;
	private Fragment mFragmentMyself;
	private Fragment mFragmentMore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ButterKnife.inject(this);
		
		mFragmentBirthday = new FragmentBirthday();
		mFragmentSmsBless = new FragmentSmsBless();
		mFragmentMyself = new FragmentMyself();
		mFragmentMore = new FragmentMore();
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragmentBirthday).commit();
	}

	
	@OnClick(R.id.rb_birthday)
	public void onBirthday() {
		if (DEBUG) {
			MinicRPCLog.i(TAG, "%s", "onBirthday");
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragmentBirthday).commit();
	}
	
	@OnClick(R.id.rb_sms_bless)
	public void onSmsBless() {
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragmentSmsBless).commit();
	}
	
	@OnClick(R.id.rb_myself)
	public void onMyself() {
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragmentMyself).commit();
	}
	
	@OnClick(R.id.rb_more) 
	public void onMore() {
		if (DEBUG) {
			MinicRPCLog.i(TAG, "%s", "onMore");
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragmentMore).commit();
	}
	
	
	
	
}
