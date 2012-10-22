package org.jenkinsci.plugins.nopmdcheck.util;

import hudson.model.BuildListener;

import java.io.PrintStream;

public class Logger {

	private PrintStream ps;

	private static Level logLevel = Level.INFO;

	private Logger(PrintStream ps) {
		this.ps = ps;
	}

	private static Logger instance;

	public static Logger getInstance() {
		if (instance != null) {
			return instance;
		} else {
			throw new IllegalStateException();
		}
	}

	public static void init(BuildListener listener) {
		instance = new Logger(listener.getLogger());
	}

	public static void init(PrintStream ps) {
		init(ps, Level.INFO);
	}

	public static void init(PrintStream ps, Level lv) {

		logLevel = lv;
		if (ps != null) {
			instance = new Logger(ps);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static boolean isEnalbeINFO() {
		return logLevel.rank() <= Level.INFO.rank();
	}

	public Logger info(String... messages) {
		if (logLevel.rank() <= Level.INFO.rank()) {
			say(messages);
		}
		return this;
	}

	public static boolean isEnalbeWARN() {
		return logLevel.rank() <= Level.WARN.rank();
	}

	public Logger warn(String... messages) {
		if (logLevel.rank() <= Level.WARN.rank()) {
			say(messages);
		}
		return this;
	}

	public static boolean isEnalbeDebug() {
		return logLevel.rank() <= Level.DEBUG.rank();
	}

	public Logger debug(String... messages) {
		if (logLevel.rank() <= Level.DEBUG.rank()) {
			say(messages);
		}
		return this;
	}

	private void say(String... messages) {
		for (String message : messages) {
			ps.print(message);
		}
		ps.println();
	}

	public enum Level {

		WARN(10), INFO(5), DEBUG(0);
		private int lv;

		int rank() {
			return lv;
		}

		private Level(int lv) {
			this.lv = lv;
		}
	}
}
