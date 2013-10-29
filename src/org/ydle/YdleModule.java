package org.ydle;

import org.ydle.remote.YdleService;
import org.ydle.remote.YdleServiceImpl;

import com.google.inject.AbstractModule;

public class YdleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(YdleService.class).to(YdleServiceImpl.class);
	}

}
