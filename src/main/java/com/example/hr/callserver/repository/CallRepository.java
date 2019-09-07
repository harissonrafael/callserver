package com.example.hr.callserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.hr.callserver.model.Call;

@Repository
public interface CallRepository extends JpaRepository<Call, Long>, JpaSpecificationExecutor<Call> {

	List<Call> findAllByOrderByStartAsc();

}
