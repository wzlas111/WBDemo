package com.wangzl.weibo.ui;

import com.wangzl.common.widget.viewpagerindicator.UnderlinePageIndicator;
import com.wangzl.weibo.R;
import com.wangzl.weibo.ui.base.BaseActivity;
import com.wangzl.weibo.ui.fragment.DrawerFragment;
import com.wangzl.weibo.ui.fragment.MentionFragment;
import com.wangzl.weibo.ui.fragment.TimelineFragment;
import com.wangzl.weibo.util.WUtil;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends BaseActivity implements OnClickListener{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private FrameLayout mPopLayout;
	private ViewPager mViewPager;
	private MyPagerAdapter mPagerAdapter;
	private DrawerFragment mDrawerFragment;
	
	private View mBtnNew;
	private View mBtnRefresh;
	
	private PendingIntent alarmIntent;
	private AlarmManager alarmManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 
				R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
			}
			@Override
			public void onDrawerClosed(View drawerView) {
			}
		};
		initViewPager();
		initActionBar();
		initLeftDrawer();
		
		initAlarm();
	}
	
	private void initAlarm() {
		Intent intent = new Intent("com.wangzl.action.alarm");
		alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		if (alarmManager != null) {
			alarmManager.cancel(alarmIntent);
		}
		alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, alarmIntent);
	}
	
	private void initViews() {
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mPopLayout = (FrameLayout)findViewById(R.id.pop_layout);
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		
		View popView = getLayoutInflater().inflate(R.layout.layout_pop, null);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		int right = WUtil.convertDimen2Pix(10.0f);
		int bottom = WUtil.convertDimen2Pix(5.0f);
		layoutParams.setMargins(0, 0, right, bottom);
		mPopLayout.addView(popView);
		mPopLayout.setLayoutParams(layoutParams);
		mPopLayout.setAlpha(0.8f);
		mBtnNew = popView.findViewById(R.id.btn_new);
		mBtnRefresh = popView.findViewById(R.id.btn_refresh);
		mBtnNew.setOnClickListener(this);
		mBtnRefresh.setOnClickListener(this);
	}
	
	/**
	 * viewpager init
	 */
	private void initViewPager() {
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setPageMargin(10);
	}
	
	/**
	 * actionbar init
	 */
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setIcon(R.drawable.ic_bar_3);
		View custView = getLayoutInflater().inflate(R.layout.layout_indicator, null);
		custView.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
		actionBar.setCustomView(custView);
		UnderlinePageIndicator pageIndicator = (UnderlinePageIndicator)custView.findViewById(R.id.indicator);
		pageIndicator.setViewPager(mViewPager);
		pageIndicator.setFades(false);
		pageIndicator.setSelectedColor(Color.WHITE);
		custView.findViewById(R.id.tab_timeline).setOnClickListener(this);
		custView.findViewById(R.id.tab_mention).setOnClickListener(this);
	}
	
	private void initLeftDrawer() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		if (manager.findFragmentByTag(DrawerFragment.class.getName()) == null) {
			mDrawerFragment = new DrawerFragment();
			transaction.replace(R.id.drawer_left, mDrawerFragment, DrawerFragment.class.getName());
		}
		transaction.commit();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MyPagerAdapter extends FragmentPagerAdapter {
		
		private TimelineFragment mTimelineFragment = new TimelineFragment();
		private MentionFragment mMentionFragment = new MentionFragment();

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return mTimelineFragment;
			case 1:
				return mMentionFragment;
			default:
				break;
			}
			return mTimelineFragment;
		}

		@Override
		public int getCount() {
			return 2;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab_timeline:
				mViewPager.setCurrentItem(0, true);
				break;
			case R.id.tab_mention:
				mViewPager.setCurrentItem(1, true);
				break;
			case R.id.btn_new:
				System.out.println("btn_new click!");
				break;
			case R.id.btn_refresh:
				System.out.println("btn_refresh click!");
				break;
		}
		
	}
}
