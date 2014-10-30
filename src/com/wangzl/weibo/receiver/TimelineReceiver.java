package com.wangzl.weibo.receiver;

import java.util.Random;

import com.wangzl.weibo.R;
import com.wangzl.weibo.util.NotificationUtil;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony.Sms.Intents;

public class TimelineReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("收到了一条信息.");
		String action = intent.getAction();
		String msg = "您有新的信息.";
		if (action.equals(Intents.SMS_RECEIVED_ACTION)) {
			System.out.println("--------------------------");
			System.out.println("收到了一条短信息.");
			msg = "您有新的短信.";
		} else if (action.equals(Intents.WAP_PUSH_RECEIVED_ACTION)) {
			System.out.println("--------------------------");
			System.out.println("收到了一条彩信息.");
			msg = "您有新的彩信.";
		}

		Notification.Builder builder = new Notification.Builder(context)
				.setTicker(msg)
				.setContentTitle("和阅读")
				.setContentTitle(msg)
				.setOnlyAlertOnce(true)
				.setAutoCancel(true)
				.setSmallIcon(R.drawable.search_user_ic).setOngoing(false);
		Notification notification = builder.getNotification();
		int n_id = new Random().nextInt(Integer.MAX_VALUE);
		NotificationUtil.show(notification, n_id);
	}

}
