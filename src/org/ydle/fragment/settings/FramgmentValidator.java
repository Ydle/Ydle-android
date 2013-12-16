package org.ydle.fragment.settings;

public interface FramgmentValidator<T> {
	
	boolean isValide();

	String getError();

	T getData();
}
