package com.example.minicrpc.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.minicrpc.R;
import com.example.minicrpc.fragments.smsbless.FA;
import com.example.minicrpc.fragments.smsbless.FB;
import com.example.minicrpc.fragments.smsbless.FC;
import com.example.minicrpc.fragments.smsbless.FD;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.widget.CommonTitleBar;
import com.viewpagerindicator.TabPageIndicator;

/**
 * ¶ÌÐÅ×£¸£
 * @author michael.chen
 *
 */
public class FragmentSmsBless extends BaseFragment {
	
	private static final String TAG = "FragmentSmsBless";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	@InjectView(R.id.titlebar) CommonTitleBar mTitlebar;
	@InjectView(R.id.indicator) TabPageIndicator mIndicator;
	@InjectView(R.id.pager) ViewPager mPager;
	
	private Fragment[] mFragments = new Fragment[4];
	private MyAdapter mAdapter;
	private String[] mTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (DEBUG) {
			MinicRPCLog.d(TAG, "onCreateView");
		}
		
		View view = View.inflate(getActivity(), R.layout.fragment_smsbless, null);
		ButterKnife.inject(this, view);
		
		//init fragments
		mFragments[0] = new FA();
		mFragments[1] = new FB();
		mFragments[2] = new FC();
		mFragments[3] = new FD();
		
		//init
		mTitle = getActivity().getResources().getStringArray(R.array.sms_bless);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s, %s, %s, %s", mTitle);			
		}
		
		mAdapter = new MyAdapter(getChildFragmentManager());
		mPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mPager);
		mIndicator.setCurrentItem(0);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onActivityCreate");
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "onAttach");
		}
	}
	
	private class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments[arg0];
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mTitle[position];
		}
		
	}
	
	
}
