package com.example.hr.callserver.model.to;

import java.util.ArrayList;
import java.util.List;

public class StatisticsTO {
	String[][] mCaller;
	String[][] mCallee;
	List<StatisticsDayTO> listStatisticsDayTO = new ArrayList<StatisticsDayTO>();

	public String[][] getmCaller() {
		return mCaller;
	}

	public void setmCaller(String[][] mCaller) {
		this.mCaller = mCaller;
	}

	public String[][] getmCallee() {
		return mCallee;
	}

	public void setmCallee(String[][] mCallee) {
		this.mCallee = mCallee;
	}

	public List<StatisticsDayTO> getListStatisticsDayTO() {
		return listStatisticsDayTO;
	}

	public void setListStatisticsDayTO(List<StatisticsDayTO> listStatisticsDayTO) {
		this.listStatisticsDayTO = listStatisticsDayTO;
	}

}
