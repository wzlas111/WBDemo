package com.wangzl.weibo.ui.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
	
	protected NotificationManager notificationManager;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		notificationManager = (NotificationManager)(getActivity().getSystemService(Context.NOTIFICATION_SERVICE));
	}
}
