package com.example.hr.callserver.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.model.enums.CallType;
import com.example.hr.callserver.model.to.StatisticsDayTO;
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

	public StatisticsTO getStatistics() {
		TreeMap<LocalDate, StatisticsDayTO> map = new TreeMap<LocalDate, StatisticsDayTO>();
		List<Call> listAll = repository.findAllByOrderByStartAsc();

		List<String> listCallerNumber = listAll.stream().map(c -> c.getCallerNumber()).distinct().collect(Collectors.toList());
		List<String> listCalleeNumber = listAll.stream().map(c -> c.getCalleeNumber()).distinct().collect(Collectors.toList());
		List<String> listDay = listAll.stream().map(c -> c.getStart().toLocalDate().toString()).distinct().collect(Collectors.toList());

		StatisticsTO statisticTO = new StatisticsTO();

		int amountDay = listDay.size();
		int amountCaller = listCallerNumber.size();
		int amountCallee = listCalleeNumber.size();

		String[][] mCaller = new String[amountDay + 1][amountCaller + 2];
		mCaller[0][0] = "Day";
		mCaller[0][1] = "Total";

		for (int i = 0; i < listCallerNumber.size(); i++) {
			mCaller[0][i + 2] = listCallerNumber.get(i);
		}

		for (int j = 0; j < listDay.size(); j++) {
			mCaller[j + 1][0] = listDay.get(j);
		}

		for (int i = 0; i < listCallerNumber.size() + 2; i++) {
			for (int j = 0; j < listDay.size() + 1; j++) {
				if (mCaller[j][i] == null) {
					mCaller[j][i] = "0";
				}
			}
		}

		statisticTO.setmCaller(mCaller);

		String[][] mCallee = new String[amountDay + 1][amountCallee + 2];
		mCallee[0][0] = "Day";
		mCallee[0][1] = "Total";

		for (int i = 0; i < listCalleeNumber.size(); i++) {
			mCallee[0][i + 2] = listCalleeNumber.get(i);
		}

		for (int j = 0; j < listDay.size(); j++) {
			mCallee[j + 1][0] = listDay.get(j);
		}

		for (int i = 0; i < listCalleeNumber.size() + 2; i++) {
			for (int j = 0; j < listDay.size() + 1; j++) {
				if (mCallee[j][i] == null) {
					mCallee[j][i] = "0";
				}
			}
		}

		statisticTO.setmCallee(mCallee);

		listAll.stream().forEach(c -> {
			LocalDate currentDay = c.getStart().toLocalDate();

			if (map.get(currentDay) == null) {
				map.put(currentDay, calculateValues(new StatisticsDayTO(currentDay), c, listCallerNumber, listCalleeNumber, listDay, statisticTO));
			} else {
				map.put(currentDay, calculateValues(map.get(currentDay), c, listCallerNumber, listCalleeNumber, listDay, statisticTO));
			}
		});


		statisticTO.setListStatisticsDayTO(new ArrayList<StatisticsDayTO>(map.values()));

		return statisticTO;
	}

	private StatisticsDayTO calculateValues(StatisticsDayTO statisticsDayTO, Call call, List<String> listCallerNumber, List<String> listCalleeNumber, List<String> listDay,
			StatisticsTO statisticTO) {
		Duration duration = Duration.between(call.getStart(), call.getEnd());

		if (call.getType().equals(CallType.INBOUND)) {
			statisticsDayTO.setDurationInbound(statisticsDayTO.getDurationInbound().plus(duration));
		} else {
			statisticsDayTO.setDurationOutbound(statisticsDayTO.getDurationOutbound().plus(duration));

			long minutes = call.getStart().until(call.getEnd(), ChronoUnit.MINUTES);

			// include initial cost
			BigDecimal value = new BigDecimal(0.10);

			if (minutes > 5) {
				BigDecimal valueLowCost = new BigDecimal(0.05);
				long amountInterval = (minutes / 5) - 1;

				value = value.add(valueLowCost.multiply(new BigDecimal(amountInterval)));
			}

			statisticsDayTO.setCost(statisticsDayTO.getCost().add(value.setScale(2, RoundingMode.DOWN)));
		}

		statisticsDayTO.setAmount(statisticsDayTO.getAmount() + 1);

		int idxDay = listDay.indexOf(statisticsDayTO.getDay().toString()) + 1;
		int idxCaller = listCallerNumber.indexOf(call.getCallerNumber()) + 2;
		int idxCallee = listCalleeNumber.indexOf(call.getCalleeNumber()) + 2;

		if (statisticTO.getmCaller()[idxDay][idxCaller] == null) {
			statisticTO.getmCaller()[idxDay][idxCaller] = "1";
		} else {
			statisticTO.getmCaller()[idxDay][idxCaller] = "" + (Integer.parseInt(statisticTO.getmCaller()[idxDay][idxCaller]) + 1);
		}

		if (statisticTO.getmCallee()[idxDay][idxCallee] == null) {
			statisticTO.getmCallee()[idxDay][idxCallee] = "1";
		} else {
			statisticTO.getmCallee()[idxDay][idxCallee] = "" + (Integer.parseInt(statisticTO.getmCallee()[idxDay][idxCallee]) + 1);
		}

		return statisticsDayTO;
	}

}
