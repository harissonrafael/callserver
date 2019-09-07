package com.example.hr.callserver.model.to;

import java.math.BigInteger;
import java.util.Date;

public class StatisticByTypeTO {
	Date day;
	BigInteger amount;

	private StatisticByTypeTO(Builder builder) {
		this.day = builder.day;
		this.amount = builder.amount;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Date day;
		private BigInteger amount;

		private Builder() {
		}

		public Builder withDay(Date day) {
			this.day = day;
			return this;
		}

		public Builder withAmount(BigInteger amount) {
			this.amount = amount;
			return this;
		}

		public StatisticByTypeTO build() {
			return new StatisticByTypeTO(this);
		}
	}

}
