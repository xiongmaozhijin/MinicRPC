package com.example.minicrpc.utils;

import android.util.Log;

/**
 * 调试输出工具类
 * @author michael.chen
 *
 */
public class MinicRPCLog {
	
	private MinicRPCLog() {}
	
	public static final boolean DEBUG = true;
	
	public static void i(String tag, String format, Object... args) {
		if (DEBUG) {
			String message = String.format(format, args);
			Log.i(tag, message);
		}
		
	}
	
	public static void e(String tag, String format, Object... args) {
		if (DEBUG) {
			String message = String.format(format, args);
			Log.e(tag, message);
		}
		
	}
	
	
	public static void d(String tag, String format, Object... args) {
		if (DEBUG) {
			String message = String.format(format, args);
			Log.d(tag, message);
		}
		
	}
	
	
	public static void w(String tag, String format, Object... args) {
		if (DEBUG) {
			String message = String.format(format, args);
			Log.w(tag, message);
		}
	}
	
	
	
	
}
