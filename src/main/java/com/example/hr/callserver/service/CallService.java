package com.example.hr.callserver.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.model.enums.CallType;
import com.example.hr.callserver.model.to.StatisticByTypeTO;
import com.example.hr.callserver.model.to.StatisticsTO;
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

	public StatisticsTO getStatistics(Map<String, String> filters) {

		List<StatisticByTypeTO> list2 = new ArrayList<StatisticByTypeTO>();
		List<Object[]> list = repository.amountByType();

		for (Object[] objects : list) {
			list2.add(StatisticByTypeTO.builder()
										.withDay((Date) objects[0])
										.withAmount((BigInteger) objects[1])
										.build());
		}

//		try {
//			List<CompletableFuture<?>> listCompletableFuture = new ArrayList<CompletableFuture<?>>();
//
//			// Amount by type
//			CompletableFuture<List<StatisticByTypeTO>> amountByType = CompletableFuture.supplyAsync(() -> {
//				return repository.amountByType();
//			});
//
//			listCompletableFuture.add(amountByType);
//
//			CompletableFuture<Void> allFutures = CompletableFuture.allOf(listCompletableFuture.toArray(new CompletableFuture[listCompletableFuture.size()]));
//
//			CompletableFuture<List<?>> allCompletableFuture = allFutures.thenApply(future -> {
//				return listCompletableFuture.stream().map(completableFuture -> completableFuture.join()).collect(Collectors.toList());
//			});
//
//			allCompletableFuture.get();
//
//			List<StatisticByTypeTO> listStatisticByTypeTO = amountByType.get();
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}

		return StatisticsTO.builder().build();
	}

}
