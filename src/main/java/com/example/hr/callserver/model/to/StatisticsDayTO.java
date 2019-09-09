package com.example.hr.callserver.model.to;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

public class StatisticsDayTO {
	LocalDate day;
	int amount = 0;
	Duration durationInbound = Duration.ZERO;
	Duration durationOutbound = Duration.ZERO;
	BigDecimal cost = BigDecimal.ZERO;

	public StatisticsDayTO(LocalDate day) {
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}
