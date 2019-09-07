package com.example.hr.callserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hr.callserver.model.Call;

@Repository
public interface CallRepository extends JpaRepository<Call, Long>, JpaSpecificationExecutor<Call> {

	@Query(value = "select cast(start as date) as day, sum(end - start), count(*) as amount from call group by cast(start as date), type", nativeQuery = true)
	List<Object[]> amountByType();

}
