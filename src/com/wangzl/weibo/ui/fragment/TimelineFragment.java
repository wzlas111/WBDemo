package com.wangzl.weibo.ui.fragment;

import com.wangzl.weibo.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("TimelineFragment onCreateView");
		return inflater.inflate(R.layout.fragment_timeline, null);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("TimelineFragment onResume");
	}
}
