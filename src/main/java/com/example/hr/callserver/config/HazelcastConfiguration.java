package com.example.hr.callserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

@Configuration
public class HazelcastConfiguration {
	
	public static String CALL_MAP_NAME = "CALL_MAP";
	public static String PAGE_CALL_MAP_NAME = "PAGE_LIST_MAP";

	@Bean
	public Config hazelCastConfig() {
		Config config = new Config();
		config.setInstanceName("hazelcast-orgportal-instance");

		// call
		config.addMapConfig(
				new MapConfig().setName(CALL_MAP_NAME)
								.setMaxSizeConfig(new MaxSizeConfig(2, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
								.setEvictionPolicy(EvictionPolicy.LRU)
								.setTimeToLiveSeconds(-1));
		
		// page_call
		config.addMapConfig(
				new MapConfig().setName(PAGE_CALL_MAP_NAME)
								.setMaxSizeConfig(new MaxSizeConfig(2, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
								.setEvictionPolicy(EvictionPolicy.LRU)
								.setTimeToLiveSeconds(60));
		return config;
	}

}
