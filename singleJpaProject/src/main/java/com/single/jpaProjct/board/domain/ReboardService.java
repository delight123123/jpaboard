package com.single.jpaProjct.board.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;
import com.single.jpaProjct.register.domain.RegisterVO;

@Service
public class ReboardService {

	@Autowired
	private ReboardRepository reboardRepository;
	
	@Autowired
	private UpfileListRepository upfileListRepository;
	
	public Page<ReboardVO> mainSel(SearchVO searchVo){
		String condition=searchVo.getSearchCondition();
		String keyWord=searchVo.getSearchKeyword();
		
		Pageable paging
		=PageRequest.of(searchVo.getCurrentPage(),Utility.RECORD_COUNT 
				,  Sort.Direction.DESC, "reboardNo");

		Page<ReboardVO> page=Page.empty();
		
		if(keyWord.isEmpty() || keyWord==null) {
			page=reboardRepository.findAll(paging);
		}else {
			if(condition.equals("reboard_title")) {
				page=reboardRepository.findByReboardTitleContaining(keyWord, paging);
			}else if(condition.equals("reboard_content")) {
				page=reboardRepository.findByReboardContentContaining(keyWord, paging);
			}else if(condition.equals("userid")) {
				RegisterVO vo=new RegisterVO();
				vo.setUserid(keyWord);
				page=reboardRepository.findAllByRegisterVo(vo, paging);
			}
		}
		
		return page;
	}
	
	
	public ReboardVO reboardWrite(ReboardVO reboardVo) {
		return reboardRepository.save(reboardVo);
	}
	
	
	public List<UpfileListVO> upfilelistInsert(List<UpfileListVO> list) {
		return upfileListRepository.saveAll(list);
	}
	
	
	public int readcountUp(Long reNo) {
		Optional<ReboardVO> vo=reboardRepository.findById(reNo);
		int res=0;
		if(!vo.isEmpty()) {
			vo.get().setReadcount(vo.get().getReadcount());
			reboardRepository.save(vo.get());
			res=1;
		}
		return res;
	}
	
	public ReboardVO reboardSelByNo(int reboardNo) {
		return reboardRepository.findById(reboardNo+0L).get();
	}
	
	public int reboardDel(Long no,Long groupno,Long step) {
		return reboardRepository.delProcedure(no, groupno, step);
	}
	
	public UpfileListVO downCntUp(Long no) {
		UpfileListVO vo=upfileListRepository.findById(no).get();
		vo.setDownCount(vo.getDownCount()+1);
		return upfileListRepository.save(vo);
	}
	
	public UpfileListVO fileOneSel(Long no) {
		return upfileListRepository.findById(no).get();
	}
}
