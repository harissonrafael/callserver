package com.example.hr.callserver.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.hr.callserver.config.HazelcastConfiguration;
import com.example.hr.callserver.model.Call;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class PageCallCacheUtils {

	private static IMap<Map<String, String>, Page<Call>> MAP_LIST;

	@Autowired
	public PageCallCacheUtils(HazelcastInstance hazelcastInstance) {
		super();
		MAP_LIST = hazelcastInstance.getMap(HazelcastConfiguration.PAGE_CALL_MAP_NAME);
	}

	public static Page<Call> get(Map<String, String> filters) {
		return MAP_LIST.get(filters);
	}

	public static void put(Map<String, String> filters, Page<Call> list) {
		MAP_LIST.put(filters, list);
	}

	public static void remove(Map<String, String> filters) {
		MAP_LIST.remove(filters);
	}

	public static void clear() {
		MAP_LIST.clear();
	}

	public static long getCacheSizeList() {
		return MAP_LIST.getLocalMapStats().getOwnedEntryCount();
	}

}
