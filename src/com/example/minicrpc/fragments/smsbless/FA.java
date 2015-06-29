package com.example.minicrpc.fragments.smsbless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.R;
import com.example.minicrpc.SmsInfo;
import com.example.minicrpc.utils.MinicRPCLog;

/**
 * Ç×ÅóºÃÓÑ
 * @author michael.chen
 *
 */
public class FA extends Fragment {
	
	
	
	private static final String TAG = "FA";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;

	private GridViewAdapter mAdapter;
	private List<Map<String, String>> mData = new ArrayList<Map<String,String>>();
	
	private final static int[] QINGPENGHAOYOU = new int[]{
		R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
		R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12,
		R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18
	};
	private static String[] CATEGORYS;
	
	@InjectView(R.id.gridview) GridView mGridView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onCreateView");
		}
		
		View view = View.inflate(getActivity(), R.layout.fragment_sms, null);
		ButterKnife.inject(this, view);
		initData(mData);
		mAdapter = new GridViewAdapter(getActivity(), mData);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
				Intent i = new Intent();
				i.putExtra(SmsInfo.CATEGORY_ID, 1);
				i.putExtra(SmsInfo.POSITION, position);
				i.setClass(getActivity(), SmsInfo.class);
				startActivity(i);
			}});
		
		return view;
	}
	
	private void initData(List<Map<String, String>> data) {
		CATEGORYS = getResources().getStringArray(R.array.qingpenghaoyou);
		data.clear();
		for (int i=0; i<QINGPENGHAOYOU.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(GridViewAdapter.IMAGEVIEW, QINGPENGHAOYOU[i]+"");
			map.put(GridViewAdapter.CATEGORY, CATEGORYS[i]);
			data.add(map);
		} 
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onActivityCreated");
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onAttach");
		}
	}
	
}
