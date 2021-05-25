package com.single.jpaProjct.register.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

	@Autowired
	RegisterRepository registerRepository;
	
	
	public long idchk(String userid) {
		return registerRepository.countByUserid(userid);
	}
	
	public void userRegister(RegisterVO vo) {
		registerRepository.save(vo);
	}
	
}
