package de.nofail.dzone;

import android.util.Log;

/**
 * Need to create my own logger, cause the log-util-api suckz.
 */
public class Logger {

	public static Logger create(Class<?> clazz) {
		Logger l = new Logger();
		l.clazz = clazz;
		return l;
	}

	private Class<?> clazz;

	public void debug(String message) {
		Log.d(clazz.getCanonicalName(), message);
	}

	public IllegalStateException toE(Exception e) {
		Log.e(clazz.getCanonicalName(), e.getMessage());
		return new IllegalStateException(e);
	}
}
