package com.example.hr.callserver.model.to;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StatisticsTO {
	LocalDate day;
	int amount;
	int amountInbound;
	int amountOutbound;
	StatisticCallTO amountCaller;
	StatisticCallTO amountCallee;
	BigDecimal cost;

	private StatisticsTO(Builder builder) {
		this.day = builder.day;
		this.amount = builder.amount;
		this.amountInbound = builder.amountInbound;
		this.amountOutbound = builder.amountOutbound;
		this.amountCaller = builder.amountCaller;
		this.amountCallee = builder.amountCallee;
		this.cost = builder.cost;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private LocalDate day;
		private int amount;
		private int amountInbound;
		private int amountOutbound;
		private StatisticCallTO amountCaller;
		private StatisticCallTO amountCallee;
		private BigDecimal cost;

		private Builder() {
		}

		public Builder withDay(LocalDate day) {
			this.day = day;
			return this;
		}

		public Builder withAmount(int amount) {
			this.amount = amount;
			return this;
		}

		public Builder withAmountInbound(int amountInbound) {
			this.amountInbound = amountInbound;
			return this;
		}

		public Builder withAmountOutbound(int amountOutbound) {
			this.amountOutbound = amountOutbound;
			return this;
		}

		public Builder withAmountCaller(StatisticCallTO amountCaller) {
			this.amountCaller = amountCaller;
			return this;
		}

		public Builder withAmountCallee(StatisticCallTO amountCallee) {
			this.amountCallee = amountCallee;
			return this;
		}

		public Builder withCost(BigDecimal cost) {
			this.cost = cost;
			return this;
		}

		public StatisticsTO build() {
			return new StatisticsTO(this);
		}
	}

}
