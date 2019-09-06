package com.example.hr.callserver.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hr.callserver.model.Call;
import com.example.hr.callserver.request.RequestCreateCall;
import com.example.hr.callserver.service.CallService;

@RestController
@RequestMapping(value = "/callers")
public class CallController {

	private CallService service;

	@Autowired
	public CallController(CallService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Page<Call>> findAll(@RequestParam(required = false) Map<String, String> filters) {
		return new ResponseEntity<Page<Call>>(service.findAll(filters), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<List<Call>> create(@RequestBody RequestCreateCall requestCreateCall) {
		return new ResponseEntity<List<Call>>(service.create(requestCreateCall), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
