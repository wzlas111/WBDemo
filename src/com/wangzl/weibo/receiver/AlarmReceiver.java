package com.wangzl.weibo.receiver;

import com.wangzl.weibo.service.TimelineService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(Intent.ACTION_RUN);
		i.setClass(context, TimelineService.class);
		context.startService(i);
	}

}
