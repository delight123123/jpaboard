package com.single.jpaProjct.refund.domain;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<RefundVO, Long>{

	public Page<RefundVO> findAllByReportingDateBetween(Timestamp startDate,
			Timestamp endDate,Pageable paging);
}
