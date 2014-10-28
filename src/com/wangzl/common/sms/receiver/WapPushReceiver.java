package com.wangzl.common.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony.Sms.Intents;

public class WapPushReceiver extends BroadcastReceiver {
	
	public static final String SMS_RECEIVE_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	public static final String WAP_PUSH_RECEIVE_ACTION = "android.provider.Telephony.WAP_PUSH_RECEIVED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intents.WAP_PUSH_RECEIVED_ACTION)) {
			try {
				System.out.println("==============>");
				System.out.println("WAP_PUSH_RECEIVE_ACTION");
//				PduParser parser = new PduParser(intent.getByteArrayExtra("data"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals(SMS_RECEIVE_ACTION)) {
			System.out.println("==============>");
			System.out.println("SMS_RECEIVE_ACTION");
		}
	}

}
