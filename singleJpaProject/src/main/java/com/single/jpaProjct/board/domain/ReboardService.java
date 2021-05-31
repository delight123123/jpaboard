package com.single.jpaProjct.board.domain;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		int currentPage=searchVo.getCurrentPage();
		System.out.println(currentPage);
		Pageable paging
		=PageRequest.of(searchVo.getCurrentPage()-1,Utility.RECORD_COUNT 
				,  Sort.Direction.ASC, "sortno");

		Page<ReboardVO> page=Page.empty();
		
		if(keyWord.isEmpty() || keyWord==null) {
			System.out.println("1");
			page=reboardRepository.findAllByOrderByGroupnoDesc(paging);
		}else {
			if(condition.equals("reboard_title")) {
				System.out.println("2");
				page=reboardRepository.findByReboardTitleContainingOrderByGroupnoDesc(keyWord, paging);
			}else if(condition.equals("reboard_content")) {
				System.out.println("3");
				page=reboardRepository.findByReboardContentContainingOrderByGroupnoDesc(keyWord, paging);
			}else if(condition.equals("userid")) {
				System.out.println("4");
				RegisterVO vo=new RegisterVO();
				vo.setUserid(keyWord);
				page=reboardRepository.findAllByRegisterVoOrderByGroupnoDesc(vo, paging);
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
			vo.get().setReadcount(vo.get().getReadcount()+1);
			reboardRepository.save(vo.get());
			res=1;
		}
		return res;
	}
	
	public ReboardVO reboardSelByNo(Long reboardNo) {
		return reboardRepository.findById(reboardNo+0L).get();
	}
	
	public void reboardDel(Long no,Long groupno,Long step) {
		reboardRepository.delProcedure(no, groupno, step);
	}
	
	public UpfileListVO downCntUp(Long no) {
		UpfileListVO vo=upfileListRepository.findById(no).get();
		vo.setDownCount(vo.getDownCount()+1);
		return upfileListRepository.save(vo);
	}
	
	public UpfileListVO fileOneSel(Long no) {
		return upfileListRepository.findById(no).get();
	}
	
	public Long fileimg(Long reboardNo) {
		ReboardVO reboardVo=new ReboardVO();
		reboardVo.setReboardNo(reboardNo);
		return upfileListRepository.countByReboardVo(reboardVo);
	}


}
