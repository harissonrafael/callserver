package com.example.hr.callserver.request;

import java.util.List;

import com.example.hr.callserver.model.Call;

public class RequestCreateCall {
	private List<Call> calls;

	public List<Call> getCalls() {
		return calls;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}

}
