package com.readysteady;

import java.util.ArrayList;
import java.util.List;

public class CompositeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 944685431442287324L;
	List<Exception> list = new ArrayList<>();

	public CompositeException() {
	}

	public CompositeException(final List<Exception> aList) {
		list = aList;
	}

	public CompositeException add(final Exception e) {
		list.add(e);
		return this;
	}

	@Override
	public String getMessage() {
		final StringBuffer sb = new StringBuffer();
		for (final Exception e : list) {
			sb.append(e.getMessage());
		}
		return sb.toString();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		final List<StackTraceElement> elements = new ArrayList<>();
		for (final Exception e : list) {
			for (final StackTraceElement el : e.getStackTrace()) {
				elements.add(el);
			}
		}
		return (StackTraceElement[]) elements.toArray();
	}

}
