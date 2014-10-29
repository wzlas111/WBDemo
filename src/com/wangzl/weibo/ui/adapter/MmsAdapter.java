package com.wangzl.weibo.ui.adapter;

import java.util.List;

import com.wangzl.weibo.R;
import com.wangzl.weibo.bean.MmsInfoBean;
import com.wangzl.weibo.bean.SmsInfoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MmsAdapter extends BaseAdapter {

	private Context mContext;
	private List<MmsInfoBean> mList;

	public MmsAdapter(Context context,List<MmsInfoBean> pList) {
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
			viewHolder.v_layout_thumbnail_pic = convertView.findViewById(R.id.layout_thumbnail_pic);
			viewHolder.iv_imageView1 = (ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		MmsInfoBean bean = mList.get(position);
		viewHolder.tv_sms_title.setText(bean.title);
		viewHolder.tv_sms_date.setText(bean.date);
		if (bean.body != null && !"".equals(bean.body)) {
			viewHolder.tv_sms_content.setText(bean.body);
		} else {
			viewHolder.tv_sms_content.setText("无");
		}
		if (bean.img != null && !"".equals(bean.img)) {
			viewHolder.v_layout_thumbnail_pic.setVisibility(View.VISIBLE);
			viewHolder.iv_imageView1.setImageBitmap(bean.img);
		} else {
			viewHolder.v_layout_thumbnail_pic.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	public class ViewHolder {
		ImageView iv_avatar;
		TextView tv_sms_title;
		TextView tv_sms_date;
		TextView tv_sms_content;
		View v_layout_thumbnail_pic;;
		ImageView iv_imageView1;
	}
}
