package com.example.hr.callserver.pageable.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public final class PageRequestBuilder {
	
	private static final int DEFAULT_PAGE_SIZE = 10;

	private PageRequestBuilder() {
		// Do nothing
	}

	/**
	 * Constructs PageRequest
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @param sortingCriteria
	 * @return
	 */
	public static PageRequest getPageRequest(Integer pageSize, Integer pageNumber, String sortingCriteria) {

		Set<String> sortingFields = new LinkedHashSet<>(Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(sortingCriteria, ""), ",")));

		List<Order> sortingOrders = sortingFields.stream().map(PageRequestBuilder::getOrder).collect(Collectors.toList());

		Sort sort = sortingOrders.isEmpty() ? Sort.unsorted() : Sort.by(sortingOrders);

		return PageRequest.of(ObjectUtils.defaultIfNull(pageNumber, 1) - 1, ObjectUtils.defaultIfNull(pageSize, DEFAULT_PAGE_SIZE), sort);
	}

	private static Order getOrder(String value) {

		if (StringUtils.startsWith(value, "-")) {
			return new Order(Direction.DESC, StringUtils.substringAfter(value, "-"));
		} else if (StringUtils.startsWith(value, "+")) {
			return new Order(Direction.ASC, StringUtils.substringAfter(value, "+"));
		} else {
			// Sometimes '+' from query param can be replaced as ' '
			return new Order(Direction.ASC, StringUtils.trim(value));
		}

	}

	public static PageRequest getPageRequest(Map<String, String> filters) {
		Integer pageSize = null, pageNumber = null;
		String sortingCriteria = null;

		if (filters.containsKey("size")) {
			pageSize = Integer.valueOf(filters.get("size").toString());
		}

		if (filters.containsKey("page")) {
			pageNumber = Integer.valueOf(filters.get("page").toString());
		}

		if (filters.containsKey("sort")) {
			sortingCriteria = filters.get("sort").toString();
		}

		return getPageRequest(pageSize, pageNumber, sortingCriteria);
	}
}
