package com.example.hr.callserver.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.model.enums.CallType;
import com.example.hr.callserver.pageable.util.CallSpecification;
import com.example.hr.callserver.pageable.util.PageRequestBuilder;
import com.example.hr.callserver.repository.CallRepository;
import com.example.hr.callserver.request.RequestCreateCall;

@Service
public class CallService {

	private CallRepository repository;

	@Autowired
	public CallService(CallRepository repository) {
		super();
		this.repository = repository;
	}

	public Page<Call> findAll(Map<String, String> filters) {
		PageRequest pageRequest = PageRequestBuilder.getPageRequest(filters);

		CallType type = null;

		if (filters.get("type") != null) {
			type = CallType.valueOf(filters.getOrDefault("type", null));
		}

		Specification<Call> specifications = Specification.where(type != null ? CallSpecification.getType(type) : null);

		return repository.findAll(specifications, pageRequest);
	}

	public List<Call> create(RequestCreateCall requestCreateCall) {
		return repository.saveAll(requestCreateCall.getCalls());
	}

	public List<Call> saveAll(List<Call> call) {
		return (List<Call>) repository.saveAll(call);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
