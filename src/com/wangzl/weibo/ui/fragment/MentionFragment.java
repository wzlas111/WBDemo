package com.wangzl.weibo.ui.fragment;

import java.util.ArrayList;

import com.wangzl.common.widget.pulltorefresh.XListView;
import com.wangzl.common.widget.pulltorefresh.XListView.IXListViewListener;
import com.wangzl.weibo.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MentionFragment extends BaseFragment implements IXListViewListener{

	private XListView xListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	
	private int start = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mention, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getItems();
		
		xListView = (XListView)view.findViewById(R.id.xlistview);
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(this);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		xListView.setAdapter(mAdapter);
		
		mHandler = new Handler();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void getItems() {
		for (int i = 0; i < 20; i++) {
			items.add("item_"+i);
		}
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start++;
				items.clear();
				items.add("add_item_"+start);
				getItems();
				mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				xListView.setAdapter(mAdapter);
				
				xListView.stopRefresh();
				xListView.stopLoadMore();
			}
		}, 2 * 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getItems();
				mAdapter.notifyDataSetChanged();
				
				xListView.stopRefresh();
				xListView.stopLoadMore();
			}
		}, 2 * 1000);
	}
}
