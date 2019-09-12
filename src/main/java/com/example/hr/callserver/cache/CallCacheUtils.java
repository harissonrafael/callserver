package com.example.hr.callserver.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.hr.callserver.config.HazelcastConfiguration;
import com.example.hr.callserver.model.Call;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class CallCacheUtils {

	private static IMap<Long, Call> MAP;

	@Autowired
	public CallCacheUtils(HazelcastInstance hazelcastInstance) {
		super();
		MAP = hazelcastInstance.getMap(HazelcastConfiguration.CALL_MAP_NAME);
	}

	public static Call get(Long id) {
		return MAP.get(id);
	}

	public static void put(Long id, Call call) {
		MAP.put(id, call);
	}

	public static void remove(Long id) {
		MAP.remove(id);
	}

	public static void clear() {
		MAP.clear();
	}

	public static long getCacheSize() {
		return MAP.getLocalMapStats().getOwnedEntryCount();
	}

}
