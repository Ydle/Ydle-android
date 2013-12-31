package org.ydle.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ydle.model.configuration.ServeurInfo;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<ServeurInfo> ITEMS = new ArrayList<ServeurInfo>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, ServeurInfo> ITEM_MAP = new HashMap<String, ServeurInfo>();

	static {
		// Add 3 sample items.
		
		addItem(new ServeurInfo("Ydle 1","192.168.0.11"));
		addItem(new ServeurInfo("Ydle Dev","127.0.0.1"));
		addItem(new ServeurInfo("Ydle test","192.168.0.13"));
	}

	private static void addItem(ServeurInfo item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.nom, item);
	}


}
