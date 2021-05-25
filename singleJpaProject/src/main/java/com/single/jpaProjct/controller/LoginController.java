package com.single.jpaProjct.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.jpaProjct.register.domain.LoginService;
import com.single.jpaProjct.register.domain.RegisterVO;


@Controller
public class LoginController {
	
	private Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Object login(HttpSession session) {
		logger.info("로그인 화면 띄우기");
		String userid=(String) session.getAttribute("userid");
		
		
		
		if(userid!=null) {
			return "redirect:/main";
		}
		
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(@RequestParam String userid,@RequestParam String userpw
			,@RequestParam(required = false) String chkSave
			,HttpServletRequest request,HttpServletResponse response
			,Model model) {
		
		logger.info("로그인 파라미터 userid={}, userpw={}",userid,userpw);
		
		int res=loginService.userLogin(userid, userpw);
		
		logger.info("결과 res={}",res);
		String url="",msg="";
		
		if(res==LoginService.NONE_USERID) {
			msg="아이디가 존재하지 않습니다.";
			url="/login";
		}else if(res==LoginService.DISAGREE_PWD) {
			msg="비밀번호가 틀립니다.";
			url="/login";
		}else if(res==LoginService.LOGIN_OK) {
			Optional<RegisterVO> registerVo=loginService.userInfo(userid);
			
			logger.info("로그인 유저 정보={}",registerVo);
			
			HttpSession session=request.getSession();
			session.setAttribute("userid", registerVo.get().getUserid());
			session.setAttribute("auth", registerVo.get().getAdminauth());
			
			Cookie ck=new Cookie("chk_userid", userid);
			ck.setPath("/");
			
			if(chkSave!=null) {
				ck.setMaxAge(1000*24*60*60);
				response.addCookie(ck);
			}else {
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
			msg="로그인 완료! 환영합니다!";
			url="/main";
		}else {
			msg="로그인 오류";
			url="/login";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/logout")
	public Object logout(HttpSession session) {
		String userid= (String) session.getAttribute("userid");
		
		logger.info("로그아웃 해당 아이디 userid={}",userid);
		
		session.removeAttribute("userid");
		
		return "redirect:/login";
	}
	
	@RequestMapping("/pwdCg")
	public Object pwdCg() {
		logger.info("비밀번호 변경 화면");
		
		return "user/pwdCg";
	}
	
	@ResponseBody
	@RequestMapping("/userPwdCg")
	public int userPwdCg(@RequestParam String currPwd, @RequestParam String cgPwd
			, HttpSession session) {
		logger.info("비밀번호 변경 파라미터 currPwd={}, cgPwd={}",currPwd,cgPwd);
		
		String userid=(String) session.getAttribute("userid");
		
		int res=0;
		
		int login=loginService.userLogin(userid, currPwd);
		if(login==LoginService.NONE_USERID) {
			RegisterVO vo=new RegisterVO();
			vo.setUserid(userid);
			vo.setUserpw(cgPwd);
			res=1;
			loginService.userPwCg(vo);
			logger.info("비밀번호 변경 결과 res={}",res);
		}else if(login==LoginService.DISAGREE_PWD) {

		}
		
		
		
		return res;
	}
}
