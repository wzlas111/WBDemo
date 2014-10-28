package com.wangzl.weibo.util;

import android.content.res.Resources;
import android.util.TypedValue;

public class WUtil {

	public static int convertDimen2Pix(float paramDP) {
		return (int)TypedValue.applyDimension(1, paramDP, Resources.getSystem().getDisplayMetrics());
	}
}
