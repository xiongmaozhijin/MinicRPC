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

public class FD extends Fragment {
	
	private GridViewAdapter mAdapter;
	private List<Map<String, String>> mData = new ArrayList<Map<String,String>>();
	private String[] CATEGOREYS;
	
	private static final int[] YUYANWENZI = new int[]{
		R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6, 
		R.drawable.d7, R.drawable.d8, R.drawable.d9, R.drawable.d10, R.drawable.d11, R.drawable.d12, 
		R.drawable.d13, R.drawable.d14, R.drawable.d15, R.drawable.d16
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
				i.putExtra(SmsInfo.CATEGORY_ID, 4);
				i.putExtra(SmsInfo.POSITION, position);
				i.setClass(getActivity(), SmsInfo.class);
				startActivity(i);
			}});
		
		return view;
		
	}
	
	private void initData(List<Map<String, String>> data) {
		data.clear();
		CATEGOREYS = getResources().getStringArray(R.array.jierizhufu);
		for (int i=0; i<YUYANWENZI.length; i++) {
			Map<String, String> item = new HashMap<String, String>();
			item.put(GridViewAdapter.IMAGEVIEW, YUYANWENZI[i]+"");
			item.put(GridViewAdapter.CATEGORY, CATEGOREYS[i]);
			data.add(item);
		}
		
		
	}
	
	
}
