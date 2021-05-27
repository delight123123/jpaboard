package com.single.jpaProjct.register.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SHA256Util;

@Service
public class RegisterService {

	@Autowired
	RegisterRepository registerRepository;
	
	
	public long idchk(String userid) {
		return registerRepository.countByUserid(userid);
	}
	
	public void userRegister(RegisterVO registerVo) {
		String salt=SHA256Util.generateSalt();
		registerVo.setSalt(salt);
		String password=SHA256Util.getEncrypt(registerVo.getUserpw(), salt);
		
		registerVo.setUserpw(password);
		
		
		registerRepository.save(registerVo);
	}
	
}
