package de.nofail.dzone;

import android.util.Log;

public class Logger {

	public static IllegalStateException toE(Class<?> clazz, Exception e) {
		Log.e(clazz.getCanonicalName(), e.getMessage());
		return new IllegalStateException(e);
	}
}
