package com.example.minicrpc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.databasedao.SmsContent;
import com.example.minicrpc.db.DBManager;
import com.example.minicrpc.db.SmsDBManager;
import com.example.minicrpc.entity.ContactPerson;
import com.example.minicrpc.fragments.FragmentBirthday;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.utils.MinicUtils;
import com.example.minicrpc.widget.CommonTitleBar;

/**
 * 短信祝福
 * @author michael.chen
 *
 */
public class SmsBless extends Activity {

	private static final String TAG = "SmsBless";

	private static final int REQUEST_NAME_ACTION = 0;

	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	@InjectView(R.id.tel_number) EditText mTelNumber;
	@InjectView(R.id.phone_action) ImageButton mPhoneAction;
	@InjectView(R.id.message) EditText mMessage;
	@InjectView(R.id.btn_best_message) TextView mBestMessage;
	@InjectView(R.id.btn_favorites) TextView mFavorites;
	@InjectView(R.id.btn_ontime_flag) ToggleButton mOntimeFlag;
	@InjectView(R.id.txv_send_date) TextView mSendDate;
	@InjectView(R.id.imgbtn_send_date_action) ImageButton mSendDateAction;
	@InjectView(R.id.txv_send_time) EditText mSendTime;
	@InjectView(R.id.imgbtn_send_time_action) ImageButton mSendTimeAction;
	@InjectView(R.id.save) Button mBtnSave;
	@InjectView(R.id.ll_ontime_send_msg_content) View mLLOntimeSendMsg;
	@InjectView(R.id.titlebar) CommonTitleBar mTitlebar;
	
	private App mApp;
	private DBManager mDBManager;
	private SmsDBManager mSmsDBManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsbless);
		
		ButterKnife.inject(this);
		
		mApp = (App) getApplication();
		mDBManager = DBManager.getInstance(this);
		mSmsDBManager = SmsDBManager.getInstance(this);
		
		setupWidget();
		setupInfo();
	}

	private void setupInfo() {
		Intent i = getIntent();
		if (i!=null) {
			//from person birthday item
			long keyid = i.getLongExtra(FragmentBirthday.ENTITY_ID, -1);
			if (keyid != -1) {
				LuckyDog luckyDog = mDBManager.loadByKey(keyid);
				if (luckyDog != null) {
					mTelNumber.setText(luckyDog.getTel());
					mSendDate.setText(MinicUtils.getDate(luckyDog.getBirthday(), getResources().getString(R.string.smsbless_send_date_content), getResources().getString(R.string.dateformat)));
				}
			}
			//from sms content send
			boolean fromSmsInfo = i.getBooleanExtra(SmsInfo.FROM_SMS_INFO, false);
			if (fromSmsInfo) {
				long smsContentId = i.getLongExtra(SmsInfo.SMS_CONTENT_ID, -1);
				if (smsContentId != -1) {
					SmsContent item = mSmsDBManager.loadContent(smsContentId);
					if (item != null) {
						mMessage.setText(item.getContent());
					}
				}
			}
			
		}
	}

	private void setupWidget() {
		mTitlebar.setOnLeftBtnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mOntimeFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mLLOntimeSendMsg.setVisibility(View.VISIBLE);
					mBtnSave.setText(getResources().getString(R.string.smsbless_send_time_save));
				} else {
					mLLOntimeSendMsg.setVisibility(View.GONE);
					mBtnSave.setText(getResources().getString(R.string.send));
				}
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			
			switch (requestCode) {
			case REQUEST_NAME_ACTION:
				Uri uri = data.getData();
				ContactPerson cp = MinicUtils.getContactPersonDetail(uri, this);
				if (DEBUG) {
					MinicRPCLog.d(TAG, cp.toString());
				}
				setupContactInfo(cp);
				break;
			}
			
		}
		
	}
	
	
	
	/**
	 * 适配联系人信息
	 * @param cp
	 */
	private void setupContactInfo(ContactPerson cp) {
		mTelNumber.setText(cp.getAllphones().get(0));
	}

	@OnClick(R.id.phone_action)
	public void phoneAction() {
		// 启动通讯录
		Intent i = new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(i, REQUEST_NAME_ACTION);
	}
	
	
	@OnClick(R.id.save)
	public void sendMsg() {
		if (mOntimeFlag.isChecked()) {
			//保存发送短信
			MinicRPCLog.e(TAG, "%s", "保存发送短信");
		} else {
			//发送短信
			MinicRPCLog.e(TAG, "%s", "发送短信");
		}
		
		Toast.makeText(this, getResources().getString(R.string.smsbless_send_sms_hint), Toast.LENGTH_SHORT).show();
	}
	

}









