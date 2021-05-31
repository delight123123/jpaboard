package com.single.jpaProjct;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.single.jpaProjct.board.domain.ReboardRepository;
import com.single.jpaProjct.board.domain.ReboardService;
import com.single.jpaProjct.register.domain.RegisterRepository;
import com.single.jpaProjct.register.domain.RegisterVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaProjectTest {

	@Autowired
	RegisterRepository registerRepository;
	
	@Autowired
	ReboardRepository reboardRepository;
	
	@Autowired
	ReboardService reboardService;
	
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
	

	
	
}
