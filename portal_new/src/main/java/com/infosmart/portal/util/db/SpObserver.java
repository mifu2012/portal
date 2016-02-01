package com.infosmart.portal.util.db;

public class SpObserver {
	private static ThreadLocal<String> dbNameThread = new ThreadLocal<String>();

	public static void putDbName(String dbName) {
		dbNameThread.set(dbName);
	}

	public static String getDbName() {
		return dbNameThread.get();
	}
}
