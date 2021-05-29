package com.single.jpaProjct;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.single.jpaProjct.board.domain.ReboardRepository;
import com.single.jpaProjct.board.domain.ReboardVO;
import com.single.jpaProjct.register.domain.RegisterRepository;
import com.single.jpaProjct.register.domain.RegisterVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaProjectTest {

	@Autowired
	RegisterRepository registerRepository;
	
	@Autowired
	ReboardRepository reboardRepository;
	
	@Test
	public void idchkTest() {
		long a=registerRepository.countByUserid("glddld");
		System.out.println("a="+a);
		
	}
	
	@Test
	public void useridSel() {
		Optional<RegisterVO> op=registerRepository.findOneByUserid("glddld");
		System.out.println(op.isEmpty());
	}
	
	@Transactional
	@Test
	public void pagingTest() {
		Pageable paging=PageRequest.of(0, 5, Sort.Direction.DESC, "reboardNo");
		//Page<ReboardVO> pa=reboardRepository.findByUseridContaining(registerVo, paging);
	
		//ReboardVO vo2=new ReboardVO();
		//vo2.setRegisterVo(op.get());

		//Page<ReboardVO> results=reboardRepository.findByRegisterVoContaining(vo2, paging);

		RegisterVO reVo=new RegisterVO();
		reVo.setUserid("glddld");
		
		Page<ReboardVO> results = reboardRepository.findAllByRegisterVo(reVo, paging);
		List<ReboardVO> list = results.getContent().stream().collect(Collectors.toList());
		System.out.println("list.size()=" + list.size());
		System.out.println("검색결과 개수=" + results.getSize());
		System.out.println("paging total" + results.getTotalPages());
		 
		
		
	}
	
}
