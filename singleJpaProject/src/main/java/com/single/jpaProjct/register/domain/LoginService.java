package com.single.jpaProjct.register.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SHA256Util;

@Service
public class LoginService {
	
	public static final int LOGIN_OK=1;  //로그인 성공
	public static final int DISAGREE_PWD=2; //비밀번호 불일치
	public static final int NONE_USERID=3; //해당 아이디 존재하지 않음
	
	@Autowired
	RegisterRepository registerRepository;
	
	public int userLogin(String userid,String userpw) {
		System.out.println(userid);
		String salt=registerRepository.findOneByUserid(userid).get().getSalt();
		String pwByuserid=registerRepository.findOneByUserid(userid).get().getUserpw();
		System.out.println(salt);
		int result=0;
		
		if(salt==null||salt.isEmpty()) {
			result=NONE_USERID;
		}else {
			String pw=SHA256Util.getEncrypt(userpw, salt);
			System.out.println("pw="+pw);
			if(pw.equals(pwByuserid)) {
				result=LOGIN_OK;
			}else {
				result=DISAGREE_PWD;
			}
		}
		
		return result;
	}
	
	
	public Optional<RegisterVO> userInfo(String userid){
		return registerRepository.findOneByUserid(userid);
	}
	
	
	public void userPwCg(RegisterVO vo) {
		registerRepository.save(vo);
	}
}
