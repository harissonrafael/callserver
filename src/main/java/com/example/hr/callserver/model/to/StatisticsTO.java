package com.example.hr.callserver.model.to;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StatisticsTO {
	LocalDate day;
	int amount = 0;
	Duration durationInbound = Duration.ZERO;
	Duration durationOutbound = Duration.ZERO;
	Map<String, Integer> amountCaller = new HashMap<String, Integer>();
	Map<String, Integer> amountCallee = new HashMap<String, Integer>();
	BigDecimal cost = BigDecimal.ZERO;

	public StatisticsTO(LocalDate day) {
		super();
		this.day = day;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Duration getDurationInbound() {
		return durationInbound;
	}

	public void setDurationInbound(Duration durationInbound) {
		this.durationInbound = durationInbound;
	}

	public Duration getDurationOutbound() {
		return durationOutbound;
	}

	public void setDurationOutbound(Duration durationOutbound) {
		this.durationOutbound = durationOutbound;
	}

	public Map<String, Integer> getAmountCaller() {
		return amountCaller;
	}

	public void setAmountCaller(Map<String, Integer> amountCaller) {
		this.amountCaller = amountCaller;
	}

	public Map<String, Integer> getAmountCallee() {
		return amountCallee;
	}

	public void setAmountCallee(Map<String, Integer> amountCallee) {
		this.amountCallee = amountCallee;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
}
