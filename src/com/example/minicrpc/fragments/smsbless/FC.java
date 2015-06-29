package com.example.minicrpc.fragments.smsbless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class FC extends Fragment {
	
	private GridViewAdapter mAdapter;
	private List<Map<String, String>> mData = new ArrayList<Map<String,String>>();
	private String[] CATEGOREYS;
	
	private static final int[] YUYANWENZI = new int[]{
		R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, 
		R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.c11, R.drawable.c12,
		R.drawable.c13, R.drawable.c14, R.drawable.c15
	};
	
	@InjectView(R.id.gridview) GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
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
				i.putExtra(SmsInfo.CATEGORY_ID, 3);
				i.putExtra(SmsInfo.POSITION, position);
				i.setClass(getActivity(), SmsInfo.class);
				startActivity(i);
			}});
		
		return view;
		
	}
	
	private void initData(List<Map<String, String>> data) {
		data.clear();
		CATEGOREYS = getResources().getStringArray(R.array.fenggeleixing);
		for (int i=0; i<YUYANWENZI.length; i++) {
			Map<String, String> item = new HashMap<String, String>();
			item.put(GridViewAdapter.IMAGEVIEW, YUYANWENZI[i]+"");
			item.put(GridViewAdapter.CATEGORY, CATEGOREYS[i]);
			data.add(item);
		}
		
		
	}
	
}
