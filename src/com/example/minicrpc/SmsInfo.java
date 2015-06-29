package com.example.minicrpc;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.adapter.AdapterSmsInfo;
import com.example.minicrpc.databasedao.SmsContent;
import com.example.minicrpc.db.SmsDBManager;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.widget.CommonTitleBar;

/**
 * 显示子类别的短信列表信息
 * @author Administrator
 *
 */
public class SmsInfo extends Activity {

	private AdapterSmsInfo mAdapter;
	private List<SmsContent> mData;
	
	public static final String CATEGORY_ID = "category_id";
	public static final String POSITION = "position";
	private static final String TAG = "SmsInfo";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	/**
	 * 携带信息到SmsBless的key, boolean
	 */
	public static final String FROM_SMS_INFO = "smsinfo_to_send_msg";
	/**
	 * 携带信息到SmsBless的短信key id, long
	 */
	public static final String SMS_CONTENT_ID = "smsinfo_sms_content_id";
	
	@InjectView(R.id.titlebar) CommonTitleBar mTitlebar;
	@InjectView(R.id.listview) ListView mListview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		
		ButterKnife.inject(this);
		
		
		initData();
		mAdapter = new AdapterSmsInfo(this, mData);
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SmsContent item = mData.get(position);
				long itemId = item.getId();
				Intent i = new Intent();
				i.setClass(SmsInfo.this, SmsBless.class);
				i.putExtra(FROM_SMS_INFO, true);
				i.putExtra(SMS_CONTENT_ID, itemId);
				startActivity(i);
			}
			
		});
		
		
		mTitlebar.setOnLeftBtnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		
	}

	/**
	 * 获取加载数据
	 * @param mData2
	 */
	private void initData() {
		Intent i = getIntent();
		if (i != null) {
			int categoryId = i.getIntExtra(CATEGORY_ID, -1);
			int position = i.getIntExtra(POSITION, -1);
			if ((categoryId!=-1) && (position!=-1)) {
				mData = SmsDBManager.getInstance(this).querySmsContent(categoryId, position); 
				if (DEBUG) {
					MinicRPCLog.d(TAG, "categoryId=%d, position=%d, data.size=%d", categoryId, position, mData.size());
				}
			}
		}
	}
	
	
	
	
	
	

}
