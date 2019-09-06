package com.example.hr.callserver.pageable.util;

import org.springframework.data.jpa.domain.Specification;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.model.enums.CallType;

public class CallSpecification {
	public static Specification<Call> getType(CallType type) {
		if (type != null) {
			return (root, query, criteriaBuilder) -> {
				return criteriaBuilder.equal(root.get("type"), type.ordinal());
			};
		}

		return null;
	}
}
