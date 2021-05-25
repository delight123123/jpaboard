package com.single.jpaProjct.register.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<RegisterVO, String>{

	public long countByUserid(String userid);
	public Optional<RegisterVO> findOneByUserid(String userid);
	public List<RegisterVO> findOneByUserid(String userid,Pageable paging);
}
