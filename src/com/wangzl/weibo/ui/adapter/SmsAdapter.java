package com.wangzl.weibo.ui.adapter;

import java.util.List;

import com.wangzl.weibo.R;
import com.wangzl.weibo.bean.SmsInfoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SmsAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<SmsInfoBean> mList;

	public SmsAdapter(Context context,List<SmsInfoBean> pList) {
		mContext = context;
		mList = pList;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.xlistview_item_sms, parent, false);
			viewHolder.iv_avatar = (ImageView)convertView.findViewById(R.id.avatar);
			viewHolder.tv_sms_title = (TextView)convertView.findViewById(R.id.sms_title);
			viewHolder.tv_sms_date = (TextView)convertView.findViewById(R.id.sms_date);
			viewHolder.tv_sms_content = (TextView)convertView.findViewById(R.id.sms_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		SmsInfoBean bean = mList.get(position);
		viewHolder.tv_sms_title.setText(bean.address);
		viewHolder.tv_sms_date.setText(bean.date);
		viewHolder.tv_sms_content.setText(bean.body);
		
		return convertView;
	}
	
	public class ViewHolder {
		ImageView iv_avatar;
		TextView tv_sms_title;
		TextView tv_sms_date;
		TextView tv_sms_content;
	}

}
