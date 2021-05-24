package com.single.jpaProjct.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.board.common.PaginationInfo;
import com.single.board.common.Utility;
import com.single.board.model.ReboardService;
import com.single.board.model.ReboardVO;

import lombok.extern.java.Log;

@Controller
public class MainController {
	
	private final static Logger logger=LoggerFactory.getLogger(MainController.class);

	@Autowired
	private ReboardService reboardService;
	
	@RequestMapping("/main")
	public Object Mainhome(@ModelAttribute ReboardVO reboardVo,Model model) {
		logger.info("메인화면 보이기");
		logger.info("목록 파라미터 reboardVo={}",reboardVo);
		
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT);
		pagingInfo.setCurrentPage(reboardVo.getCurrentPage());
		
		reboardVo.setRecordCountPerPage(Utility.RECORD_COUNT);
		reboardVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		logger.info("셋팅후 reboardVo={}",reboardVo);
		
		List<ReboardVO> list=reboardService.reboardSearch(reboardVo);
		logger.info("검색결과 list.size()={}",list.size());
		
		int totalRecord=reboardService.searchTotal(reboardVo);
		logger.info("totalRecord={}",totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		logger.info("pagingInfo={}",pagingInfo);
		
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "Main";
	}
	
	@ResponseBody
	@RequestMapping("/fileimg")
	public int fileimg(@RequestParam("reboardNo") int reboardNo) {
		int res=0;
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
