package com.single.jpaProjct.email;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.jpaProjct.common.Utility;

@Controller
public class EmailController {
	
	private static final Logger logger=LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	EmailSender emailSender;
	
	@RequestMapping("/register/useremail")
	@ResponseBody
	public int emailsend(@RequestParam String email1,@RequestParam String email2
			,@RequestParam(required = false) String email3) {
		
		logger.info("이메일 보내기 시작 파라미터 email1={},email2={}",email1,email2);
		logger.info("email3={}",email3);
		
		int res=0;
		
		if(email1==null || email1.isEmpty()) {
	         email2="";
	      }else {
	         if(email2.equals("etc")) {
	            if(email3!=null && !email3.isEmpty()) {
	               email2=email3;
	            }else {
	               email1="";
	               email2="";
	            }
	         }
	      }
		
		RandomKey k=new RandomKey();
	      String key=k.excuteGenerate();
	      
	      logger.info(email1+"@"+email2+"의 인증키={}",key);

		      if(Utility.map.containsKey(email1+"@"+email2)) {
		    	  Utility.map.remove(email1+"@"+email2);
		    	  Utility.map.put(email1+"@"+email2,key);		    	  
		      }else {
		    	  Utility.map.put(email1+"@"+email2,key);
		      }
	      
	      String subject="안녕하세요. 회원가입 인증키입니다.";
	      String content=DM.dmCertification(key);
	      String receiver=email1+"@"+email2;
	      String sender="fhdhaldh@naver.com";
	      
	      try {
	         emailSender.sendMail(subject, content, receiver, sender);
	         logger.info("이메일 발송 성공");
	         res=1;
	      } catch (MessagingException e) {
	         logger.info("이메일 발송 실패!!");
	         e.printStackTrace();
	      }
	      
	      return res;
	}
	
	@RequestMapping("/register/emailcer")
	@ResponseBody
	public int emailCer(@RequestParam String email1,@RequestParam String email2
			,@RequestParam(required = false) String email3,@RequestParam String cer) {
		logger.info("인증확인 파라미터 email1={},email2={}",email1,email2);
		logger.info("인증확인 파라미터 email3={},cer={}",email3,cer);
		
		int result=0;
		
		if(email1==null || email1.isEmpty()) {
	         email2="";
	      }else {
	         if(email2.equals("etc")) {
	            if(email3!=null && !email3.isEmpty()) {
	               email2=email3;
	            }else {
	               email1="";
	               email2="";
	            }
	         }
	      }
		
		String userkey=email1+"@"+email2;
		String uservalue=cer;
		
		logger.info("키={},값={}",userkey,uservalue);
		logger.info(Utility.map.get(userkey));
		
		if(Utility.map.containsKey(userkey)) {
			logger.info("있다");
			if(Utility.map.get(userkey).contains(uservalue)) {
				result=1;
				Utility.map.get(userkey).replace(uservalue, "Y");
				logger.info(Utility.map.get(userkey));
			}
		}else {
			logger.info("없다는데?");
			result=2;
		}
		
		
		return result;
		
	}
	
}
