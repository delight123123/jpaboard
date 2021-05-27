package com.single.jpaProjct.board.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;

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
		=PageRequest.of(searchVo.getCurrentPage(),Utility.BLOCK_SIZE 
				,  Sort.Direction.DESC, "reboardNo");

		Page<ReboardVO> page=null;
		
		if(keyWord.isEmpty() || keyWord==null) {
			page=reboardRepository.findAll(paging);
		}else {
			if(condition.equals("reboard_title")) {
				page=reboardRepository.findByReboardTitleContaining(keyWord, paging);
			}else if(condition.equals("reboard_content")) {
				page=reboardRepository.findByReboardContentContaining(keyWord, paging);
			}else if(condition.equals("userid")) {
				
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
}
