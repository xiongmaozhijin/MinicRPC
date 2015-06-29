package com.example.minicrpc;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.db.DBManager;
import com.example.minicrpc.entity.LuckyPeople;
import com.example.minicrpc.fragments.FragmentBirthday;
import com.example.minicrpc.utils.MinicUtils;
import com.example.minicrpc.widget.CommonTitleBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BirthdayInfo extends Activity {

	private static final String TAG = "BirthdayInfo";
	private DBManager mDBManager;
	private ImageLoader mImageLoader;
	private long mKeyId = -1;
	
	public static final String UPDATE = "update";
	
	@InjectView(R.id.titlebar) CommonTitleBar mTitlebar;
	@InjectView(R.id.portrait) ImageView mPortrait;
	@InjectView(R.id.name) TextView mName;
	@InjectView(R.id.birthday_date) TextView mBirthdayDate;
	@InjectView(R.id.birthday_hint) TextView mBirthdayHint;
	@InjectView(R.id.birth_leave_hint) TextView mBirthLeaveHint;
	@InjectView(R.id.next_birthday_date) TextView mNextBirthDate;
	@InjectView(R.id.today_lucky) RadioButton mTodayLucky;
	@InjectView(R.id.sms_bless) RadioButton mSmsBless;
	@InjectView(R.id.tel_bless) RadioButton mTelBless;
	
	private DisplayImageOptions mDisplayImageOptions;
	private LuckyDog mLuckyDog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_birthday_info);
		
		ButterKnife.inject(this);
		
		App app = (App) getApplication();
		
		mImageLoader = app.getImageLoader();
		mDBManager = DBManager.getInstance(this);
		
		setupInfo();
	}
	
	
	private void setupInfo() {
		long id = getIntent().getLongExtra(FragmentBirthday.ENTITY_ID, -1);
		if (id != -1) {
			LuckyDog luckyDog = mDBManager.loadByKey(id);
			mLuckyDog = luckyDog;
			
			mKeyId = id;
			if (luckyDog != null) {
				if (luckyDog.getSex() == LuckyPeople.MAN) {
					mDisplayImageOptions = new DisplayImageOptions.Builder()
					.showImageOnFail(R.drawable.defaultboy)
					.showImageOnLoading(R.drawable.defaultboy)
					.cacheInMemory(true)
					.cacheOnDisk(false)
					.build();
				} else {
					mDisplayImageOptions = new DisplayImageOptions.Builder()
					.showImageOnFail(R.drawable.defaultgirl)
					.showImageOnLoading(R.drawable.defaultgirl)
					.cacheInMemory(true)
					.cacheOnDisk(false)
					.build();
				}
				mImageLoader.displayImage(MinicUtils.wrapperToImageload(luckyDog.getPortraitPath()), mPortrait, mDisplayImageOptions);
				mName.setText(luckyDog.getName());
				SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.birthday_info_next_birthday_date), Locale.getDefault());
				String date = sdf.format(luckyDog.getBirthday());
				mBirthdayDate.setText(date);
				mBirthdayHint.setText(MinicUtils.getStringBirthdayHint(luckyDog, this));
				mBirthLeaveHint.setText(MinicUtils.calculateBetweenDays(luckyDog.getBirthday(), this));
				mNextBirthDate.setText(MinicUtils.getThisYearBirthDateString(luckyDog.getBirthday()));
				
			} //luckyDog is not null
		} else {
			Toast.makeText(this, getResources().getString(R.string.birthday_info_load_err), Toast.LENGTH_SHORT).show();
		}
		
		mTitlebar.setOnLeftBtnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}});
		mTitlebar.setOnRightBtnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(UPDATE, true);
				i.putExtra(FragmentBirthday.ENTITY_ID, mKeyId);
				i.setClass(BirthdayInfo.this, AddLuckyDog.class);
				startActivity(i);
				
			}});
	}


	@Override
	protected void onResume() {
		super.onResume();
		
		
	}
	
	
	@OnClick(R.id.birthday_hint)
	public void birthdayHint() {
		
	}
	
	
	@OnClick(R.id.today_lucky)
	public void todayLucky() {
		
	}
	
	@OnClick(R.id.sms_bless)
	public void smsBless() {
		Intent i = new Intent();
		i.setClass(this, SmsBless.class);
		i.putExtra(FragmentBirthday.ENTITY_ID, mKeyId);
		startActivity(i);
	}
	
	@OnClick(R.id.tel_bless)
	public void telBless() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.parse("tel:"+mLuckyDog.getTel());
		i.setData(uri);
		startActivity(i);
	}
	

}
