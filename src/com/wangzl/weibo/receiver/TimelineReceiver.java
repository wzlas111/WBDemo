package com.wangzl.weibo.receiver;

import java.util.Random;

import com.wangzl.weibo.R;
import com.wangzl.weibo.ui.MainActivity;
import com.wangzl.weibo.util.NotificationUtil;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Telephony.Sms.Intents;
import android.util.Log;

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
			
			try {
				Object pdus = intent.getExtras().get("pdus");
				if(pdus == null){
					System.out.println("TimelineReceiver ==> sms : null");
				}else {
					System.out.println("TimelineReceiver ==> sms : data");
				}
			} catch (Exception e) {
				System.out.println("TimelineReceiver ==> sms : error");
			}
			
		} else if (action.equals(Intents.WAP_PUSH_RECEIVED_ACTION)) {
			System.out.println("--------------------------");
			System.out.println("收到了一条彩信息.");
			msg = "您有新的彩信.";
			
			try {
				String mimeType = intent.getType();
				System.out.println("TimelineReceiver ==> mimeType : "+mimeType);
				byte[] pduByte = intent.getByteArrayExtra("data");
				if(pduByte == null){
					System.out.println("TimelineReceiver ==> mms : null");
				}else {
					System.out.println("TimelineReceiver ==> mms : data");
				}
			} catch (Exception e) {
				System.out.println("TimelineReceiver ==> mms : error");
			}
			
		}

		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.setClass(context, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
		Notification.Builder builder = new Notification.Builder(context)
				.setTicker(msg)
				.setContentTitle("和阅读")
				.setContentText(msg)
				.setOnlyAlertOnce(true)
				.setAutoCancel(true)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
				.setSmallIcon(R.drawable.icon)
				.setOngoing(false)
				.setContentIntent(pendingIntent);
		Notification notification = builder.getNotification();
		int n_id = new Random().nextInt(Integer.MAX_VALUE);
		NotificationUtil.show(notification, n_id);
	}

}
