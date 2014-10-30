package com.wangzl.weibo.util;

import com.wangzl.weibo.AppContext;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class NotificationUtil {

	public static void show(Notification notification,int id) {
		NotificationManager notificationManager = (NotificationManager)AppContext.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(id, notification);
	}
	
	public static void cancel(int id) {
		NotificationManager notificationManager = (NotificationManager)AppContext.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(id);
	}
}
