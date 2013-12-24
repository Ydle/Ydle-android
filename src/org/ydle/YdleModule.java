package org.ydle;

import org.ydle.remote.YdleCacheService;
import org.ydle.remote.YdleService;

import com.google.inject.AbstractModule;

public class YdleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(YdleService.class).to(YdleCacheService.class);
		
	}

}
