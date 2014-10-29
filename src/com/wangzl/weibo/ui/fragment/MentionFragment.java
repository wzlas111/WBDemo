package com.wangzl.weibo.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wangzl.common.widget.pulltorefresh.XListView;
import com.wangzl.common.widget.pulltorefresh.XListView.IXListViewListener;
import com.wangzl.weibo.R;
import com.wangzl.weibo.bean.MmsInfoBean;
import com.wangzl.weibo.ui.adapter.MmsAdapter;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MentionFragment extends BaseFragment implements IXListViewListener{

	private XListView mListView;
	private MmsAdapter mAdapter;
	private List<MmsInfoBean> mList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mention, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mList = new ArrayList<MmsInfoBean>();
		mListView = (XListView)view.findViewById(R.id.xlistview);
		mListView.setPullLoadEnable(false);
		mListView.setXListViewListener(this);
		mAdapter = new MmsAdapter(getActivity(), mList);
		mListView.setAdapter(mAdapter);
		
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
	
	private final Uri CONTENT_URI = Uri.parse("content://mms/inbox"); //查询彩信收件箱
    private final Uri CONTENT_URI_PART = Uri.parse("content://mms/part"); //彩信附件表
	
	private class InitDataAsyncTask extends AsyncTask<String, Integer, Boolean> {

		private Context mContext;
		private ContentResolver resolver;
		
		public InitDataAsyncTask(Context context) {
			mContext = context;
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			mList = new ArrayList<MmsInfoBean>();
			try {
				resolver = mContext.getContentResolver();
				Cursor cursor = resolver.query(CONTENT_URI, new String[] {"_id", "date", "sub"}, null, null, " date desc ");
				while(cursor.moveToNext()) {
					MmsInfoBean bean = new MmsInfoBean();
		            bean.id = cursor.getString(0);  
		            bean.date = dateFormat.format(new Date(cursor.getLong(1)));  
		            String title = cursor.getString(2);
		            if (title != null && !"".equals(title)) {
		            	bean.title = new String(title.getBytes("iso-8859-1"),"UTF-8");
					} else {
						bean.title = "无";
					}
		            
		            Cursor subCursor = resolver.query(CONTENT_URI_PART, new String[] {"_id", "mid", "ct", "text"}, "mid='"+bean.id+"'", null, null);
		            while(subCursor.moveToNext()) {
		            	String _id = subCursor.getString(0);
		            	String mid = subCursor.getString(1);
		            	String ct = subCursor.getString(2);
		            	String text = subCursor.getString(3);
		            	if ("image/jpeg".equals(ct) || "image/bmp".equals(ct) || "image/gif".equals(ct)
								|| "image/png".equals(ct) || "image/jpg".equals(ct)) {
		            		bean.img = getImage(_id);
						} else {
							bean.body = getText(_id);
						}
		            }
		            mList.add(bean);
		            subCursor.close();
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
			mAdapter = new MmsAdapter(getActivity(), mList);
			mListView.setAdapter(mAdapter);
			
//			Notification nt = new Notification();
//			nt.icon = R.drawable.about_ic_suixing;
//			nt.tickerText = "您收到了一条彩信息.";
//			nt.when = System.currentTimeMillis();
//			nt.defaults = Notification.DEFAULT_ALL;
//			notificationManager.notify(0x123, nt);
		}
		
		private String getText(String _id) {
			Uri partURI = Uri.parse("content://mms/part/" + _id ); 
	        InputStream is = null; 
	        StringBuilder sb = new StringBuilder();
	        try { 
	            is = resolver.openInputStream(partURI); 
	            if(is!=null){
	                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	                String temp = reader.readLine();
	                while (temp != null) {
	                    sb.append(temp);
	                    temp=reader.readLine();
	                }
	            }
	        }catch (IOException e) { 
	        }finally{ 
	            if (is != null){ 
	                try { 
	                    is.close(); 
	                }catch (IOException e){
	                }
	            } 
	        }
	        return sb.toString();
		}
		
		private Bitmap getImage(String _id) {
			Uri partURI = Uri.parse("content://mms/part/" + _id ); 
	        InputStream is = null; 
	        Bitmap bitmap=null;
	        try { 
	            is = resolver.openInputStream(partURI); 
	            bitmap = BitmapFactory.decodeStream(is);
	        }catch (IOException e) { 
	            e.printStackTrace();  
	        }finally{ 
	            if (is != null){ 
	                try { 
	                    is.close(); 
	                }catch (IOException e){
	                }
	            } 
	        }
	        return bitmap;
		}
	}
}
