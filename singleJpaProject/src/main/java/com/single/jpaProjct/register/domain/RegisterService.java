package com.single.jpaProjct.register.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SHA256Util;
import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;

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
	
	
	public Page<RegisterVO> useridManager(String userid,int type,SearchVO searchVo){
		
		Page<RegisterVO> page=Page.empty();

		if(type==Utility.NONEWDWTYPE) {
			Pageable paging
			=PageRequest.of(searchVo.getCurrentPage(),Utility.BLOCK_SIZE 
					,  Sort.Direction.DESC, "userid");
			
			page=registerRepository.findByUseridNotAndOutDateNull(userid, paging);
		}else if(type==Utility.WDWTYPE) {
			Pageable paging
			=PageRequest.of(searchVo.getCurrentPage2(),Utility.BLOCK_SIZE 
					,  Sort.Direction.DESC, "userid");
			page=registerRepository.findByUseridNotAndOutDateNotNull(userid, paging);
		}
		
		return page;
	}
	
	
	public int forcedExit(String userid) {
		int res=0;
		
		Optional<RegisterVO> op=registerRepository.findOneByUserid(userid);
		
		if(op.isPresent()) {
			RegisterVO vo=op.get();
			LocalDateTime ldt=LocalDateTime.now();
			
			vo.setOutDate(Timestamp.valueOf(ldt));
			
			registerRepository.save(vo);
			res=1;
		}
		
		return res;
	}
	
	public int cancle(String userid) {
		int res=0;
		
		Optional<RegisterVO> op=registerRepository.findOneByUserid(userid);
		
		if(op.isPresent()) {
			RegisterVO vo=op.get();
			vo.setOutDate(Timestamp.valueOf(""));
			registerRepository.save(vo);
			res=1;
		}
		
		return res;
	}
	
	
	public int multiforcedExit(List<RegisterVO> list) {
		int res=0;
		
		LocalDateTime ldt=LocalDateTime.now();
		
		list.stream().forEach(a-> a.setOutDate(Timestamp.valueOf(ldt)));
		
		List<RegisterVO> resList=registerRepository.saveAll(list);
		if(!resList.isEmpty()) {
			res=1;
		}
		return res;
	}
	
	
	public int multiCancle(List<RegisterVO> list) {
		int res=0;
		
		list.stream().forEach(a -> a.setOutDate(Timestamp.valueOf("")));
		
		List<RegisterVO> resList=registerRepository.saveAll(list);
		
		if(!resList.isEmpty()) {
			res=1;
		}
		
		
		return res;
	}
	
	public List<RegisterVO> userAll(){
		return registerRepository.findAll();
	}
}
