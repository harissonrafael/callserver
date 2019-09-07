package com.example.hr.callserver.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.model.enums.CallType;
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

	public Map<LocalDate, StatisticsTO> getStatistics(Map<String, String> filters) {
		Map<LocalDate, StatisticsTO> map = new HashMap<LocalDate, StatisticsTO>();
		List<Call> listAll = repository.findAllByOrderByStartAsc();

		listAll.stream().forEach(c -> {
			LocalDate currentDay = c.getStart().toLocalDate();

			if (map.get(currentDay) == null) {
				map.put(currentDay, calculateValues(new StatisticsTO(), c));
			} else {
				map.put(currentDay, calculateValues(map.get(currentDay), c));
			}
		});

		return map;
	}

	private StatisticsTO calculateValues(StatisticsTO statisticsTO, Call call) {
		Duration duration = Duration.between(call.getStart(), call.getEnd());

		if (call.getType().equals(CallType.INBOUND)) {
			statisticsTO.setDurationInbound(statisticsTO.getDurationInbound().plus(duration));
		} else {
			statisticsTO.setDurationOutbound(statisticsTO.getDurationOutbound().plus(duration));

			long minutes = call.getStart().until(call.getEnd(), ChronoUnit.MINUTES);

			// include initial cost
			BigDecimal value = new BigDecimal(0.10);

			if (minutes > 5) {
				BigDecimal valueLowCost = new BigDecimal(0.05);
				long amountInterval = (minutes / 5) - 1;

				value = value.add(valueLowCost.multiply(new BigDecimal(amountInterval)));
			}

			statisticsTO.setCost(statisticsTO.getCost().add(value.setScale(2, RoundingMode.DOWN)));
		}

		statisticsTO.setAmount(statisticsTO.getAmount() + 1);

		if (statisticsTO.getAmountCaller().get(call.getCallerNumber()) == null) {
			statisticsTO.getAmountCaller().put(call.getCallerNumber(), 1);
		} else {
			statisticsTO.getAmountCaller().put(call.getCallerNumber(), statisticsTO.getAmountCaller().get(call.getCallerNumber()) + 1);
		}

		if (statisticsTO.getAmountCallee().get(call.getCalleeNumber()) == null) {
			statisticsTO.getAmountCallee().put(call.getCalleeNumber(), 1);
		} else {
			statisticsTO.getAmountCallee().put(call.getCalleeNumber(), statisticsTO.getAmountCallee().get(call.getCalleeNumber()) + 1);
		}

		return statisticsTO;
	}

}
