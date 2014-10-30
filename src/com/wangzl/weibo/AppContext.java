package com.wangzl.weibo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class AppContext extends Application {
	
	private static AppContext singleton = null;
	
	private LruCache<String, Bitmap> appBitmapCache = null;

	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		buildCache();
	}
	
	public static AppContext getInstance() {
		return singleton;
	}
	
	public synchronized LruCache<String, Bitmap> getBitmapCache() {
		if (appBitmapCache == null) {
			buildCache();
		}
		return appBitmapCache;
	}
	
	private void buildCache() {
		int memClass = ((ActivityManager)(getSystemService(Context.ACTIVITY_SERVICE))).getMemoryClass();
		int cacheSize = Math.max(1024 * 1024 * 10, 1024 * 1024 * memClass / 5);
		appBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}
}
