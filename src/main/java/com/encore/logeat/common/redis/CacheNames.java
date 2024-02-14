package com.encore.logeat.common.redis;

import lombok.Getter;

@Getter
public class CacheNames {
	public static final String POST = "post";
	public static final String USER_PROFILE = "user_profile";

	public static String createPostCacheKey(Long id) {
		return createCacheKey(POST, id);
	}

	public static String createCacheKey(String cacheType, Long id) {
		return cacheType + "::" + id;
	}
}