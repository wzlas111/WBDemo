package com.wangzl.weibo.ui.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wangzl.common.widget.pulltorefresh.XListView;
import com.wangzl.common.widget.pulltorefresh.XListView.IXListViewListener;
import com.wangzl.weibo.R;
import com.wangzl.weibo.bean.SmsInfoBean;
import com.wangzl.weibo.ui.MainActivity;
import com.wangzl.weibo.ui.adapter.SmsAdapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFragment extends BaseFragment implements IXListViewListener{
	
	private XListView mListView;
	private SmsAdapter mAdapter;
	private List<SmsInfoBean> mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_timeline, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mListView = (XListView)view.findViewById(R.id.xlistview);
		
		mList = new ArrayList<SmsInfoBean>();
		mAdapter = new SmsAdapter(getActivity(), mList);
		mListView.setXListViewListener(this);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		onRefresh();
	}

	@Override
	public void onRefresh() {
		new InitDataAsyncTask(getActivity()).execute("");
		mListView.stopRefresh();
	}

	@Override
	public void onLoadMore() {
	}
	
	private class InitDataAsyncTask extends AsyncTask<String, Integer, Boolean> {

		private Context mContext;
		
		public InitDataAsyncTask(Context context) {
			mContext = context;
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			mList = new ArrayList<SmsInfoBean>();
			try {
				ContentResolver resolver = mContext.getContentResolver();
				Uri uri = Uri.parse("content://sms/");
				Cursor cursor = resolver.query(uri, new String[] {"_id", "address", "date", "type", "body"}, null, null, " date desc ");
				while(cursor.moveToNext()) {
					SmsInfoBean bean = new SmsInfoBean();
		            bean.id = cursor.getString(0);  
		            bean.address = cursor.getString(1);  
		            bean.type = cursor.getInt(3);  //1:接收；2：发送
		            bean.body = cursor.getString(4);  
		            bean.date = dateFormat.format(new Date(cursor.getLong(2)));
		            
		            if (bean.type == 1) {
		            	mList.add(bean);
					}
				}
				cursor.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mAdapter = new SmsAdapter(getActivity(), mList);
			mListView.setAdapter(mAdapter);
			
			Notification nt = new Notification();
			nt.icon = R.drawable.about_ic_suixing;
			nt.tickerText = "您收到了一条短信息.";
			nt.when = System.currentTimeMillis();
			nt.defaults = Notification.DEFAULT_ALL;
			PendingIntent intent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
			nt.setLatestEventInfo(getActivity(), "短信息", "您收到了一条短信息.", intent);
			notificationManager.notify(0x123, nt);
		}
	}
}
