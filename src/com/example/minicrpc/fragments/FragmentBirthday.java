package com.example.minicrpc.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.AddLuckyDog;
import com.example.minicrpc.BirthdayInfo;
import com.example.minicrpc.R;
import com.example.minicrpc.adapter.AdapterBirthday;
import com.example.minicrpc.entity.LuckyPeople;
import com.example.minicrpc.utils.MinicBiz;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.widget.CommonTitleBar;

public class FragmentBirthday extends BaseFragment implements View.OnClickListener {
	
	private static final String TAG = "FragmentBirthday";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	public static final String ENTITY_ID = "database item id";
	
	@InjectView(R.id.f_birthday_titlebar) CommonTitleBar mTitleBar;
	@InjectView(R.id.f_birthday_listview) ListView mListview;
	
	private AdapterBirthday mAdapter;
	
	/**
	 * 编辑按钮
	 */
	private Button mEditBtn;
	
	/**
	 * 增加按钮
	 */
	private Button mAddBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.fragment_birthday, null);
		ButterKnife.inject(this, v);
		
		//init widget
		mEditBtn = (Button) mTitleBar.findViewById(R.id.btn_left);
		mAddBtn = (Button) mTitleBar.findViewById(R.id.btn_right);
		mEditBtn.setOnClickListener(this);
		mAddBtn.setOnClickListener(this);
		
		List<LuckyPeople> data = MinicBiz.getAllData(getActivity());
		mAdapter = new AdapterBirthday(data, getActivity());
		
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LuckyPeople luckypeople = (LuckyPeople) mAdapter.getItem(position);
				long dbId = luckypeople.getId();
				Intent i = new Intent();
				i.putExtra(ENTITY_ID, dbId);
				i.setClass(getActivity(), BirthdayInfo.class);
				startActivity(i);
			}
			
		});
		
		
		return v;
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onActivityCreated");
		}
		
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onAttach");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onDetach");
		}
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (DEBUG) {
			MinicRPCLog.e(TAG, "onResume");
		}
		mAdapter.updateData();
	}
	

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_left) {
			MinicRPCLog.d(TAG, "", "bianji");
			if (mAdapter.getEditable()) {
				mAdapter.setEditable(false);
				mEditBtn.setText(getResources().getString(R.string.f_birthdary_edit));
			} else {
				mAdapter.setEditable(true);
				mEditBtn.setText(getResources().getString(R.string.f_birthdary_finish));
			}
			
			
		} else if (v.getId() == R.id.btn_right) {
			MinicRPCLog.d(TAG, "%s", "add");
			Intent i = new Intent();
			i.setClass(getActivity(), AddLuckyDog.class);
			startActivity(i);
		}
	}
	
}
