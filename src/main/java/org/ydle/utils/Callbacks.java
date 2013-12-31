package org.ydle.utils;

import java.util.List;

public interface Callbacks<T> {
	/**
	 * Callback for when an item has been selected.
	 */
	public void onItemSelected(T id,List<T> items);
}
