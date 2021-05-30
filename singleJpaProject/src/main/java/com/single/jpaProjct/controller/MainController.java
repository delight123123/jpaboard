package com.single.jpaProjct.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.jpaProjct.board.domain.ReboardService;
import com.single.jpaProjct.board.domain.ReboardVO;
import com.single.jpaProjct.common.PaginationInfo;
import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;

@Controller
public class MainController {

	private Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ReboardService reboardService;
	
	
	@RequestMapping("/main")
	public Object mainHome(@ModelAttribute SearchVO searchVo,
			Model model) {
		logger.info("메인화면 보이기");
		logger.info("파라미터 searchVo={}",searchVo);
		
		Page<ReboardVO> page=reboardService.mainSel(searchVo);
		
		List<ReboardVO> list=page.getContent().stream().collect(Collectors.toList());
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		int totalRecord=(int) page.getTotalElements();
		logger.info("totalRecord={}",totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		logger.info("pagingInfo={}",pagingInfo);
		
		logger.info("list={}",list.toString());
		logger.info("list.size()={}",list.size());
		logger.info("list.userid={}",list.get(0).getRegisterVo().getUserid());
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		
		return "Main";
	}
	
	@ResponseBody
	@RequestMapping("/fileimg")
	public long fileimg(@RequestParam("reboardNo") Long reboardNo) {
		long res=0;
		logger.info("파일 이미지 유무 파라미터 reboardNo={}",reboardNo);
		
		res=reboardService.fileimg(reboardNo);
		
		logger.info("게시물 파일 개수 res={}",res);
		
		return res;
	}
	
	@RequestMapping("/realTimeChat")
	public Object realTimeChat() {
		logger.info("실시간 채팅");
		
		return "chatting/realTimeChat";
	}
	
}
