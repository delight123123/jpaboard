package com.single.jpaProjct.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.board.register.model.RegisterServiceImpl;
import com.single.board.register.model.RegisterVO;

import lombok.extern.java.Log;

@Controller
@Log
public class RegisterController {
	
	private final static Logger logger=LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private RegisterServiceImpl registerService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public Object register() {
		log.info("회원가입 화면");
		
		return "user/register";
	}
	
	@RequestMapping("/register/idChk")
	@ResponseBody
	public int useridDuplChk(@RequestParam String useridChk) {
		
		logger.info("아이디 중복 확인 파라미터 useridChk={}",useridChk);
		
		int res=2;
		
		res=registerService.userIdChk(useridChk);
		
		logger.info("검색 결과 res={}",res);
		
		return res;
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object register(@ModelAttribute RegisterVO registerVo, @RequestParam(required = false) String email3
			,Model model) {
		
		logger.info("회원가입 파라미터 registerVo={}, email3={}",registerVo,email3);
		
		String email1=registerVo.getEmail1();
		String email2=registerVo.getEmail2();
		
		if(email1==null||email1.isEmpty()) {
			registerVo.setEmail2("");
		}else {
			if(email2.equals("etc")) {
				if(email3!=null&&!email3.isEmpty()) {
					registerVo.setEmail2(email3);
				}else {
					registerVo.setEmail1("");
					registerVo.setEmail2("");
				}
			}
		}
		
		int res=registerService.userRegister(registerVo);
		
		String msg="", url="";
		
		if(res>0) {
			msg="회원가입이 완료되었습니다.";
			url="/login";
		}else {
			msg="회원가입 실패";
			url="/register";
		}
		
		model.addAttribute("url",url);
		model.addAttribute("msg",msg);
		
		return "common/message";
	}
	
	
}
