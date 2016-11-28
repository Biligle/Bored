package com.wgl.bored.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wgl.bored.R;

/**
 *  吐司工具类
 * @author xbybaoying
 *
 */
public class ToastUtil {
	private static ToastUtil mToastUtil;
	public static ToastUtil getInstance(){
		if(mToastUtil==null){
			mToastUtil = new ToastUtil();
		}
		return mToastUtil;
	}
	private ToastUtil(){}
	private Toast mToast;
	/**
	 * 中间显示吐司
	 * 
	 * @param context
	 *            上下文对象
	 * @param text
	 *            要显示的文本
	 */
	public  void toastInCenter(Context context, String text) {
		if(mToast!=null){
			mToast.cancel();
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_one, null);
		((TextView) layout.findViewById(R.id.textview)).setText(text);
		mToast = new Toast(context);
		mToast.setView(layout);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}
	/**
	 * 中间显示吐司
	 * 
	 * @param context
	 *            上下文对象
	 * @param stringId
	 *            要显示的文本ID
	 */
	public  void toastInCenter(Context context, int stringId) {
		if(mToast!=null){
			mToast.cancel();
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_one, null);
		((TextView) layout.findViewById(R.id.textview)).setText(context.getResources().getString(stringId));
		mToast = new Toast(context);
		mToast.setView(layout);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		//mToast.setDuration(100);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}

}
